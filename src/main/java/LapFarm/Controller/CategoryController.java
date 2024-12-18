package LapFarm.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.DTO.PaginatesDto;
import LapFarm.DTO.ProductDTO;
import LapFarm.Service.CategoryServiceImp;
import LapFarm.Service.PaginatesServiceImp;
import LapFarm.Service.ProductServiceImp;
import LapFarm.Utils.SecureUrlUtil;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class CategoryController extends BaseController {

    @Autowired
    private CategoryServiceImp categoryService;
    @Autowired
    private ProductServiceImp productService;
    @Autowired
    private PaginatesServiceImp paginateService;

    private int totalProductPage = 9;

    @RequestMapping(value = "/products-category", params = {"!page"})
    public ModelAndView indexWithoutPage(@RequestParam(value = "idCategory", required = false) String encryptedIdCategory) {
        // Giải mã idCategory từ tham số URL
        int idCategory = decryptIdCategory(encryptedIdCategory);
        return handleCategoryRequest(idCategory, 1);
    }

    @RequestMapping(value = "/products-category", params = {"page"})
    public ModelAndView indexWithPage(@RequestParam(value = "idCategory", required = false) String encryptedIdCategory,
                                      @RequestParam(value = "page", defaultValue = "1") int currentPage) {
        // Giải mã idCategory từ tham số URL
        int idCategory = decryptIdCategory(encryptedIdCategory);
        return handleCategoryRequest(idCategory, currentPage);
    }

    private int decryptIdCategory(String encryptedIdCategory) {
        try {
            // Giải mã idCategory
            String decryptedId = SecureUrlUtil.decrypt(encryptedIdCategory);
            return Integer.parseInt(decryptedId);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Trả về giá trị mặc định nếu không thể giải mã
        }
    }

    private ModelAndView handleCategoryRequest(int idCategory, int currentPage) {
        Init();

        // Lấy thông tin Category
        _mvShare.addObject("category", categoryService.getCategoryById(idCategory));

        // Lấy danh sách sản phẩm theo idCategory
        List<ProductDTO> allProducts = categoryService.getProductsByCategory(idCategory);
        
        // Mã hóa ID sản phẩm
        allProducts.forEach(product -> {
            try {
                product.setEncryptedId(SecureUrlUtil.encrypt(String.valueOf(product.getIdProduct())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        _mvShare.addObject("AllProductByID", allProducts);

        // Tính tổng số sản phẩm và phân trang
        int totalData = allProducts.size();
        PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
        _mvShare.addObject("paginateInfo", paginateInfo);

        // Lấy sản phẩm phân trang
        List<ProductDTO> productsPaginate = categoryService.GetDataProductPaginates(paginateInfo.getStart(),
                                                                                   paginateInfo.getEnd(), "", idCategory, "", 0);

        // Mã hóa ID sản phẩm trong danh sách phân trang
        productsPaginate.forEach(product -> {
            try {
                product.setEncryptedId(SecureUrlUtil.encrypt(String.valueOf(product.getIdProduct())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Lấy rating cho các sản phẩm trong danh mục
        List<Integer> productIds = productsPaginate.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());
        List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);

        // Gán rating summary vào từng sản phẩm trong danh sách phân trang
        for (ProductDTO product : productsPaginate) {
            for (Map<String, Object> summary : ratingSummaries) {
                if (product.getIdProduct() == (int) summary.get("productId")) {
                    product.setRatingSummary(summary);
                }
            }
        }

        // Truyền sản phẩm đã có rating vào model
        _mvShare.addObject("ProductsPaginate", productsPaginate);

        // Truyền các giá trị min, max cho phạm vi giá
        Map<String, Double> price = productService.getMinMaxPrices();
        _mvShare.addObject("searchCategory", idCategory);
        _mvShare.addObject("priceMin", price.get("min"));
        _mvShare.addObject("priceMax", price.get("max"));

        // Đặt view cho ModelAndView
        _mvShare.setViewName("productsByCategory");
        return _mvShare;
    }
}
