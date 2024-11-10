package LapFarm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String admin() {

		return "admin/index";
	}
	
	@RequestMapping(value = { "/menu" }, method = RequestMethod.GET)
	public String menu() {

		return "admin/menu";
	}
}
