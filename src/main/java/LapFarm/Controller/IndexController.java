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
import LapFarm.Utils.SecureUrlUtil;
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
	    return handlePageRequest(1, session); // Mặc định trang đầu tiên
	}

	@RequestMapping(value = { "", "/", "/home" }, method = RequestMethod.GET, params = "page")
	public ModelAndView Index(@RequestParam(value = "page", defaultValue = "1") int currentPage, HttpSession session) {
	    return handlePageRequest(currentPage, session); // Sử dụng giá trị trang được truyền
	}

	private ModelAndView handlePageRequest(int currentPage, HttpSession session) {
	    Init();
	    addCartToSession(session);

	    // Lấy danh sách sản phẩm
	    List<ProductDTO> allProducts = productService.getAllProductsDTO();

	    // Mã hóa ID sản phẩm
	    allProducts.forEach(product -> {
	        try {
	            product.setEncryptedId(SecureUrlUtil.encrypt(String.valueOf(product.getIdProduct())));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	    _mvShare.addObject("products", allProducts);

	    // Tính toán phân trang
	    int totalData = allProducts.size();
	    PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
	    _mvShare.addObject("paginateInfo", paginateInfo);

	    // Lấy sản phẩm phân trang
	    List<ProductDTO> paginatedProducts = productService.GetDataProductPaginates(
	        paginateInfo.getStart(), paginateInfo.getEnd(), "", 0, "", 0
	    );

	    // Mã hóa ID sản phẩm trong danh sách phân trang
	    paginatedProducts.forEach(product -> {
	        try {
	            product.setEncryptedId(SecureUrlUtil.encrypt(String.valueOf(product.getIdProduct())));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });

	    // Lấy rating summary cho từng sản phẩm
	    List<Integer> productIds = paginatedProducts.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());
	    List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);

	    // Gắn rating summary vào sản phẩm
	    for (ProductDTO product : paginatedProducts) {
	        for (Map<String, Object> summary : ratingSummaries) {
	            if (product.getIdProduct() == (int) summary.get("productId")) {
	                product.setRatingSummary(summary);
	            }
	        }
	    }

	    _mvShare.addObject("ProductsPaginate", paginatedProducts);

	    // Lấy giá min và max
	    Map<String, Double> price = productService.getMinMaxPrices();
	    _mvShare.addObject("priceMin", price.get("min"));
	    _mvShare.addObject("priceMax", price.get("max"));

	    // Đặt view
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
