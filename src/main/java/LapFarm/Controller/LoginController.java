package LapFarm.Controller;

import LapFarm.DAO.CartDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DAO.UserDAO;
import LapFarm.DTO.CartDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.CartEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Entity.RoleEntity;
import LapFarm.Service.GoogleService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

			// Redirect to home or dashboard
			return "redirect:/home";
		} catch (Exception e) {
			// Handle exceptions
			model.addAttribute("warning", "Xác thực thất bại!!! " + e);
			return "login";
		}
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

	@RequestMapping("/logout")
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute("user");
		httpSession.removeAttribute("admin");
		httpSession.removeAttribute("Cart");
		httpSession.removeAttribute("viewedItems");
		httpSession.removeAttribute("order");
		httpSession.removeAttribute("accessToken");
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
