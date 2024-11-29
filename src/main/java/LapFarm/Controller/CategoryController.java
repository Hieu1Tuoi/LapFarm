package LapFarm.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.DAO.BrandDAO;
import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.PaginatesDto;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Service.CategoryServiceImp;
import LapFarm.Service.PaginatesServiceImp;
import LapFarm.Service.ProductServiceImp;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class CategoryController extends BaseController {

	@Autowired
	private CategoryServiceImp categoryService;
	@Autowired
	ProductServiceImp productService;
	@Autowired
	private PaginatesServiceImp paginateService;
	private int totalProductPage = 9;

	@RequestMapping(value = "/products-category", params = "!page")
	public ModelAndView Index(@RequestParam(value = "idCategory", required = false) int idCategory) {

		Init();

		// Lấy toàn bộ thông tin Category
		_mvShare.addObject("category", categoryService.getCategoryById(idCategory));

		_mvShare.addObject("AllProductByID", categoryService.getProductsByCategory(idCategory));
		
		int totalData = categoryService.getProductsByCategory(idCategory).size();
		PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
		_mvShare.addObject("paginateInfo", paginateInfo);
		_mvShare.addObject("ProductsPaginate",
				categoryService.GetDataProductPaginates(paginateInfo.getStart(), paginateInfo.getEnd(), "", idCategory, "", 0));
		Map<String, Double> price = productService.getMinMaxPrices();
		_mvShare.addObject("searchCategory", idCategory);
		_mvShare.addObject("priceMin", price.get("min"));
		_mvShare.addObject("priceMax", price.get("max"));
		_mvShare.setViewName("productsByCategory");
		return _mvShare; // The view name
	}

	@RequestMapping(value = "/products-category", params = "page")
	public ModelAndView Index(@RequestParam(value = "idCategory", required = false) Integer idCategory,
			@RequestParam(value = "page", defaultValue = "1") int currentPage) {

		Init();

		// Lấy toàn bộ thông tin Category
		_mvShare.addObject("category", categoryService.getCategoryById(idCategory));

		// Lấy danh sách sản phẩm của danh mục
		List<ProductDTO> productsByCategory = categoryService.getProductsByCategory(idCategory);

		// Phân trang
		// Số sản phẩm trên mỗi trang
		int totalData = productsByCategory.size(); // Tổng số sản phẩm trong danh mục
		PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
		_mvShare.addObject("paginateInfo", paginateInfo);

		// Lấy sản phẩm cho trang hiện tại
		List<ProductDTO> productsPaginate = categoryService.GetDataProductPaginates(paginateInfo.getStart(),
				paginateInfo.getEnd(), "", idCategory, "", 0);
		Map<String, Double> price = productService.getMinMaxPrices();
		_mvShare.addObject("searchCategory", idCategory);
		_mvShare.addObject("priceMin", price.get("min"));
		_mvShare.addObject("priceMax", price.get("max"));
		_mvShare.addObject("ProductsPaginate", productsPaginate);

		_mvShare.setViewName("productsByCategory");
		return _mvShare; // View name
	}
}
