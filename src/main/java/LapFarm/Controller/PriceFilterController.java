package LapFarm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.DTO.PaginatesDto;
import LapFarm.DTO.ProductDTO;
import LapFarm.Service.PaginatesServiceImp;
import LapFarm.Service.ProductServiceImp;
import jakarta.transaction.Transactional;
@Controller
@Transactional
public class PriceFilterController extends BaseController {
	

	@Autowired
	ProductServiceImp productService;
	@Autowired
	private PaginatesServiceImp paginateService;
	private int totalProductPage = 9;
	
	@RequestMapping(value="/products-price", params = "!page")
	public ModelAndView filterProductsByPrice(@RequestParam(value = "priceRange", required = false) String priceRange) {
	    // Xử lý phạm vi giá
	    String[] range = priceRange.split("-");
	    int minPrice = Integer.parseInt(range[0]);
	    int maxPrice = Integer.parseInt(range[1]);

	    // Truy vấn sản phẩm theo khoảng giá
	    List<ProductDTO> filteredProducts;
	    if (maxPrice == 0) {
	        filteredProducts = productService.findProductsByPriceGreaterThan(minPrice);
	    } else {
	        filteredProducts = productService.findProductsByPriceRange(minPrice, maxPrice);
	    }

	    // Lấy danh sách Category
	    _mvShare.addObject("categories", _baseService.getCategoryEntities());

	    // Lấy danh sách Brand
	    _mvShare.addObject("brands", _baseService.getBrandEntities());

	    // Lấy số lượng sản phẩm theo tất cả danh mục
	    _mvShare.addObject("productCounts", _baseService.getProductCountByAllCategories(_baseService.getCategoryEntities()));

	    // Lấy số lượng sản phẩm theo tất cả brand
	    _mvShare.addObject("productCountsByBrand", _baseService.getProductCountByAllBrands(_baseService.getBrandEntities()));

	    // Lấy danh sách sản phẩm bán chạy nhất
	    _mvShare.addObject("products_top_sell", _baseService.getTop5ProductsByLowestQuantity());

	    // Đưa danh sách sản phẩm vào model
	    _mvShare.addObject("products", filteredProducts);

	    int totalData = filteredProducts.size();
	    PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
	    _mvShare.addObject("paginateInfo", paginateInfo);
	    _mvShare.addObject("ProductsPaginate", productService.GetDataProductPaginates(paginateInfo.getStart(), paginateInfo.getEnd()));
	    _mvShare.setViewName("price_filter");
	    return _mvShare;
	}

	@RequestMapping(value="/products-price", params = "page")
	public ModelAndView filterProductsByPrice(@RequestParam(value = "priceRange", required = false) String priceRange,
			@RequestParam(value = "page", defaultValue = "1") int currentPage) {
	    // Xử lý phạm vi giá
	    String[] range = priceRange.split("-");
	    int minPrice = Integer.parseInt(range[0]);
	    int maxPrice = Integer.parseInt(range[1]);

	    // Truy vấn sản phẩm theo khoảng giá
	    List<ProductDTO> filteredProducts;
	    if (maxPrice == 0) {
	        filteredProducts = productService.findProductsByPriceGreaterThan(minPrice);
	    } else {
	        filteredProducts = productService.findProductsByPriceRange(minPrice, maxPrice);
	    }

	    // Lấy danh sách Category
	    _mvShare.addObject("categories", _baseService.getCategoryEntities());

	    // Lấy danh sách Brand
	    _mvShare.addObject("brands", _baseService.getBrandEntities());

	    // Lấy số lượng sản phẩm theo tất cả danh mục
	    _mvShare.addObject("productCounts", _baseService.getProductCountByAllCategories(_baseService.getCategoryEntities()));

	    // Lấy số lượng sản phẩm theo tất cả brand
	    _mvShare.addObject("productCountsByBrand", _baseService.getProductCountByAllBrands(_baseService.getBrandEntities()));

	    // Lấy danh sách sản phẩm bán chạy nhất
	    _mvShare.addObject("products_top_sell", _baseService.getTop5ProductsByLowestQuantity());

	    // Đưa danh sách sản phẩm vào model
	    _mvShare.addObject("products", filteredProducts);

	    int totalData = filteredProducts.size();
	    PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
	    _mvShare.addObject("paginateInfo", paginateInfo);
	    _mvShare.addObject("ProductsPaginate", productService.GetDataProductPaginates(paginateInfo.getStart(), paginateInfo.getEnd()));
	    _mvShare.setViewName("price_filter");
	    return _mvShare;
	}

	
}
