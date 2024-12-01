package LapFarm.Controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.ViewedItem;
import LapFarm.Entity.AccountEntity;

import LapFarm.Entity.UserInfoEntity;
import LapFarm.Service.OrdersServiceImp;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	@Autowired
	SessionFactory factory;

	@Autowired
	private OrdersServiceImp orderService;
	

    @GetMapping("/account")
    public String index(@RequestParam(value = "tab", defaultValue = "profile") String tab,
                        HttpSession httpSession, Model model) {
        AccountEntity user = (AccountEntity) httpSession.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Load thông tin cá nhân
        loadUserProfile(user.getEmail(), model);

        // Load lịch sử đơn hàng
        try {
            List<OrdersDTO> ordersList = orderService.getOrdersByUserId(user.getUserInfo().getUserId());
           System.out.println(ordersList);
            model.addAttribute("orders", ordersList);
        } catch (Exception e) {
        	System.out.println("Không thể tải lịch sử đơn hàng: " + e.getMessage());
            model.addAttribute("error", "Không thể tải lịch sử đơn hàng: " + e.getMessage());
        }

        // Lấy sản phẩm đã xem
        List<ViewedItem> viewedItems = (List<ViewedItem>) httpSession.getAttribute("viewedItems");
        if (viewedItems == null) {
            viewedItems = new ArrayList<>();
        }
        model.addAttribute("viewedItems", viewedItems);

        // Truyền tab đang hoạt động vào Model
        model.addAttribute("activeTab", tab);

        return "account"; // Trả về view "account.jsp"
    }
	@GetMapping("/profile")
	public String profile(HttpSession httpSession, Model model) {
		AccountEntity user = (AccountEntity) httpSession.getAttribute("user");
		if (user == null) {
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
	                            HttpSession httpSession, 
	                            Model model) {
	    AccountEntity user = (AccountEntity) httpSession.getAttribute("user");
	    if (user == null) {
	        return "redirect:/login";
	    }

	    Session session = factory.openSession();
	    Transaction t = session.beginTransaction();
	    try {
	        AccountEntity account = session.get(AccountEntity.class, user.getEmail());
	        UserInfoEntity userInfo = account.getUserInfo();
	        
	        // Nếu userInfo chưa tồn tại, tạo mới
	        if (userInfo == null) {
	            userInfo = new UserInfoEntity();
	            account.setUserInfo(userInfo);
	        }

	        // Cập nhật thông tin
	        userInfo.setFullName(fullName);
	        userInfo.setDob(dob);
	        userInfo.setSex(sex);
	        userInfo.setPhone(phone);
	        userInfo.setAddress(address);

	        session.update(account);
	        t.commit();

	        // Lấy lại dữ liệu mới nhất và cập nhật vào session
	        AccountEntity updatedAccount = session.get(AccountEntity.class, account.getEmail());
	        httpSession.setAttribute("user", updatedAccount);

	        model.addAttribute("success", "Cập nhật thông tin thành công!");
	    } catch (Exception e) {
	        t.rollback();
	        model.addAttribute("error", "Không thể cập nhật thông tin: " + e.getMessage());
	    } finally {
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
			model.addAttribute("error", "could not load account information" + e.getMessage());
		} finally {
			session.close();
		}
	}

//	@GetMapping("/account")
//	public String getOrderHistory(HttpSession httpSession, Model model, @RequestParam(value = "tab", defaultValue = "profile") String tab) {
//	    // Kiểm tra người dùng đăng nhập
//	    AccountEntity user = (AccountEntity) httpSession.getAttribute("user");
//	    if (user == null) {
//	        return "redirect:/login";
//	    }
//
//	    // Gọi OrdersService để lấy danh sách đơn hàng theo userId
//	    try {
//	        List<OrdersDTO> ordersList = orderService.getOrdersByUserId(user.getUserInfo().getUserId());
//	        model.addAttribute("orders", ordersList);
//	    } catch (Exception e) {
//	        model.addAttribute("error", "Không thể tải lịch sử đơn hàng: " + e.getMessage());
//	    }
//
//	    // Thêm tham số tab vào Model
//	    model.addAttribute("activeTab", tab);
//	    // Trả về trang account.jsp với tab orders-history được hiển thị
//	    return "account";
//	}



}
