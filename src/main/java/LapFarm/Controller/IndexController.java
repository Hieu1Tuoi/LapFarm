package LapFarm.Controller;

import java.util.List;
import java.util.Map;

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
	public ModelAndView Index() {
		// Lấy danh sách Category
		_mvShare.addObject("categories", _baseService.getCategoryEntities());

		// Lấy danh sách Brand
		_mvShare.addObject("brands", _baseService.getBrandEntities());

		_mvShare.addObject("products", _baseService.getAllProducts());

		// Lấy số lượng sản phẩm theo tất cả danh mục
		_mvShare.addObject("productCounts",
				_baseService.getProductCountByAllCategories(_baseService.getCategoryEntities())); // Truyền Map vào
																									// Model

		// Lấy số lượng sản phẩm theo tất cả brand
		_mvShare.addObject("productCountsByBrand",
				_baseService.getProductCountByAllBrands(_baseService.getBrandEntities()));

		_mvShare.addObject("products_top_sell", _baseService.getTop5ProductsByLowestQuantity());

		// Thêm vào model để hiển thị trên view
		_mvShare.addObject("totalQuantity", productService.getTotalProductQuantity());

		int totalData = _baseService.getAllProducts().size();
		PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
		_mvShare.addObject("paginateInfo", paginateInfo);
		_mvShare.addObject("ProductsPaginate",
				productService.GetDataProductPaginates(paginateInfo.getStart(), paginateInfo.getEnd()));
		_mvShare.setViewName("store");
		return _mvShare;
	}

	@RequestMapping(value = { "", "/", "/home" }, method = RequestMethod.GET, params = "page")
	public ModelAndView Index(@RequestParam(value = "page", defaultValue = "1") int currentPage) {
		// Lấy danh sách Category
		_mvShare.addObject("categories", _baseService.getCategoryEntities());

		// Lấy danh sách Brand
		_mvShare.addObject("brands", _baseService.getBrandEntities());

		_mvShare.addObject("products", _baseService.getAllProducts());

		// Lấy số lượng sản phẩm theo tất cả danh mục
		_mvShare.addObject("productCounts",
				_baseService.getProductCountByAllCategories(_baseService.getCategoryEntities())); // Truyền Map vào
																									// Model

		// Lấy số lượng sản phẩm theo tất cả brand
		_mvShare.addObject("productCountsByBrand",
				_baseService.getProductCountByAllBrands(_baseService.getBrandEntities()));

		_mvShare.addObject("products_top_sell", _baseService.getTop5ProductsByLowestQuantity());

		// Thêm vào model để hiển thị trên view
		_mvShare.addObject("totalQuantity", productService.getTotalProductQuantity());
		int totalData = _baseService.getAllProducts().size();
		PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
		_mvShare.addObject("paginateInfo", paginateInfo);
		_mvShare.addObject("ProductsPaginate",
				productService.GetDataProductPaginates(paginateInfo.getStart(), paginateInfo.getEnd()));
		_mvShare.setViewName("store");
		return _mvShare;
	}

	@RequestMapping("error")
	public String error() {
		return "error";
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
