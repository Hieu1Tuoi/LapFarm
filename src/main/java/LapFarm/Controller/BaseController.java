package LapFarm.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.Service.BaseServiceImp;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;


@Transactional
@Controller
public class BaseController {
	@Autowired
	BaseServiceImp _baseService;

	public ModelAndView _mvShare = new ModelAndView();
	
	
	
	public ModelAndView Init() {
		// Lấy danh sách Category
		_mvShare.addObject("categories", _baseService.getCategoryEntities());

		// Lấy danh sách Brand
		_mvShare.addObject("brands", _baseService.getBrandEntities());

	

		// Lấy số lượng sản phẩm theo tất cả danh mục
		_mvShare.addObject("productCounts",
				_baseService.getProductCountByAllCategories(_baseService.getCategoryEntities())); // Truyền Map vào
																									// Model

		// Lấy số lượng sản phẩm theo tất cả brand
		_mvShare.addObject("productCountsByBrand",
				_baseService.getProductCountByAllBrands(_baseService.getBrandEntities()));

		_mvShare.addObject("products_top_sell", _baseService.getTop5ProductsByLowestQuantity());
		return _mvShare;
	}

}
