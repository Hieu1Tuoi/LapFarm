package LapFarm.Controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.UserInfoEntity;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	@Autowired
	SessionFactory factory;

	@GetMapping("/account")
	public String index(HttpSession httpSession, Model model) {
		AccountEntity user = (AccountEntity) httpSession.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			AccountEntity account = session.get(AccountEntity.class, user.getEmail());
			model.addAttribute("userProfile", account);
			UserInfoEntity userInfo = account.getUserInfo();
			model.addAttribute("userInfo", userInfo);
			t.commit();

		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			model.addAttribute("error", "Could not load account information" + e.getMessage());
		} finally {
			session.close();
		}
		return "account";
	}
	
	@GetMapping("/profile")
	public String profile(HttpSession httpSession, Model model) {
		AccountEntity user = (AccountEntity) httpSession.getAttribute("user");
		if(user == null) {
			return "redirect:/login";
		}
		loadUserProfile(user.getEmail(), model);
		return "redirect:/account#profile";
	}

	@PostMapping("/profile/update") 
	public String updateProfile(@RequestParam("fullname") String fullName,
			@RequestParam("dob") String dob,
			@RequestParam("sex") String sex, 
			@RequestParam("phone") String phone,
			@RequestParam("address") String address,
			HttpSession httpSession, Model model) {
		AccountEntity user = (AccountEntity) httpSession.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			AccountEntity account = session.get(AccountEntity.class, user.getEmail());
			UserInfoEntity userInfo = account.getUserInfo();
			if (userInfo == null) {
				userInfo = new UserInfoEntity();
				userInfo.setFullName(fullName);
				userInfo.setDob(dob);
				userInfo.setSex(sex);
				userInfo.setPhone(phone);
				userInfo.setAddress(address);
				account.setUserInfo(userInfo);
				session.save(userInfo);
			}
			userInfo.setFullName(fullName);
			userInfo.setDob(dob);
			userInfo.setSex(sex);
			userInfo.setPhone(phone);
			userInfo.setAddress(address);
			session.update(account);
			t.commit();
			model.addAttribute("success", "Profile updated successfully");
			
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			model.addAttribute("error", "Could not update profile infor" + e.getMessage());
		}finally {
			session.close();
		}
		return "redirect:/account#profile";
	}
	private void loadUserProfile(String email, Model model) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			AccountEntity account = session.get(AccountEntity.class, email);
			model.addAttribute("userProfile", account);
			
			UserInfoEntity userInfo = account.getUserInfo();
			model.addAttribute("userInfo", userInfo);
			t.commit();
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			model.addAttribute("error", "could not load account information"+e.getMessage());
		}finally {
			session.close();
		}
	}
}
