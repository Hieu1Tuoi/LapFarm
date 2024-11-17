package LapFarm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import LapFarm.DAO.CategoryDAO;
import LapFarm.Entity.CategoryEntity;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
    private CategoryDAO categoryDAO;
	
	@RequestMapping(value = "/home" , method = RequestMethod.GET)
	public String index(ModelMap model) {
		// Lấy danh sách categories từ DAO
        List<CategoryEntity> categories = categoryDAO.getAllCategories();

        // Đưa danh sách vào Model để đẩy sang view
        model.addAttribute("categories", categories);
		return "/admin/index";
	}
	
	@RequestMapping(value = { "/orders" }, method = RequestMethod.GET)
	public String orders(ModelMap model) {
		// Lấy danh sách categories từ DAO
        List<CategoryEntity> categories = categoryDAO.getAllCategories();

        // Đưa danh sách vào Model để đẩy sang view
        model.addAttribute("categories", categories);
		return "/admin/orders";
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String products() {
		return "/admin/products";
	}
}
