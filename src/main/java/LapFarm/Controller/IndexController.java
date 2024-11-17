package LapFarm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import LapFarm.Bean.Mailer;
import jakarta.servlet.ServletContext;

@Controller
public class IndexController {
	@Autowired
	Mailer mailer;
	
	@Autowired
    private ServletContext context;

	@RequestMapping(value = { "", "/", "/home" }, method = RequestMethod.GET)
	public String index() {

		return "index";
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
