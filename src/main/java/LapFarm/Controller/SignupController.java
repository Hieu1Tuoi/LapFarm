package LapFarm.Controller;

import LapFarm.Bean.Mailer;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.RoleEntity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

@Controller
public class SignupController {

	@Autowired
	Mailer mailer;
	
	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(ModelMap model, @RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("confirmPassword") String confirmPassword, @ModelAttribute("account") AccountEntity acc) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

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
		mailer.VerifyCode(email);
		return "signup";
//		try {
//			
//			String hql = "FROM AccountEntity WHERE email = :email";
//			Query query = session.createQuery(hql);
//			query.setParameter("email", acc.getEmail());
//
//			AccountEntity existingAccount = (AccountEntity) query.uniqueResult();
//
//			if (existingAccount != null) {
//				model.addAttribute("warning", "Email đã tồn tại!");
//				model.addAttribute("email", email);
//				model.addAttribute("pw", password);
//				model.addAttribute("cfpw", confirmPassword);
//				return "signup";
//			}
//
//			String hashedPassword = hashPasswordWithMD5(acc.getPassword());
//			acc.setPassword(hashedPassword);
//
//			session.save(acc);
//			t.commit();
//			model.addAttribute("message", "Đăng ký thành công!");
//		} catch (Exception e) {
//			t.rollback();
//			model.addAttribute("warning", "Đăng ký thất bại! " + e.getMessage());
//		} finally {
//			session.close();
//		}
//		return "signup";
	}

	private String hashPasswordWithMD5(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(password.getBytes());
			return Hex.encodeHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 algorithm not found", e);
		}
	}

}
