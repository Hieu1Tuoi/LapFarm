package LapFarm.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	    // Lấy thông tin Category
	    _mvShare.addObject("category", categoryService.getCategoryById(idCategory));

	    // Lấy danh sách sản phẩm theo idCategory
	    List<ProductDTO> allProducts = categoryService.getProductsByCategory(idCategory);
	    _mvShare.addObject("AllProductByID", allProducts);
	    
	    // Tính tổng số sản phẩm và phân trang
	    int totalData = allProducts.size();
	    PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
	    _mvShare.addObject("paginateInfo", paginateInfo);

	    // Lấy sản phẩm phân trang
	    List<ProductDTO> productsPaginate = categoryService.GetDataProductPaginates(paginateInfo.getStart(),
	                                                                                paginateInfo.getEnd(), "", idCategory, "", 0);
	    
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


	@RequestMapping(value = "/products-category", params = "page")
	public ModelAndView Index(@RequestParam(value = "idCategory", required = false) Integer idCategory,
	                          @RequestParam(value = "page", defaultValue = "1") int currentPage) {

	    Init();

	    // Lấy thông tin Category
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

	    // Lấy rating summary cho tất cả các sản phẩm trong danh mục
	    List<Integer> productIds = productsPaginate.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());
	    List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);

	    // Gán rating vào từng sản phẩm
	    for (ProductDTO product : productsPaginate) {
	        for (Map<String, Object> summary : ratingSummaries) {
	            if (product.getIdProduct() == (int) summary.get("productId")) {
	                product.setRatingSummary(summary);
	            }
	        }
	    }

	    // Truyền sản phẩm đã có rating vào model
	    _mvShare.addObject("ProductsPaginate", productsPaginate);

	    // Lấy phạm vi giá (min, max)
	    Map<String, Double> price = productService.getMinMaxPrices();
	    _mvShare.addObject("searchCategory", idCategory);
	    _mvShare.addObject("priceMin", price.get("min"));
	    _mvShare.addObject("priceMax", price.get("max"));

	    // Đặt view cho ModelAndView
	    _mvShare.setViewName("productsByCategory");
	    return _mvShare;
	}

}
