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
import LapFarm.Service.BrandServiceImp;
import LapFarm.Service.CategoryServiceImp;
import LapFarm.Service.PaginatesServiceImp;
import LapFarm.Service.ProductServiceImp;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class BrandController extends BaseController {

	@Autowired
	private BrandServiceImp brandService;
	@Autowired
	ProductServiceImp productService;
	@Autowired
	private PaginatesServiceImp paginateService;
	private int totalProductPage = 9;


	@RequestMapping(value = "/products-brand", params = "!page")
	public ModelAndView Index(@RequestParam(value = "idBrand", required = false) Integer idBrand,
	                          @RequestParam(value = "nameBrand", required = false) String nameBrand) {
	    Init();

	    // Kiểm tra xem có idBrand hoặc nameBrand không
	    if (idBrand != null) {
	        // Lấy thông tin thương hiệu và danh sách sản phẩm theo idBrand
	        _mvShare.addObject("brand", brandService.getBrandById(idBrand));
	        _mvShare.addObject("AllProductByID", brandService.getProductsByBrand(idBrand));
	        
	        int totalData = brandService.getProductsByBrand(idBrand).size();
	        PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
	        _mvShare.addObject("paginateInfo", paginateInfo);

	        // Lấy sản phẩm phân trang
	        List<ProductDTO> allProducts = brandService.GetDataProductPaginates(paginateInfo.getStart(),
	                                                                           paginateInfo.getEnd(), "", 0, "", idBrand);
	        
	        // Lấy rating cho các sản phẩm theo idBrand
	        List<Integer> productIds = allProducts.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());
	        List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);

	        // Gán rating summary vào từng sản phẩm trong danh sách phân trang
	        for (ProductDTO product : allProducts) {
	            for (Map<String, Object> summary : ratingSummaries) {
	                if (product.getIdProduct() == (int) summary.get("productId")) {
	                    product.setRatingSummary(summary);
	                }
	            }
	        }

	        // Truyền danh sách sản phẩm đã có rating vào Model
	        _mvShare.addObject("ProductsPaginate", allProducts);
	    } else if (nameBrand != null) {
	        // Lấy thông tin thương hiệu và danh sách sản phẩm theo nameBrand
	        _mvShare.addObject("brand", brandService.getBrandByName(nameBrand));
	        _mvShare.addObject("AllProductByName", brandService.getProductsByBrandName(nameBrand));
	        
	        int totalData = brandService.getProductsByBrandName(nameBrand).size();
	        PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
	        _mvShare.addObject("paginateInfo", paginateInfo);

	        // Lấy sản phẩm phân trang
	        List<ProductDTO> allProducts = brandService.GetDataProductPaginates(paginateInfo.getStart(),
	                                                                           paginateInfo.getEnd(), "", 0, "", idBrand);
	        
	        // Lấy rating cho các sản phẩm theo nameBrand
	        List<Integer> productIds = allProducts.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());
	        List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);

	        // Gán rating summary vào từng sản phẩm trong danh sách phân trang
	        for (ProductDTO product : allProducts) {
	            for (Map<String, Object> summary : ratingSummaries) {
	                if (product.getIdProduct() == (int) summary.get("productId")) {
	                    product.setRatingSummary(summary);
	                }
	            }
	        }

	        // Truyền danh sách sản phẩm đã có rating vào Model
	        _mvShare.addObject("ProductsPaginate", allProducts);
	    }

	    // Truyền giá trị min và max cho phạm vi giá
	    Map<String, Double> price = productService.getMinMaxPrices();
	    _mvShare.addObject("idBrand", idBrand);
	    _mvShare.addObject("priceMin", price.get("min"));
	    _mvShare.addObject("priceMax", price.get("max"));
	    
	    // Đặt view cho ModelAndView
	    _mvShare.setViewName("productsByBrand");
	    return _mvShare;
	}


	@RequestMapping(value = "/products-brand", params = "page")
	public ModelAndView Index(@RequestParam(value = "idBrand", required = false) Integer idBrand,
	                          @RequestParam(value = "nameBrand", required = false) String nameBrand,
	                          @RequestParam(value = "page", defaultValue = "1") int currentPage) {
	    Init();

	    if (idBrand != null) {
	        // Lấy thông tin thương hiệu và tất cả sản phẩm theo idBrand
	        _mvShare.addObject("brand", brandService.getBrandById(idBrand));
	        _mvShare.addObject("AllProductByID", brandService.getProductsByBrand(idBrand));
	        
	        // Tính tổng số sản phẩm và phân trang
	        int totalData = brandService.getProductsByBrand(idBrand).size();
	        PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
	        _mvShare.addObject("paginateInfo", paginateInfo);

	        // Lấy sản phẩm phân trang
	        List<ProductDTO> allProducts = brandService.GetDataProductPaginates(paginateInfo.getStart(),
	                                                                           paginateInfo.getEnd(), "", 0, "", idBrand);
	        
	        // Lấy rating cho các sản phẩm theo idBrand
	        List<Integer> productIds = allProducts.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());
	        List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);

	        // Gán rating summary vào từng sản phẩm trong danh sách phân trang
	        for (ProductDTO product : allProducts) {
	            for (Map<String, Object> summary : ratingSummaries) {
	                if (product.getIdProduct() == (int) summary.get("productId")) {
	                    product.setRatingSummary(summary);
	                }
	            }
	        }

	        // Truyền danh sách sản phẩm đã có rating vào Model
	        _mvShare.addObject("ProductsPaginate", allProducts);
	    } else if (nameBrand != null) {
	        // Lấy thông tin thương hiệu và tất cả sản phẩm theo nameBrand
	        _mvShare.addObject("brand", brandService.getBrandByName(nameBrand));
	        _mvShare.addObject("AllProductByName", brandService.getProductsByBrandName(nameBrand));
	        
	        // Tính tổng số sản phẩm và phân trang
	        int totalData = brandService.getProductsByBrandName(nameBrand).size();
	        PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
	        _mvShare.addObject("paginateInfo", paginateInfo);

	        // Lấy sản phẩm phân trang
	        List<ProductDTO> allProducts = brandService.GetDataProductPaginates(paginateInfo.getStart(),
	                                                                           paginateInfo.getEnd(), "", 0, "", idBrand);

	        // Lấy rating cho các sản phẩm theo nameBrand
	        List<Integer> productIds = allProducts.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());
	        List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);

	        // Gán rating summary vào từng sản phẩm trong danh sách phân trang
	        for (ProductDTO product : allProducts) {
	            for (Map<String, Object> summary : ratingSummaries) {
	                if (product.getIdProduct() == (int) summary.get("productId")) {
	                    product.setRatingSummary(summary);
	                }
	            }
	        }

	        // Truyền danh sách sản phẩm đã có rating vào Model
	        _mvShare.addObject("ProductsPaginate", allProducts);
	    }

	    // Truyền giá trị min và max cho phạm vi giá
	    Map<String, Double> price = productService.getMinMaxPrices();
	    _mvShare.addObject("idBrand", idBrand);
	    _mvShare.addObject("priceMin", price.get("min"));
	    _mvShare.addObject("priceMax", price.get("max"));
	    
	    // Đặt view cho ModelAndView
	    _mvShare.setViewName("productsByBrand");
	    return _mvShare;
	}


}
