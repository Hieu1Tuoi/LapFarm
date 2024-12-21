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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import LapFarm.DAO.OrdersDAO;
import LapFarm.DTO.OrderDetailDTO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.DTO.ViewedItem;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.OrderDetailsEntity;
import LapFarm.Entity.OrdersEntity;
import LapFarm.Entity.UserInfoEntity;
import LapFarm.Service.OrdersServiceImp;
import LapFarm.Utils.SecureUrlUtil;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	@Autowired
	SessionFactory factory;

	@Autowired
	private OrdersServiceImp orderService;

	@GetMapping("/account")
	public String index(@RequestParam(value = "tab", defaultValue = "profile") String tab, HttpSession httpSession,
			Model model) {
		AccountEntity user = (AccountEntity) httpSession.getAttribute("user");
		if (user == null) {
			user = (AccountEntity) httpSession.getAttribute("admin");
			if (user == null) {
				return "redirect:/login";
			}
		}

		// Load thông tin cá nhân
		loadUserProfile(user.getEmail(), model);

		// Load lịch sử đơn hàng
		try {
			List<OrdersDTO> ordersList = orderService.getOrdersByUserId(user.getUserInfo().getUserId());
			
			// Mã hóa ID sản phẩm trong danh sách top selling
			ordersList.forEach(order -> {
		        try {
		        	order.setEncryptedId(SecureUrlUtil.encrypt(String.valueOf(order.getOrderId())));
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    });
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
		// Mã hóa ID sản phẩm trong danh sách top selling
		viewedItems.forEach(product -> {
	        try {
	            product.setEncryptedId(SecureUrlUtil.encrypt(String.valueOf(product.getId())));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
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
	public String updateProfile(@RequestParam("fullname") String fullName, @RequestParam("dob") String dob,
			@RequestParam(value = "sex", required = false) String sex, @RequestParam("phone") String phone,
			@RequestParam("address") String address, HttpSession httpSession, Model model) {
		AccountEntity user = (AccountEntity) httpSession.getAttribute("user");
		int x = 0;
		if (user == null) {
			x = 1;
			user = (AccountEntity) httpSession.getAttribute("admin");
			if (user == null) {
				return "redirect:/login";
			}
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
			userInfo.setSex(sex != null && !sex.isEmpty() ? sex : null);
			userInfo.setPhone(phone);
			userInfo.setAddress(address);

			session.update(account);
			t.commit();

			// Lấy lại dữ liệu mới nhất và cập nhật vào session
			AccountEntity updatedAccount = session.get(AccountEntity.class, account.getEmail());
			if (x == 0) {
				httpSession.setAttribute("user", updatedAccount);
			} else {
				httpSession.setAttribute("admin", updatedAccount);
			}

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

	@RequestMapping("/order-detail")
	public String showOrderDetail(@RequestParam("orderId") String encryptedId, Model model) {
	    int orderId = 0;
	    try {
	        // Giải mã orderId từ encryptedId
	        orderId = Integer.parseInt(SecureUrlUtil.decrypt(encryptedId)); // Sử dụng phương thức giải mã
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("error", "Không thể giải mã ID đơn hàng.");
	        return "error"; // Trả về trang lỗi nếu có vấn đề với việc giải mã
	    }

	    // Lấy thông tin chi tiết đơn hàng từ dịch vụ
	    List<OrderDetailDTO> orderDetails = orderService.getOrderDetail(orderId);
	    
	    // Duyệt qua từng chi tiết đơn hàng để mã hóa idProduct và tính tổng giá
	    for (OrderDetailDTO orderDetail : orderDetails) {
	        ProductDTO product = orderDetail.getProduct();
	        try {
	            // Mã hóa idProduct
	            String encryptedProductId = SecureUrlUtil.encrypt(String.valueOf(product.getIdProduct()));
	            product.setEncryptedId(encryptedProductId); // Gán id đã mã hóa vào product
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Tính tổng giá
	    double totalPrice = orderDetails.stream()
	        .mapToDouble(item -> item.getPrice() * item.getQuantity())
	        .sum();
	    
	    OrdersEntity order = orderService.getOrderById(orderId);

	    // Thêm chi tiết đơn hàng và tổng giá vào model
	    model.addAttribute("order", order);
	    model.addAttribute("orderDetail", orderDetails);
	    model.addAttribute("totalPrice", totalPrice);
	    model.addAttribute("stateOrder", orderService.getStateById(orderId));

	    // Trả về tên của JSP sẽ hiển thị chi tiết đơn hàng
	    return "user/orderdetails/orderdetail"; // Trang này sẽ hiển thị chi tiết đơn hàng
	}


}
