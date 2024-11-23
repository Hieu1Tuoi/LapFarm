package LapFarm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.OrdersDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ProductEntity;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
    private CategoryDAO categoryDAO;
	@Autowired
	private OrdersDAO ordersDAO;
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(value = "/home" , method = RequestMethod.GET)
	public String index(ModelMap model) {
		// Lấy danh sách categories từ DAO
        List<CategoryEntity> categories = categoryDAO.getAllCategories();

        // Đưa danh sách vào Model để đẩy sang view
        model.addAttribute("categories", categories);
		return "/admin/index";
	}
	
	@RequestMapping(value = { "/orders" }, method = RequestMethod.GET)
	public String ordersIndex(ModelMap model) {
		// Lấy danh sách categories từ DAO
        List<CategoryEntity> categories = categoryDAO.getAllCategories();
        List<OrdersDTO> orders = ordersDAO.getAllOrdersWithUserFullname();

        // Đưa danh sách vào Model để đẩy sang view
        model.addAttribute("categories", categories);
        model.addAttribute("orders", orders);
		return "/admin/orders/index";
	}
	
	@RequestMapping(value = { "/product" }, method = RequestMethod.GET)
	public String categoryIndex(@RequestParam("category") int id, ModelMap model) {
	    // Lấy danh sách từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
	    List<ProductDTO> products = productDAO.getProductsByCategory(id);
	    // Đưa danh sách vào Model để đẩy sang view
	    model.addAttribute("categories", categories);
	    model.addAttribute("products", products); // Sản phẩm của danh mục

	    // Trả về view cho trang quản lý sản phẩm của danh mục
	    return "/admin/products/category";
	}
}
