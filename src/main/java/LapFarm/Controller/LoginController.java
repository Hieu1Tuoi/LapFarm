package LapFarm.Controller;

import LapFarm.Entity.AccountEntity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession httpSession) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			String hql = "FROM AccountEntity WHERE email = :email AND password = :password";
			Query query = session.createQuery(hql);
			query.setParameter("email", email);
			query.setParameter("password", hashPasswordWithMD5(password));

			AccountEntity acc = (AccountEntity) query.uniqueResult();

			if (acc != null) {
				if (acc.getRole().getId() == 1) {
					httpSession.setAttribute("admin", acc);
				} else if (acc.getRole().getId() == 0) {
					httpSession.setAttribute("user", acc);
				}
				t.commit();
				return "redirect:/home";
			} else {
				model.addAttribute("warning", "Email hoặc mật khẩu không đúng!");
				model.addAttribute("email", email);
				model.addAttribute("pw", password);
				return "login";
			}
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("warning", "Đăng nhập thất bại! " + e);
			return "login";
		} finally {
			session.close();
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute("user");
		httpSession.removeAttribute("admin");
		httpSession.removeAttribute("Cart");
		return "redirect:/login";
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
