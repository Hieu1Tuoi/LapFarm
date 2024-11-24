package LapFarm.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.Service.BaseServiceImp;
import jakarta.transaction.Transactional;



@Transactional
@Controller
public class BaseController {
	@Autowired
	BaseServiceImp _baseService;

	public ModelAndView _mvShare = new ModelAndView();
	
//	@PostConstruct
//	public ModelAndView Init() {
//		_mvShare.addObject("menus", _homeService.getDataMenus());
//		return _mvShare;
//	}

}
