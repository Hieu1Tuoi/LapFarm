package LapFarm.Controller;

import LapFarm.DAO.CartDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DAO.UserDAO;
import LapFarm.DTO.CartDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.CartEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Service.GoogleService;
import LapFarm.Utils.XSSUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class LoginController extends BaseController {

	@Autowired
	SessionFactory factory;

	@Autowired
	ProductDAO productDAO;

	@Autowired
	CartDAO cartDAO;

	@Autowired
	private UserDAO accountDAO;

	@Autowired
	private GoogleService googleService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {

		String authUrl = googleService.generateAuthorizationUrl();
		model.addAttribute("googleAuthUrl", authUrl);
		return "login";
	}

	@GetMapping("/oauth2/callback/google")
	public String handleGoogleCallback(@RequestParam("code") String code, Model model, HttpSession httpSession) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			// Exchange authorization code for access token
			Map<String, Object> tokenResponse = googleService.exchangeCodeForToken(code);
			String accessToken = (String) tokenResponse.get("access_token");
			httpSession.setAttribute("accessToken", accessToken);

			// Validate access token by calling Google API
			String validationUrl = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + accessToken;
			URL url = new URL(validationUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// Read the response
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// You can parse the response to get user info or other details (e.g., sub,
			// email)
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> userInfo = objectMapper.readValue(response.toString(), Map.class);
			// Duyệt qua Map và in các key và giá trị
			/*
			 * for (Map.Entry<String, Object> entry : userInfo.entrySet()) {
			 * System.out.println(entry.getKey() + ": " + entry.getValue()); }
			 */

			AccountEntity acc;
			String email = (String) userInfo.get("email");
			if (accountDAO.checkEmailExists(email)) {
				String hql = "FROM AccountEntity WHERE email = :email";
				Query query = session.createQuery(hql);
				query.setParameter("email", email);
				acc = (AccountEntity) query.uniqueResult();

			} else {
				acc = new AccountEntity(email, "thanhnhatdeptraivainhoai");
				accountDAO.saveAccount(acc);
				accountDAO.createUserinfo(email);
				acc = accountDAO.getAccountByEmail(email);
			}

			// Lấy giỏ hàng từ session
			HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) httpSession.getAttribute("Cart");

			// Kiểm tra giỏ hàng không null và không rỗng
			if (cart != null && !cart.isEmpty()) {
				// Duyệt qua từng mục trong giỏ hàng
				for (Map.Entry<Integer, CartDTO> entry : cart.entrySet()) {
					Integer productId = entry.getKey(); // Lấy key (ID sản phẩm)
					CartDTO cartItem = entry.getValue(); // Lấy giá trị (CartDTO)

					ProductEntity productEntity = productDAO.getProductById(cartItem.getProduct().getIdProduct());
					CartEntity cartEntity = new CartEntity(acc.getUserInfo(), productEntity, 1);
					cartDAO.createCart(cartEntity);
				}
				cart = cartDAO.getCartFromDatabase(acc.getUserInfo().getUserId());

				// Cập nhật lại session
				httpSession.setAttribute("Cart", cart);
				httpSession.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
				httpSession.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));
			}

			t.commit();
			// Add user info to model for display or further processing
			model.addAttribute("userInfo", userInfo);
			httpSession.setAttribute("user", acc);
			httpSession.setAttribute("isGoogleLogin", true);

			// Redirect to home or dashboard
			return "redirect:/home";
		} catch (Exception e) {
			// Handle exceptions
			model.addAttribute("warning", "Xác thực thất bại!!! " + e);
			return "login";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("g-recaptcha-response") String recaptchaResponse, Model model, HttpSession httpSession) {
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
				System.out.println("reCAPTCHA invalidation succeeded.");
				return "login";
			}
		} catch (Exception e) {
			model.addAttribute("warning", "Lỗi khi xác thực reCAPTCHA: " + e.getMessage());
			return "login";
		}

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			String hql = "FROM AccountEntity WHERE email = :email";
			Query query = session.createQuery(hql);
			query.setParameter("email", email);

			AccountEntity acc = (AccountEntity) query.uniqueResult();

			if (acc != null) {
				// Kiểm tra xem tài khoản có bị khóa không
				if (acc.getLockedUntil() != null && acc.getLockedUntil().isAfter(LocalDateTime.now())) {
					// Tính toán thời gian mở khóa tài khoản
					LocalDateTime lockedUntil = acc.getLockedUntil();
					String unlockTime = lockedUntil.format(DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy"));
					model.addAttribute("warning",
							"Tài khoản của bạn đã bị khóa. Vui lòng thử lại sau, sau thời gian " + unlockTime + ".");
					return "login";
				}

				String hashedPassword = hashPasswordWithMD5(password);
				if (!hashedPassword.equals(acc.getPassword())) { // Mật khẩu mã hóa
					Integer failedAttempts = acc.getFailedAttempts();
					if (failedAttempts == null) {
						failedAttempts = 0; // Nếu failedAttempts là null, khởi tạo giá trị mặc định
					}

					failedAttempts++; // Tăng số lần thất bại
					accountDAO.updateFailedAttempts(failedAttempts, email); // Cập nhật số lần đăng nhập thất bại

					if (failedAttempts >= 3) {
						// Set thời gian khóa tài khoản là hiện tại cộng 10 phút
						LocalDateTime lockTime = LocalDateTime.now();
						LocalDateTime unlockTime = lockTime.plusMinutes(10);
						accountDAO.lockAccount(email, unlockTime);
						String unlockFormattedTime = unlockTime
								.format(DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy"));
						accountDAO.changeUserState(email, "Bị khóa");
						model.addAttribute("warning",
								"Bạn đã nhập sai mật khẩu 3 lần. Tài khoản đã bị khóa trong 10 phút và sẽ mở lại vào lúc "
										+ unlockFormattedTime + ".");
					} else {
						model.addAttribute("warning", "Sai mật khẩu. Thử lại.");
					}
					return "login";
				}

				// Nếu đăng nhập thành công, đặt lại failedAttempts về 0
				accountDAO.resetFailedAttempts(email); // Đặt lại số lần thất bại về 0
				accountDAO.resetLockedUntil(email); // Xóa thời gian khóa nếu đăng nhập thành công
				accountDAO.changeUserState(email, "Hoạt động");

				// Kiểm tra đăng nhập qua Google
				Boolean isGoogleLogin = (Boolean) httpSession.getAttribute("isGoogleLogin");
				if (isGoogleLogin != null && isGoogleLogin) {
					httpSession.setAttribute("user", acc);
					// Reset trạng thái đăng nhập qua Google
					httpSession.removeAttribute("isGoogleLogin");
				} else {
					// Kiểm tra trạng thái mật khẩu
					if (acc.getLastPasswordChangeDate() == null || isPasswordExpired(acc.getLastPasswordChangeDate())) {
						httpSession.setAttribute("passwordWarning", "Mật khẩu của bạn cần được thay đổi sau 90 ngày.");
					}
				}

				if (acc.getRole().getId() == 1) {
					httpSession.setAttribute("admin", acc);
				} else if (acc.getRole().getId() == 0) {
					httpSession.setAttribute("user", acc);
				}

				// Lấy giỏ hàng từ session
				HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) httpSession.getAttribute("Cart");

				// Kiểm tra giỏ hàng không null và không rỗng
				if (cart != null && !cart.isEmpty()) {
					// Duyệt qua từng mục trong giỏ hàng
					for (Map.Entry<Integer, CartDTO> entry : cart.entrySet()) {
						Integer productId = entry.getKey(); // Lấy key (ID sản phẩm)
						CartDTO cartItem = entry.getValue(); // Lấy giá trị (CartDTO)

						ProductEntity productEntity = productDAO.getProductById(cartItem.getProduct().getIdProduct());
						CartEntity cartEntity = new CartEntity(acc.getUserInfo(), productEntity, 1);
						cartDAO.createCart(cartEntity);
					}
					cart = cartDAO.getCartFromDatabase(acc.getUserInfo().getUserId());

					// Cập nhật lại session
					httpSession.setAttribute("Cart", cart);
					httpSession.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
					httpSession.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));
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

	private boolean isPasswordExpired(LocalDateTime passwordChangedAt) {
		if (passwordChangedAt == null) {
			return true; // Nếu ngày thay đổi mật khẩu chưa được đặt, coi là hết hạn.
		}
		// Thêm 90 ngày vào thời điểm thay đổi mật khẩu
		LocalDateTime expiryDate = passwordChangedAt.plusDays(90);
		return LocalDateTime.now().isAfter(expiryDate);
	}

	@RequestMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute("user");
		httpSession.removeAttribute("admin");
		httpSession.removeAttribute("Cart");
		httpSession.removeAttribute("viewedItems");
		httpSession.removeAttribute("order");
		httpSession.removeAttribute("accessToken");
		httpSession.removeAttribute("passwordWarning");
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

	@RequestMapping("/forgot-password")
	public String showForgotPasswordPage() {
		return "forgotpassword";
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public String resetPassword(RedirectAttributes redirectAttributes, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("verificationCode") String verificationCode, HttpSession verificationSession,
			@RequestParam("g-recaptcha-response") String recaptchaResponse) {
		try {
			if (XSSUtils.containsXSS(password) || XSSUtils.containsXSS(email) || XSSUtils.containsXSS(confirmPassword)
					|| XSSUtils.containsXSS(verificationCode)) {
				redirectAttributes.addFlashAttribute("warning", "Dữ liệu nhập vào chứa nội dung không hợp lệ.");
				redirectAttributes.addFlashAttribute("email", email);
				redirectAttributes.addFlashAttribute("pw", password);
				redirectAttributes.addFlashAttribute("cfpw", confirmPassword);
				return "redirect:/forgot-password";
			}
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
				return "redirect:/forgot-password";
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("warning", "Lỗi khi xác thực reCAPTCHA: " + e.getMessage());
			return "redirect:/forgot-password";
		}

		// Kiểm tra mã xác minh
		String codeFromSession = (String) verificationSession.getAttribute("verificationCode");
		if (codeFromSession == null || !codeFromSession.equals(verificationCode)) {
			redirectAttributes.addFlashAttribute("warning", "Mã xác nhận không chính xác!");
			redirectAttributes.addFlashAttribute("email", email);
			redirectAttributes.addFlashAttribute("pw", password);
			redirectAttributes.addFlashAttribute("cfpw", confirmPassword);
			return "redirect:/forgot-password"; // Trang sửa mật khẩu
		}

		// Kiểm tra độ dài mật khẩu và mật khẩu bằng regex
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";

		if (!password.matches(passwordRegex)) {
			redirectAttributes.addFlashAttribute("warning",
					"Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và kí tự đặc biệt!");
			redirectAttributes.addFlashAttribute("email", email);
			redirectAttributes.addFlashAttribute("pw", password);
			redirectAttributes.addFlashAttribute("cfpw", confirmPassword);
			return "redirect:/forgot-password"; // Trang sửa mật khẩu
		} else if (!password.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("warning", "Xác nhận mật khẩu không giống nhau!");
			redirectAttributes.addFlashAttribute("email", email);
			redirectAttributes.addFlashAttribute("pw", password);
			redirectAttributes.addFlashAttribute("cfpw", confirmPassword);
			return "redirect:/forgot-password"; // Trang sửa mật khẩu
		}

		// Tiến hành cập nhật mật khẩu
		try {
			if (!accountDAO.checkEmailExists(email)) {
				redirectAttributes.addFlashAttribute("warning", "Email không tồn tại trong hệ thống!");
				redirectAttributes.addFlashAttribute("email", email);
				redirectAttributes.addFlashAttribute("pw", password);
				redirectAttributes.addFlashAttribute("cfpw", confirmPassword);
				return "redirect:/forgot-password";
			}

			accountDAO.updatePassword(email, password);

			redirectAttributes.addFlashAttribute("message", "Mật khẩu đã được cập nhật thành công!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("warning", "Cập nhật mật khẩu thất bại! " + e.getMessage());
		}

		return "redirect:/forgot-password";
	}

}
