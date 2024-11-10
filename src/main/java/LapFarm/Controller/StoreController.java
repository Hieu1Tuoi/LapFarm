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

import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import jakarta.transaction.Transactional;

@Transactional
@Controller
public class StoreController {
	@Autowired
	SessionFactory factory;

	@RequestMapping(value = { "/pages", "/pages/*" }, method = RequestMethod.GET)
	public String pages(ModelMap model) {
		 Session session = factory.getCurrentSession();

		    // Lấy danh sách Category
		    String hqlCategory = "FROM CategoryEntity";
		    Query<CategoryEntity> queryCategory = session.createQuery(hqlCategory, CategoryEntity.class);
		    List<CategoryEntity> categories = queryCategory.list();
		    model.addAttribute("categories", categories);

		    // Lấy danh sách Brand
		    String hqlBrand = "FROM BrandEntity"; // Giả sử bạn có thực thể BrandEntity
		    Query<BrandEntity> queryBrand = session.createQuery(hqlBrand, BrandEntity.class);
		    List<BrandEntity> brands = queryBrand.list();
		    model.addAttribute("brands", brands);
		return "store";
	}

}
