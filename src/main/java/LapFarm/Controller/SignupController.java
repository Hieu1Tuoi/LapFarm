package LapFarm.Controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import LapFarm.Bean.Mailer;
import LapFarm.DAO.UserDAO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Utils.ValidationUtils;
import LapFarm.Utils.ValidationUtils.ValidationResult;
import jakarta.servlet.http.HttpSession;


@Controller
public class SignupController {

	@Autowired
	Mailer mailer;

	@Autowired
	private UserDAO accountDAO;

	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory factory;
	
	@Autowired
	@Qualifier("sessionFactoryUser")
	SessionFactory factoryUser;
	
	@Autowired
	@Qualifier("sessionFactoryVisitor")
	SessionFactory factoryVisitor;

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

	

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(ModelMap model, @RequestParam("password") String password, @RequestParam("email") String email,
	        @RequestParam("confirmPassword") String confirmPassword,
	        @RequestParam("verificationCode") String verificationCode, @ModelAttribute("account") AccountEntity acc,
	        HttpSession verificationSession, 
	        @RequestParam("g-recaptcha-response") String recaptchaResponse) {
	    try {
	    	
	    	
	        // Verify reCAPTCHA
	        String secretKey = "6LcMHp8qAAAAAOfjRaga9eSFLoV7lkQHY8-vb9sj"; // Secret Key từ Google reCAPTCHA
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
	            return "signup";
	        }
	    } catch (Exception e) {
	        model.addAttribute("warning", "Lỗi khi xác thực reCAPTCHA: " + e.getMessage());
	        return "signup";
	    }

	    // Kiểm tra mã xác minh
	    String codeFromSession = (String) verificationSession.getAttribute("verificationCode");
	    if (codeFromSession == null || !codeFromSession.equals(verificationCode)) {
	        model.addAttribute("warning", "Mã xác nhận không chính xác!");
	        model.addAttribute("email", email);
	        model.addAttribute("pw", password);
	        model.addAttribute("cfpw", confirmPassword);
	        return "signup";
	    }

		/*
		 * // Kiểm tra độ dài mật khẩu và mật khẩu bằng regex String passwordRegex =
		 * "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
		 * if (!password.matches(passwordRegex)) { model.addAttribute("warning",
		 * "Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và kí tự đặc biệt!"
		 * ); model.addAttribute("email", email); model.addAttribute("pw", password);
		 * model.addAttribute("cfpw", confirmPassword); return "signup"; } else if
		 * (!password.equals(confirmPassword)) { model.addAttribute("warning",
		 * "Xác nhận mật khẩu không giống nhau!"); model.addAttribute("email", email);
		 * model.addAttribute("pw", password); model.addAttribute("cfpw",
		 * confirmPassword); return "signup"; }
		 */
	 // Validate email first
        ValidationResult emailValidation = ValidationUtils.validateEmail(email);
        if (!emailValidation.isValid()) {
            model.addAttribute("warning", emailValidation.getMessage());
            addToModel(model, email, password, confirmPassword);
            return "signup";
        }

        // Sanitize email input
        String sanitizedEmail = ValidationUtils.safeSetString(email, ValidationUtils.MAX_EMAIL_LENGTH);
        acc.setEmail(sanitizedEmail);
        //Validate password
	    ValidationResult passwordValidation = ValidationUtils.validatePassword(password, confirmPassword);
        if (!passwordValidation.isValid()) {
            model.addAttribute("warning", passwordValidation.getMessage());
            addToModel(model, email, password, confirmPassword);
            return "signup";
        }

        // Sanitize password input
        String sanitizedPassword = ValidationUtils.safeSetString(password, ValidationUtils.MAX_PASSWORD_LENGTH);
        acc.setPassword(sanitizedPassword);

	    try {
	        // Kiểm tra email đã tồn tại chưa
	        if (accountDAO.checkEmailExists(sanitizedEmail)) {
	            model.addAttribute("warning", "Email đã tồn tại!");
	            model.addAttribute("email", email);
	            model.addAttribute("pw", password);
	            model.addAttribute("cfpw", confirmPassword);
	            return "signup";
	        }

	        // Cập nhật thời gian đổi mật khẩu trước khi lưu tài khoản
	        acc.setLastPasswordChangeDate(LocalDateTime.now());  // Cập nhật thời gian đổi mật khẩu

	        accountDAO.saveAccount(acc);  // Lưu tài khoản vào database
	        accountDAO.createUserinfo(sanitizedEmail);  // Lưu thông tin người dùng

	        model.addAttribute("message", "Đăng ký thành công!");
	    } catch (Exception e) {
	        model.addAttribute("warning", "Đăng ký thất bại! " + e.getMessage());
	    }

	    return "signup";
	}
	 private void addToModel(ModelMap model, String email, String password, String confirmPassword) {
	        model.addAttribute("email", email);
	        model.addAttribute("pw", password);
	        model.addAttribute("cfpw", confirmPassword);
	    }

}
