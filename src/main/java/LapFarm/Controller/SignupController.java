package LapFarm.Controller;

import LapFarm.Bean.Mailer;
import LapFarm.DAO.UserDAO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.RoleEntity;
import jakarta.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

@Controller
public class SignupController {

	@Autowired
	Mailer mailer;

	@Autowired
	private UserDAO accountDAO;

	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String fogotpasswordForm() {
		return "signup";
	}

	
	@ResponseBody
	@PostMapping("/sendVerifyCode")
	public String sendVerifyCode(@RequestParam("email") String email, HttpSession session) {

		String code = mailer.VerifyCode(email);

		session.setAttribute("verificationCode", code);

		return "Mã xác minh đã được gửi: " + code;
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public String resetPassword(ModelMap model, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("verificationCode") String verificationCode, HttpSession verificationSession, 
			@RequestParam("g-recaptcha-response") String recaptchaResponse) {
		try {
	        // Verify reCAPTCHA
	        String secretKey = "6LcMHp8qAAAAAOfjRaga9eSFLoV7lkQHY8-vb9sj"; //Secret Key từ Google reCAPTCHA
	        String verifyUrl = "https://www.google.com/recaptcha/api/siteverify";
	        
	        RestTemplate restTemplate = new RestTemplate();
	        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
	        requestParams.add("secret", secretKey);
	        requestParams.add("response", recaptchaResponse);
	        
	        ResponseEntity<Map> response = restTemplate.postForEntity(verifyUrl, requestParams, Map.class);
	        Map<String, Object> responseBody = response.getBody();
	        
	        if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success"))) {
	            // reCAPTCHA validation passed
	            System.out.println("reCAPTCHA validation succeeded.");
	        } else {
	            model.addAttribute("warning", "reCAPTCHA không hợp lệ. Vui lòng thử lại!");
	            System.out.println("reCAPTCHA invalidation succeeded.");
	            return "forgotpassword";
	        }
	    } catch (Exception e) {
	        model.addAttribute("warning", "Lỗi khi xác thực reCAPTCHA: " + e.getMessage());
	        return "forgotpassword";
	    }
		String codeFromSession = (String) verificationSession.getAttribute("verificationCode");

		if (codeFromSession == null || !codeFromSession.equals(verificationCode)) {
			model.addAttribute("warning", "Mã xác nhận không chính xác!");
			model.addAttribute("email", email);
			model.addAttribute("pw", password);
			model.addAttribute("cfpw", confirmPassword);
			return "forgotpassword"; // Trang sửa mật khẩu
		}

		if (password.length() < 6) {
			model.addAttribute("warning", "Mật khẩu phải hơn 6 ký tự!");
			model.addAttribute("email", email);
			model.addAttribute("pw", password);
			model.addAttribute("cfpw", confirmPassword);
			return "forgotpassword"; // Trang sửa mật khẩu
		} else if (!password.equals(confirmPassword)) {
			model.addAttribute("warning", "Xác nhận mật khẩu không giống nhau!");
			model.addAttribute("email", email);
			model.addAttribute("pw", password);
			model.addAttribute("cfpw", confirmPassword);
			return "forgotpassword"; // Trang sửa mật khẩu
		}

		try {
			if (!accountDAO.checkEmailExists(email)) {
				model.addAttribute("warning", "Email không tồn tại trong hệ thống!");
				model.addAttribute("email", email);
				model.addAttribute("pw", password);
				model.addAttribute("cfpw", confirmPassword);
				return "forgotpassword";
			}

			accountDAO.updatePassword(email, password);

			model.addAttribute("message", "Mật khẩu đã được cập nhật thành công!");
		} catch (Exception e) {
			model.addAttribute("warning", "Cập nhật mật khẩu thất bại! " + e.getMessage());
		}

		return "redirect:/login";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(ModelMap model, @RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("verificationCode") String verificationCode, @ModelAttribute("account") AccountEntity acc,
			HttpSession verificationSession, 
			@RequestParam("g-recaptcha-response") String recaptchaResponse) {
		try {
	        // Verify reCAPTCHA
	        String secretKey = "6LcMHp8qAAAAAOfjRaga9eSFLoV7lkQHY8-vb9sj"; //Secret Key từ Google reCAPTCHA
	        String verifyUrl = "https://www.google.com/recaptcha/api/siteverify";
	        
	        RestTemplate restTemplate = new RestTemplate();
	        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
	        requestParams.add("secret", secretKey);
	        requestParams.add("response", recaptchaResponse);
	        
	        ResponseEntity<Map> response = restTemplate.postForEntity(verifyUrl, requestParams, Map.class);
	        Map<String, Object> responseBody = response.getBody();
	        
	        if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success"))) {
	            // reCAPTCHA validation passed
	            System.out.println("reCAPTCHA validation succeeded.");
	        } else {
	            model.addAttribute("warning", "reCAPTCHA không hợp lệ. Vui lòng thử lại!");
	            System.out.println("reCAPTCHA invalidation succeeded.");
	            return "signup";
	        }
	    } catch (Exception e) {
	        model.addAttribute("warning", "Lỗi khi xác thực reCAPTCHA: " + e.getMessage());
	        return "signup";
	    }
		String codeFromSession = (String) verificationSession.getAttribute("verificationCode");

		if (codeFromSession == null || !codeFromSession.equals(verificationCode)) {
			model.addAttribute("warning", "Mã xác nhận không chính xác!");
			model.addAttribute("email", email);
			model.addAttribute("pw", password);
			model.addAttribute("cfpw", confirmPassword);
			return "signup";
		}

		if (password.length() < 6) {
			model.addAttribute("warning", "Mật khẩu phải hơn 6 ký tự!");
			model.addAttribute("email", email);
			model.addAttribute("pw", password);
			model.addAttribute("cfpw", confirmPassword);
			return "signup";
		} else if (!password.equals(confirmPassword)) {
			model.addAttribute("warning", "Xác nhận mật khẩu không giống nhau!");
			model.addAttribute("email", email);
			model.addAttribute("pw", password);
			model.addAttribute("cfpw", confirmPassword);
			return "signup";
		}

		try {
			// Check if email already exists
			if (accountDAO.checkEmailExists(email)) {
				model.addAttribute("warning", "Email đã tồn tại!");
				model.addAttribute("email", email);
				model.addAttribute("pw", password);
				model.addAttribute("cfpw", confirmPassword);
				return "signup";
			}

			accountDAO.saveAccount(acc);
			accountDAO.createUserinfo(email);

			model.addAttribute("message", "Đăng ký thành công!");
		} catch (Exception e) {
			model.addAttribute("warning", "Đăng ký thất bại! " + e.getMessage());
		}

		return "signup";
	}

}
