package LapFarm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String index() {

		return "admin/index";
	}
	
	@RequestMapping(value = { "/menu", "/menu/" }, method = RequestMethod.GET)
	public String menu() {

		return "admin/menu";
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String products() {
		return "/admin/products";
	}
}
