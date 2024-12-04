package LapFarm.Controller;

import LapFarm.DAO.CartDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.CartDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.CartEntity;
import LapFarm.Entity.ProductEntity;

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
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController extends BaseController {

	@Autowired
	SessionFactory factory;

	@Autowired
	ProductDAO productDAO;

	@Autowired
	CartDAO cartDAO;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	/*
	 * @GetMapping("/login/oauth2/code/google") public String
	 * getGoogleUserInfo(Authentication authentication) { if (authentication
	 * instanceof OAuth2AuthenticationToken) { OAuth2AuthenticationToken oauth2Token
	 * = (OAuth2AuthenticationToken) authentication; OAuth2User oAuth2User =
	 * oauth2Token.getPrincipal(); // Lấy thông tin người dùng từ Google
	 * 
	 * System.out.println("Authenticated user: " + oAuth2User.getAttributes());
	 * 
	 * // Bạn có thể thêm logic để lưu thông tin người dùng vào database
	 * 
	 * return "redirect:/home"; // Sau khi đăng nhập thành công, chuyển hướng tới
	 * trang home } else { // Xử lý lỗi nếu không phải OAuth2AuthenticationToken
	 * return "error"; } }
	 */

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
