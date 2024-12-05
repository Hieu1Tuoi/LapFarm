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
import jakarta.servlet.ServletContext;

@Controller
@RequestMapping(value = "/search")
public class SearchController extends BaseController {

	@Autowired
	private ServletContext context;

	@Autowired
	ProductServiceImp productService;
	@Autowired
	private PaginatesServiceImp paginateService;
	private int totalProductPage = 9;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, params = "!page")
	public ModelAndView Index(@RequestParam("idCategory") String idCategory,
	                          @RequestParam(value = "idBrand", required = false) Integer idBrand,
	                          @RequestParam("searchtext") String searchText,
	                          @RequestParam("priceRange") String priceRange,
	                          @RequestParam(value = "page", defaultValue = "1") int currentPage) {
	    
	    if (idBrand == null) {
	        idBrand = 0; // Giá trị mặc định nếu không có tham số idBrand
	    }
	    
	    // Kiểm tra và chuẩn hóa giá trị priceRange
	    priceRange = validatePriceRange(priceRange);
	    
	    // Lấy danh sách sản phẩm theo phân trang
	    Init();
	    _mvShare.addObject("products", productService.getAllProductsDTO());

	    // Thêm vào model để hiển thị trên view
	    _mvShare.addObject("totalQuantity", productService.getTotalProductQuantity());

	    // Lọc sản phẩm theo searchText, idCategory, priceRange, và idBrand
	    List<ProductDTO> filteredProducts = filter(productService.getAllProductsDTO(), searchText, 
	                                                Integer.valueOf(idCategory), priceRange, idBrand);
	    
	    // Tính toán tổng số dữ liệu sau khi lọc
	    int totalData = filteredProducts.size();
	    PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
	    _mvShare.addObject("paginateInfo", paginateInfo);

	    // Lấy danh sách sản phẩm đã phân trang
	    List<ProductDTO> paginatedProducts = productService.GetDataProductPaginates(paginateInfo.getStart(), 
	                                                                               paginateInfo.getEnd(), 
	                                                                               searchText, Integer.valueOf(idCategory), 
	                                                                               priceRange, idBrand);
	    
	    // In ra số lượng sản phẩm sau khi lọc và phân trang
	    System.out.println(totalData);
	    System.out.println(paginatedProducts.size());

	    // Lấy tất cả ID sản phẩm để lấy rating summary cho mỗi sản phẩm
	    List<Integer> productIds = paginatedProducts.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());

	    // Gọi service để lấy rating summary cho tất cả sản phẩm
	    List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);
	    System.out.println("Rating Summaries: " + ratingSummaries);

	    // Gán rating summary vào từng sản phẩm trong danh sách phân trang
	    for (ProductDTO product : paginatedProducts) {
	        for (Map<String, Object> summary : ratingSummaries) {
	            if (product.getIdProduct() == (int) summary.get("productId")) {
	                product.setRatingSummary(summary);
	                System.out.println("Product ID: " + product.getIdProduct() + " - Rating: " + summary.get("average"));
	            }
	        }
	    }

	    // Truyền các tham số vào Model để hiển thị trong view
	    _mvShare.addObject("searchText", searchText);
	    _mvShare.addObject("searchCategory", idCategory);
	    _mvShare.addObject("priceRange", priceRange);
	    _mvShare.addObject("ProductsPaginate", paginatedProducts);
	    
	    // Lấy giá trị min và max cho phạm vi giá
	    Map<String, Double> price = productService.getMinMaxPrices();
	    _mvShare.addObject("priceMin", price.get("min"));
	    _mvShare.addObject("priceMax", price.get("max"));

	    // Đặt view cho ModelAndView
	    _mvShare.setViewName("search");
	    return _mvShare;
	}


	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, params = "page")
	public ModelAndView Index(@RequestParam("idCategory") String idCategory,
	                          @RequestParam("searchtext") String searchText,
	                          @RequestParam("priceRange") String priceRange,
	                          @RequestParam(value = "idBrand", required = false) Integer idBrand,
	                          @RequestParam(value = "page", defaultValue = "1") int currentPage) {
	    
	    // Chuẩn hóa giá trị priceRange
	    priceRange = validatePriceRange(priceRange);
	    
	    // Khởi tạo các tham số ban đầu
	    Init();
	    
	    // Nếu idBrand không có thì gán giá trị mặc định là 0
	    if (idBrand == null) {
	        idBrand = 0;
	    }
	    
	    // Lấy tất cả sản phẩm và truyền vào model
	    _mvShare.addObject("products", productService.getAllProductsDTO());
	    
	    // Lấy tổng số sản phẩm
	    _mvShare.addObject("totalQuantity", productService.getTotalProductQuantity());

	    // Lọc danh sách sản phẩm theo các tham số tìm kiếm
	    List<ProductDTO> filteredProducts = filter(productService.getAllProductsDTO(), searchText, 
	                                                Integer.valueOf(idCategory), priceRange, idBrand);
	    
	    // Lấy số lượng sản phẩm sau khi lọc
	    int totalData = filteredProducts.size();
	    PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
	    _mvShare.addObject("paginateInfo", paginateInfo);

	    // Lấy sản phẩm phân trang
	    List<ProductDTO> allProducts = productService.GetDataProductPaginates(paginateInfo.getStart(),
	                                                                         paginateInfo.getEnd(),
	                                                                         searchText, Integer.valueOf(idCategory),
	                                                                         priceRange, idBrand);

	    // Lấy tất cả ID sản phẩm để lấy rating summary cho mỗi sản phẩm
	    List<Integer> productIds = allProducts.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());
	    
	    // Gọi service để lấy rating summary cho tất cả sản phẩm
	    List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);
	    System.out.println("Rating Summaries: " + ratingSummaries);
	    
	    // Gán rating summary vào từng sản phẩm trong danh sách phân trang
	    for (ProductDTO product : allProducts) {
	        for (Map<String, Object> summary : ratingSummaries) {
	            if (product.getIdProduct() == (int) summary.get("productId")) {
	                product.setRatingSummary(summary);
	                System.out.println("Product ID: " + product.getIdProduct() + " - Rating: " + summary.get("average"));
	            }
	        }
	    }

	    // Truyền các tham số vào Model để hiển thị trên view
	    _mvShare.addObject("searchText", searchText);
	    _mvShare.addObject("searchCategory", idCategory);
	    _mvShare.addObject("priceRange", priceRange);
	    _mvShare.addObject("ProductsPaginate", allProducts);
	    
	    // Lấy giá trị min và max cho phạm vi giá
	    Map<String, Double> price = productService.getMinMaxPrices();
	    _mvShare.addObject("priceMin", price.get("min"));
	    _mvShare.addObject("priceMax", price.get("max"));

	    // Đặt view cho ModelAndView
	    _mvShare.setViewName("search");
	    return _mvShare;
	}

	public List<ProductDTO> filter(List<ProductDTO> list, String searchText, int idCategory, String priceRange,
			int idBrand) {
		// Nếu không có điều kiện tìm kiếm, trả về toàn bộ danh sách
		if ((searchText == null || searchText.trim().isEmpty()) && idCategory == 0
				&& (priceRange == null || priceRange.isEmpty()) && idBrand == 0) {
			return list;
		}

		// Xử lý giá trị priceMin và priceMax từ priceRange
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

		// Tạo các biến final để sử dụng trong biểu thức lambda
		final double finalPriceMin = priceMin;
		final double finalPriceMax = priceMax;

		// Thực hiện lọc theo các tiêu chí
		return list.stream().filter(product -> {
			// Lọc theo tên sản phẩm
			boolean matchesSearchText = (searchText == null || searchText.trim().isEmpty())
					|| product.getNameProduct().toLowerCase().contains(searchText.toLowerCase());

			// Lọc theo danh mục
			boolean matchesCategory = (idCategory == 0) || (product.getIdCategory() == idCategory);

			// Lọc theo thương hiệu
			boolean matchesBrand = (idBrand == 0) || (product.getIdBrand() == idBrand);

			// Tính giá trị SalePrice đã áp dụng discount và làm tròn đến 2 chữ số thập phân
			double salePriceAfterDiscount = product.getSalePrice() * (1 - product.getDiscount());
			salePriceAfterDiscount = Math.round(salePriceAfterDiscount * 100.0) / 100.0;

			// Lọc theo khoảng giá sau khi đã áp dụng discount và làm tròn
			boolean matchesPriceRange = salePriceAfterDiscount >= finalPriceMin
					&& salePriceAfterDiscount <= finalPriceMax;

			return matchesSearchText && matchesCategory && matchesPriceRange && matchesBrand;
		}).collect(Collectors.toList());
	}

	public static String validatePriceRange(String priceRange) {
		// Kiểm tra nếu chuỗi null hoặc rỗng
		if (priceRange == null || priceRange.trim().isEmpty()) {
			return "";
		}

		// Tách chuỗi thành 2 phần dựa trên dấu "-"
		String[] parts = priceRange.split("-");
		if (parts.length != 2) {
			return ""; // Nếu không có đúng 2 phần, trả về chuỗi rỗng
		}

		try {
			// Chuyển đổi 2 giá trị thành số
			double priceMin = Double.parseDouble(parts[0].trim());
			double priceMax = Double.parseDouble(parts[1].trim());

			// Kiểm tra điều kiện priceMin <= priceMax
			if (priceMin <= priceMax) {
				return priceRange; // Trả về chuỗi không thay đổi nếu hợp lệ
			}
		} catch (NumberFormatException e) {
			// Bắt lỗi nếu một trong hai giá trị không phải là số
		}

		return ""; // Trả về chuỗi rỗng cho các trường hợp không hợp lệ
	}

}
