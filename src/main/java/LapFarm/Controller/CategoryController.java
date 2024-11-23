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
public class CategoryController {
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private CategoryServiceImp categoryService;
    @Autowired
    private PaginatesServiceImp paginateService;
    
    @Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private BrandDAO brandDAO;
	
	private int totalProductPage = 9;

    @RequestMapping(value = "/products-category", params = "!page")
    public String getProductsByCategory(@RequestParam(value = "idCategory", required = false) Integer idCategory, Model model) {
       
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
	    CategoryEntity category = categoryDAO.getCategoryById(idCategory);
	    model.addAttribute("category", category);
    
	    model.addAttribute("AllProductByID", categoryService.getProductsByCategory(idCategory));
    
	    
	    int totalData = categoryService.getProductsByCategory(idCategory).size();
	    PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
	    model.addAttribute("paginateInfo", paginateInfo);
        model.addAttribute("ProductsPaginate", categoryService.GetDataProductPaginates(paginateInfo.getStart(), paginateInfo.getEnd()));
        return "productsByCategory"; // The view name
    }
    
    
    @RequestMapping(value = "/products-category", params = "page")
    public String getProductsByCategory(
            @RequestParam(value = "idCategory", required = false) Integer idCategory,
            @RequestParam(value = "page", defaultValue = "1") int currentPage,
            Model model) {
        
        // Lấy danh sách Category
        List<CategoryEntity> categories = categoryDAO.getAllCategories();
        model.addAttribute("categories", categories);

        // Lấy danh sách Brand
        List<BrandEntity> brands = brandDAO.getAllBrands();
        model.addAttribute("brands", brands);

        // Lấy sản phẩm bán chạy
        List<ProductDTO> productsTopSell = productDAO.getTop5ProductsByLowestQuantity();
        model.addAttribute("products_top_sell", productsTopSell);

        // Lấy số lượng sản phẩm theo danh mục
        Map<Integer, Long> productCounts = productDAO.getProductCountByAllCategories(categories);
        model.addAttribute("productCounts", productCounts);

        // Lấy số lượng sản phẩm theo thương hiệu
        Map<Integer, Long> productCountsByBrand = productDAO.getProductCountByAllBrands(brands);
        model.addAttribute("productCountsByBrand", productCountsByBrand);

        // Lấy thông tin danh mục
        CategoryEntity category = categoryDAO.getCategoryById(idCategory);
        model.addAttribute("category", category);

        // Lấy danh sách sản phẩm của danh mục
        List<ProductDTO> productsByCategory = categoryService.getProductsByCategory(idCategory);

        // Phân trang
       // Số sản phẩm trên mỗi trang
        int totalData = productsByCategory.size(); // Tổng số sản phẩm trong danh mục
        PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
        model.addAttribute("paginateInfo", paginateInfo);

        System.out.println("Current Page: " + currentPage);
        System.out.println("Total Data: " + totalData);
        System.out.println("Start: " + paginateInfo.getStart());
        System.out.println("End: " + paginateInfo.getEnd());
        // Lấy sản phẩm cho trang hiện tại
        List<ProductDTO> productsPaginate = categoryService.GetDataProductPaginates(
                paginateInfo.getStart(), paginateInfo.getEnd());
        model.addAttribute("ProductsPaginate", productsPaginate);

        return "productsByCategory"; // View name
    }

}

