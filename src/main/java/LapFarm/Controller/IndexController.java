package LapFarm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import LapFarm.Bean.Mailer;
import LapFarm.DAO.BrandDAO;
import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ProductEntity;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class IndexController {
	@Autowired
	Mailer mailer;

	@Autowired
	private ServletContext context;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private BrandDAO brandDAO;
	
	@Autowired
	private ProductDAO productDAO;

	@RequestMapping(value = { "", "/", "/home" }, method = RequestMethod.GET)
	public String index(ModelMap model) {
		// Lấy danh sách Category
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
		model.addAttribute("categories", categories);

		// Lấy danh sách Brand
		List<BrandEntity> brands = brandDAO.getAllBrands();
		model.addAttribute("brands", brands);
		
		List<ProductEntity> products = productDAO.getAllProducts();
		model.addAttribute("products", products);
		return "store";
	}

	@RequestMapping("error")
	public String error() {
		return "error";
	}

	@RequestMapping(value = "/home/send")
	public String send(ModelMap model, @RequestParam("email") String email) {
		try {
			mailer.send(email, context);
			model.addAttribute("message", "Gửi email thành công !");
		} catch (Exception ex) {
			model.addAttribute("message", "Gửi email thất bại !");
		}
		return "redirect:/home";
	}
}
