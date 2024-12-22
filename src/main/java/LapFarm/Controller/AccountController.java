package LapFarm.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import LapFarm.DAO.PaymentDAO;
import LapFarm.DAO.UserDAO;
import LapFarm.DTO.OrderDetailDTO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.DTO.ViewedItem;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.PaymentEntity;
import LapFarm.Entity.UserInfoEntity;
import LapFarm.Service.OrdersServiceImp;
import LapFarm.Utils.SecureUrlUtil;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	@Autowired
	SessionFactory factory;

	@Autowired
	private UserDAO accountDAO;
	@Autowired
    private PaymentDAO paymentDAO;
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
		
		try {
	        List<PaymentEntity> payments = paymentDAO.getPaymentsByUserId(user.getUserInfo().getUserId());
	        model.addAttribute("payments", payments);
	        System.out.println("Số lượng payment: " + (payments != null ? payments.size() : 0));
	    } catch (Exception e) {
	        System.out.println("Lỗi khi tải lịch sử thanh toán: " + e.getMessage());
	        e.printStackTrace();
	        model.addAttribute("paymentError", "Không thể tải lịch sử thanh toán");
	    }
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
		double totalPrice = orderDetails.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

		// Thêm chi tiết đơn hàng và tổng giá vào model
		model.addAttribute("orderDetail", orderDetails);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("stateOrder", orderService.getStateById(orderId));

		// Trả về tên của JSP sẽ hiển thị chi tiết đơn hàng
		return "user/orderdetails/orderdetail"; // Trang này sẽ hiển thị chi tiết đơn hàng
	}

	@PostMapping("/change-password/send-code") // Đổi URL để tránh conflict
	@ResponseBody
	public ResponseEntity<?> sendPasswordChangeCode(HttpSession session) {
		try {
			// Lấy thông tin user đã đăng nhập từ session
			AccountEntity user = (AccountEntity) session.getAttribute("user");
			if (user == null) {
				user = (AccountEntity) session.getAttribute("admin");
				if (user == null) {
					return ResponseEntity.badRequest().body("Người dùng chưa đăng nhập");
				}
			}

			// Gửi mã xác thực
			String code = accountDAO.sendVerificationCode(user.getEmail());

			// Lưu mã và thời gian hết hạn vào session
			session.setAttribute("verificationCode", code);
			session.setAttribute("codeExpireTime", LocalDateTime.now().plusMinutes(5));

			return ResponseEntity.ok().body("Mã xác nhận đã được gửi");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Lỗi khi gửi mã xác nhận: " + e.getMessage());
		}
	}

	@RequestMapping("/change-password")
	public String showChangePasswordPage(HttpSession session, Model model) {
		// Check if user is logged in
		AccountEntity user = (AccountEntity) session.getAttribute("user");
		if (user == null) {
			user = (AccountEntity) session.getAttribute("admin");
			if (user == null) {
				return "redirect:/login";
			}
		}

		// Store user email in session for the password change process
		session.setAttribute("userEmail", user.getEmail());
		return "changepassword";
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public String changePassword(RedirectAttributes redirectAttributes, @RequestParam("oldPassword") String oldPassword,
			@RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("verificationCode") String verificationCode, HttpSession verificationSession,
			@RequestParam("g-recaptcha-response") String recaptchaResponse, HttpSession session) {

		AccountEntity user = (AccountEntity) session.getAttribute("user");
		if (user == null) {
			user = (AccountEntity) session.getAttribute("admin");
			if (user == null) {
				return "redirect:/login";
			}
		}

		String email = user.getEmail();
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
				redirectAttributes.addFlashAttribute("warning", "reCAPTCHA không hợp lệ. Vui lòng thử lại!");
				return "redirect:/change-password";
			}
			/*
			 * } catch (Exception e) { redirectAttributes.addFlashAttribute("warning",
			 * "Lỗi khi xác thực reCAPTCHA: " + e.getMessage()); return
			 * "redirect:/change-password"; }
			 */
			// Verify old password
			if (!accountDAO.verifyPassword(email, oldPassword)) {
				redirectAttributes.addFlashAttribute("warning", "Mật khẩu cũ không chính xác!");
				return "redirect:/change-password";
			}

// Kiểm tra mã xác minh
			String savedCode = (String) session.getAttribute("verificationCode");
			LocalDateTime expireTime = (LocalDateTime) session.getAttribute("codeExpireTime");

			if (savedCode == null || expireTime == null || LocalDateTime.now().isAfter(expireTime)
					|| !savedCode.equals(verificationCode)) {
				redirectAttributes.addFlashAttribute("warning", "Mã xác nhận không chính xác hoặc đã hết hạn!");
				return "redirect:/change-password";
			}
			
			// Kiểm tra mật khẩu mới không trùng với mật khẩu cũ
			if (oldPassword.equals(password)) {
			    redirectAttributes.addFlashAttribute("warning", "Mật khẩu mới không được trùng với mật khẩu cũ!");
			    redirectAttributes.addFlashAttribute("pw", password);
			    redirectAttributes.addFlashAttribute("cfpw", confirmPassword);
			    return "redirect:/change-password"; // Trang sửa mật khẩu
			}


// Kiểm tra độ dài mật khẩu và mật khẩu bằng regex
			String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";

			if (!password.matches(passwordRegex)) {
				redirectAttributes.addFlashAttribute("warning",
						"Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và kí tự đặc biệt!");
				redirectAttributes.addFlashAttribute("pw", password);
				redirectAttributes.addFlashAttribute("cfpw", confirmPassword);
				return "redirect:/change-password"; // Trang sửa mật khẩu
			} else if (!password.equals(confirmPassword)) {
				redirectAttributes.addFlashAttribute("warning", "Xác nhận mật khẩu không giống nhau!");
				redirectAttributes.addFlashAttribute("pw", password);
				redirectAttributes.addFlashAttribute("cfpw", confirmPassword);
				return "redirect:/change-password"; // Trang sửa mật khẩu
			}

// Tiến hành cập nhật mật khẩu
			accountDAO.updatePassword(email, password);
			redirectAttributes.addFlashAttribute("message", "Mật khẩu đã được cập nhật thành công!");
			SecurityContextHolder.clearContext(); // Xóa thông tin đăng nhập hiện tại
			session.invalidate(); // Xóa session
			return "redirect:/login";

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("warning", "Cập nhật mật khẩu thất bại! " + e.getMessage());
			return "redirect:/change-password";
		}
	}
	@GetMapping("/payment-history")
    public String showPaymentHistory(Model model, HttpSession session) {
        // Lấy userId từ session
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        List<PaymentEntity> payments = paymentDAO.getPaymentsByUserId(userId);
        model.addAttribute("payments", payments);
        return "payment-history";
    }
	
}
