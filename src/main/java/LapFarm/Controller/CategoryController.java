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
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class CategoryController extends BaseController {

	@Autowired
	private CategoryServiceImp categoryService;
	@Autowired
	private PaginatesServiceImp paginateService;
	private int totalProductPage = 9;

	@RequestMapping(value = "/products-category", params = "!page")
	public ModelAndView Index(@RequestParam(value = "idCategory", required = false) int idCategory) {

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

		// Lấy toàn bộ thông tin Category
		_mvShare.addObject("category", categoryService.getCategoryById(idCategory));

		_mvShare.addObject("AllProductByID", categoryService.getProductsByCategory(idCategory));

		int totalData = categoryService.getProductsByCategory(idCategory).size();
		PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
		_mvShare.addObject("paginateInfo", paginateInfo);
		_mvShare.addObject("ProductsPaginate",
				categoryService.GetDataProductPaginates(paginateInfo.getStart(), paginateInfo.getEnd(), "", 0, ""));
		_mvShare.setViewName("productsByCategory");
		return _mvShare; // The view name
	}

	@RequestMapping(value = "/products-category", params = "page")
	public ModelAndView Index(@RequestParam(value = "idCategory", required = false) Integer idCategory,
			@RequestParam(value = "page", defaultValue = "1") int currentPage) {

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
				paginateInfo.getEnd(), "", 0, "");
		_mvShare.addObject("ProductsPaginate", productsPaginate);

		_mvShare.setViewName("productsByCategory");
		return _mvShare; // View name
	}
}
