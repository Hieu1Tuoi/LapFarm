package LapFarm.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.Bean.Mailer;
import LapFarm.DTO.PaginatesDto;
import LapFarm.DTO.ProductDTO;
import LapFarm.Service.CategoryServiceImp;
import LapFarm.Service.PaginatesServiceImp;
import LapFarm.Service.ProductServiceImp;
import LapFarm.Utils.SecureUrlUtil;
import jakarta.servlet.ServletContext;

@Controller
@RequestMapping(value = "/search")
public class SearchController extends BaseController {

    @Autowired
    private ServletContext context;

    @Autowired
    private ProductServiceImp productService;

    @Autowired
    private PaginatesServiceImp paginateService;

    private final int totalProductPage = 9;

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public ModelAndView Index(@RequestParam("idCategory") String idCategory,
                              @RequestParam("searchtext") String searchText,
                              @RequestParam("priceRange") String priceRange,
                              @RequestParam(value = "idBrand", required = false) Integer idBrand,
                              @RequestParam(value = "page", defaultValue = "1") int currentPage) {

    	Init();
        // Giải mã idCategory
        String decryptedIdCategory;
        try {
            decryptedIdCategory = SecureUrlUtil.decrypt(idCategory);
        } catch (Exception e) {
            // Xử lý nếu giải mã thất bại
            decryptedIdCategory = "0"; // Giá trị mặc định nếu giải mã không thành công
            e.printStackTrace();
        }

        // Kiểm tra và chuẩn hóa các tham số
        if (idBrand == null) {
            idBrand = 0;
        }
        priceRange = validatePriceRange(priceRange);

        // Lấy danh sách tất cả sản phẩm và áp dụng bộ lọc
        List<ProductDTO> allProducts = productService.getAllProductsDTO();
        List<ProductDTO> filteredProducts = filter(allProducts, searchText, Integer.parseInt(decryptedIdCategory), priceRange, idBrand);

        // Tính toán thông tin phân trang
        int totalData = filteredProducts.size();
        PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);

        // Lấy sản phẩm phân trang
        List<ProductDTO> paginatedProducts = productService.GetDataProductPaginates(paginateInfo.getStart(), 
                                                                                   paginateInfo.getEnd(), 
                                                                                   searchText, Integer.parseInt(decryptedIdCategory), 
                                                                                   priceRange, idBrand);

        // Lấy tất cả ID sản phẩm để lấy rating summary cho mỗi sản phẩm
        List<Integer> productIds = paginatedProducts.stream()
                                                    .map(ProductDTO::getIdProduct)
                                                    .collect(Collectors.toList());

        // Gọi service để lấy rating summary cho tất cả sản phẩm
        List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);

        // Gán rating summary vào từng sản phẩm trong danh sách phân trang
        for (ProductDTO product : paginatedProducts) {
            ratingSummaries.stream()
                           .filter(summary -> product.getIdProduct() == (int) summary.get("productId"))
                           .findFirst()
                           .ifPresent(summary -> product.setRatingSummary(summary));

            // Thêm mã hóa idProduct vào sản phẩm
            try {
                product.setEncryptedId(SecureUrlUtil.encrypt(String.valueOf(product.getIdProduct())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Đưa các giá trị vào ModelAndView
        _mvShare.addObject("searchText", searchText);
        _mvShare.addObject("searchCategory", decryptedIdCategory);
        _mvShare.addObject("priceRange", priceRange);
        _mvShare.addObject("ProductsPaginate", paginatedProducts);
        _mvShare.addObject("paginateInfo", paginateInfo);

        // Lấy giá trị min và max cho phạm vi giá
        Map<String, Double> price = productService.getMinMaxPrices();
        _mvShare.addObject("priceMin", price.get("min"));
        _mvShare.addObject("priceMax", price.get("max"));

        // Đặt view cho ModelAndView
        _mvShare.setViewName("search");
        return _mvShare;
    }


    // Phương thức lọc sản phẩm
    public List<ProductDTO> filter(List<ProductDTO> list, String searchText, int idCategory, String priceRange,
                                   int idBrand) {
        if ((searchText == null || searchText.trim().isEmpty()) && idCategory == 0
                && (priceRange == null || priceRange.isEmpty()) && idBrand == 0) {
            return list; // Nếu không có điều kiện tìm kiếm, trả về toàn bộ danh sách
        }

        double priceMin = Double.MIN_VALUE;
        double priceMax = Double.MAX_VALUE;

        if (priceRange != null && !priceRange.isEmpty()) {
            String[] parts = priceRange.split("-");
            if (parts.length == 2) {
                try {
                    priceMin = Double.parseDouble(parts[0].trim());
                    priceMax = Double.parseDouble(parts[1].trim());
                } catch (NumberFormatException e) {
                    // Nếu parse không thành công, giữ lại giá trị mặc định
                }
            }
        }

        final double finalPriceMin = priceMin;
        final double finalPriceMax = priceMax;

        return list.stream().filter(product -> {
            boolean matchesSearchText = (searchText == null || searchText.trim().isEmpty())
                    || product.getNameProduct().toLowerCase().contains(searchText.toLowerCase());
            boolean matchesCategory = (idCategory == 0) || (product.getIdCategory() == idCategory);
            boolean matchesBrand = (idBrand == 0) || (product.getIdBrand() == idBrand);

            double salePriceAfterDiscount = product.getSalePrice() * (1 - product.getDiscount());
            salePriceAfterDiscount = Math.round(salePriceAfterDiscount * 100.0) / 100.0;

            boolean matchesPriceRange = salePriceAfterDiscount >= finalPriceMin
                    && salePriceAfterDiscount <= finalPriceMax;

            return matchesSearchText && matchesCategory && matchesPriceRange && matchesBrand;
        }).collect(Collectors.toList());
    }

    // Phương thức kiểm tra và chuẩn hóa giá trị priceRange
    public static String validatePriceRange(String priceRange) {
        if (priceRange == null || priceRange.trim().isEmpty()) {
            return ""; // Trả về chuỗi rỗng nếu không có giá trị
        }

        String[] parts = priceRange.split("-");
        if (parts.length != 2) {
            return ""; // Trả về chuỗi rỗng nếu không có đúng 2 phần
        }

        try {
            double priceMin = Double.parseDouble(parts[0].trim());
            double priceMax = Double.parseDouble(parts[1].trim());

            if (priceMin <= priceMax) {
                return priceRange; // Trả về chuỗi hợp lệ nếu điều kiện đúng
            }
        } catch (NumberFormatException e) {
            // Nếu không thể parse giá trị, trả về chuỗi rỗng
        }

        return "";
    }
}
