package LapFarm.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import LapFarm.DAO.BrandDAO;
import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ProductEntity;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class BrandController {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private BrandDAO brandDAO;


    @RequestMapping(value = "/products-brand", method = RequestMethod.GET)
    public String getProductsByBrand(@RequestParam(value = "idBrand", required = false) Integer idBrand, Model model) {
       
    	// Lấy danh sách Category
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
		model.addAttribute("categories", categories);

		// Lấy danh sách Brand
		List<BrandEntity> brands = brandDAO.getAllBrands();
		model.addAttribute("brands", brands);
		
	    List<ProductDTO> products_top_sell = productDAO.getTop5ProductsByLowestQuantity();
		model.addAttribute("products_top_sell", products_top_sell);
		
		   // Lấy số lượng sản phẩm theo tất cả danh mục
	    Map<Integer, Long> productCounts = productDAO.getProductCountByAllCategories(categories);
	    model.addAttribute("productCounts", productCounts); // Truyền Map vào Model
	    

	 // Lấy số lượng sản phẩm theo tất cả brand
	    Map<Integer, Long> productCountsByBrand = productDAO.getProductCountByAllBrands(brands);
	    model.addAttribute("productCountsByBrand", productCountsByBrand);
	    
	    // Lấy toàn bộ thông tin Category
	    BrandEntity brand = brandDAO.getBrandById(idBrand);
	    model.addAttribute("brand", brand);
    	List<ProductEntity> products = productDAO.getProductsByBrandId(idBrand);
	   
    	 model.addAttribute("productsByBrand", products);
 	    
        return "productsByBrand"; // The view name
    }
}

