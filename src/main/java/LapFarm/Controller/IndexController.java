package LapFarm.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.Bean.Mailer;
import LapFarm.DAO.BrandDAO;
import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.PaginatesDto;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Service.PaginatesServiceImp;
import LapFarm.Service.ProductServiceImp;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class IndexController extends BaseController {

	@Autowired
	Mailer mailer;

	@Autowired
	private ServletContext context;

	@Autowired
	ProductServiceImp productService;
	@Autowired
	private PaginatesServiceImp paginateService;
	private int totalProductPage = 9;

	@RequestMapping(value = { "", "/", "/home" }, method = RequestMethod.GET, params = "!page")
	public ModelAndView Index(HttpSession session) {
	    Init();
	    addCartToSession(session);

	    // Lấy danh sách sản phẩm
	    List<ProductDTO> allProducts = productService.getAllProductsDTO();
	    _mvShare.addObject("products", allProducts);

	    // Lấy tổng số lượng sản phẩm
	    _mvShare.addObject("totalQuantity", productService.getTotalProductQuantity());

	    // Lấy tổng số sản phẩm và thông tin phân trang
	    int totalData = allProducts.size();
	    PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
	    _mvShare.addObject("paginateInfo", paginateInfo);

	    // Phân trang và lấy danh sách sản phẩm phân trang
	    List<ProductDTO> paginatedProducts = productService.GetDataProductPaginates(paginateInfo.getStart(), paginateInfo.getEnd(), "", 0, "", 0);

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

	    // Đưa ProductsPaginate vào model
	    _mvShare.addObject("ProductsPaginate", paginatedProducts);

	    // Lấy giá min và max
	    Map<String, Double> price = productService.getMinMaxPrices();
	    _mvShare.addObject("priceMin", price.get("min"));
	    _mvShare.addObject("priceMax", price.get("max"));

	    // Đặt view là "store"
	    _mvShare.setViewName("store");
	    return _mvShare;
	}




	@RequestMapping(value = { "", "/", "/home" }, method = RequestMethod.GET, params = "page")
	public ModelAndView Index(@RequestParam(value = "page", defaultValue = "1") int currentPage) {
	    Init();
	    
	    // Lấy tất cả sản phẩm
	    List<ProductDTO> allProducts = productService.getAllProductsDTO();
	    _mvShare.addObject("products", allProducts);

	    // Lấy tổng số lượng sản phẩm
	    _mvShare.addObject("totalQuantity", productService.getTotalProductQuantity());

	    // Tính tổng số dữ liệu và thông tin phân trang
	    int totalData = allProducts.size();
	    PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
	    _mvShare.addObject("paginateInfo", paginateInfo);

	    // Lấy dữ liệu phân trang
	    List<ProductDTO> paginatedProducts = productService.GetDataProductPaginates(paginateInfo.getStart(), paginateInfo.getEnd(), "", 0, "", 0);

	    // Lấy tất cả ID sản phẩm trong danh sách phân trang
	    List<Integer> productIds = paginatedProducts.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());

	    // Gọi service để lấy rating summary cho tất cả sản phẩm
	    List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);
	    System.out.println("Rating Summaries: " + ratingSummaries);

	    // Gán rating summary vào từng sản phẩm trong danh sách phân trang
	    for (ProductDTO product : paginatedProducts) {
	        for (Map<String, Object> summary : ratingSummaries) {
	            if (product.getIdProduct() == (int) summary.get("productId")) {
	                product.setRatingSummary(summary);
	            }
	        }
	    }

	    // Đưa ProductsPaginate vào model
	    _mvShare.addObject("ProductsPaginate", paginatedProducts);

	    // Lấy giá min và max
	    Map<String, Double> price = productService.getMinMaxPrices();
	    _mvShare.addObject("priceMin", price.get("min"));
	    _mvShare.addObject("priceMax", price.get("max"));

	    // Đặt view là "store"
	    _mvShare.setViewName("store");

	    return _mvShare;
	}


	@RequestMapping(value = "/home/send")
	public String send(ModelMap model, @RequestParam("email") String email) {
		try {
			mailer.send(email, context);
			model.addAttribute("message", "Gửi email thành công !");
		} catch (Exception ex) {
			model.addAttribute("message", "Gửi email thất bại !");
		}
		return "redirect:/home";
	}

}
