package LapFarm.Controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String index(HttpSession httpSession, Model model) {
		
		Map<String, Object> order = (Map<String, Object>) httpSession.getAttribute("order");
		if(order == null) {
			return "redirect:/";
		}
		
		model.addAttribute("order", order.get("order"));
		
		model.addAttribute("orderdetail", order.get("orderdetail"));
		
		return "user/orders/detail";
	}
}
