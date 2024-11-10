package LapFarm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StoreController {

	@RequestMapping(value = {"/pages", "/pages/*"}, method = RequestMethod.GET)
	public String pages() {

		return "store";
	}
	
}
