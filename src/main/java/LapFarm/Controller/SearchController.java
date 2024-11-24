package LapFarm.Controller;

import java.util.List;
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
	public ModelAndView Index(@RequestParam("category") String category,
			@RequestParam("searchtext") String searchText) {

		// Lấy danh sách Category
		_mvShare.addObject("categories", _baseService.getCategoryEntities());

		// Lấy danh sách Brand
		_mvShare.addObject("brands", _baseService.getBrandEntities());

		_mvShare.addObject("products", productService.getAllProductsDTO());

		// Lấy số lượng sản phẩm theo tất cả danh mục
		_mvShare.addObject("productCounts",
				_baseService.getProductCountByAllCategories(_baseService.getCategoryEntities())); // Truyền Map vào

		// Lấy số lượng sản phẩm theo tất cả brand
		_mvShare.addObject("productCountsByBrand",
				_baseService.getProductCountByAllBrands(_baseService.getBrandEntities()));

		_mvShare.addObject("products_top_sell", _baseService.getTop5ProductsByLowestQuantity());

		// Thêm vào model để hiển thị trên view
		_mvShare.addObject("totalQuantity", productService.getTotalProductQuantity());

		int totalData = filter(productService.getAllProductsDTO(), searchText, Integer.valueOf(category)).size();
		PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
		_mvShare.addObject("paginateInfo", paginateInfo);

		List<ProductDTO> allProducts = productService.GetDataProductPaginates(paginateInfo.getStart(),
				paginateInfo.getEnd(), searchText, Integer.valueOf(category));

		// Kiểm tra searchText và lọc danh sách sản phẩm

		_mvShare.addObject("searchText", searchText);
		_mvShare.addObject("searchCategory", category);
		_mvShare.addObject("ProductsPaginate", allProducts);
		_mvShare.setViewName("search");
		return _mvShare;
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, params = "page")
	public ModelAndView Index(@RequestParam("category") String category, @RequestParam("searchtext") String searchText,
			@RequestParam(value = "page", defaultValue = "1") int currentPage) {
		// Lấy danh sách Category
		_mvShare.addObject("categories", _baseService.getCategoryEntities());

		// Lấy danh sách Brand
		_mvShare.addObject("brands", _baseService.getBrandEntities());

		_mvShare.addObject("products", productService.getAllProductsDTO());

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

		int totalData = filter(productService.getAllProductsDTO(), searchText, Integer.valueOf(category)).size();
		PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
		_mvShare.addObject("paginateInfo", paginateInfo);

		List<ProductDTO> allProducts = productService.GetDataProductPaginates(paginateInfo.getStart(),
				paginateInfo.getEnd(), searchText, Integer.valueOf(category));

		// Kiểm tra searchText và lọc danh sách sản phẩm

		_mvShare.addObject("searchText", searchText);
		_mvShare.addObject("searchCategory", category);
		_mvShare.addObject("ProductsPaginate", allProducts);
		_mvShare.setViewName("search");
		return _mvShare;
	}

	public List<ProductDTO> filter(List<ProductDTO> list, String searchText, int idCategory) {
		// Nếu không có điều kiện tìm kiếm (searchText và idCategory = 0), trả về toàn
		// bộ danh sách
		if ((searchText == null || searchText.trim().isEmpty()) && idCategory == 0) {
			return list;
		}

		// Thực hiện lọc theo các tiêu chí
		return list.stream().filter(product -> {
			boolean matchesSearchText = (searchText == null || searchText.trim().isEmpty())
					|| product.getNameProduct().toLowerCase().contains(searchText.toLowerCase());
			boolean matchesCategory = (idCategory == 0) || (product.getIdCategory() == idCategory);
			return matchesSearchText && matchesCategory;
		}).collect(Collectors.toList());
	}

}
