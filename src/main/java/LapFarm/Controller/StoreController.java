package LapFarm.Controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import LapFarm.DAO.BrandDAO;
import LapFarm.DAO.CategoryDAO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import jakarta.transaction.Transactional;

@Transactional
@Controller
public class StoreController {
	@Autowired
	SessionFactory factory;
	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private BrandDAO brandDAO;

	@RequestMapping(value = { "/pages", "/pages/*" }, method = RequestMethod.GET)
	public String pages(ModelMap model) {
		// Lấy danh sách Category
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
		model.addAttribute("categories", categories);

		// Lấy danh sách Brand
		List<BrandEntity> brands = brandDAO.getAllBrands();
		model.addAttribute("brands", brands);
		return "store";
	}

}
