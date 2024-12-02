package LapFarm.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.DTO.CartDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Service.CartServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController extends BaseController {

	@Autowired
	CartServiceImp cartService = new CartServiceImp();

	@RequestMapping("cart")
	public String showCart(HttpSession session) {
		// Lấy thông tin người dùng từ session
		AccountEntity user = (AccountEntity) session.getAttribute("user");

		// Kiểm tra xem người dùng có đăng nhập không
		if (user != null) {
			// Nếu người dùng đã đăng nhập, đồng bộ giỏ hàng từ database vào session
			HashMap<Integer, CartDTO> cart = cartService.getCartFromDatabase(user.getUserInfo().getUserId());

			// Cập nhật lại giỏ hàng trong session
			session.setAttribute("Cart", cart);
			session.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
			session.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));
		}

		// Trả về view giỏ hàng
		return "cart";
	}

	@RequestMapping(value = "addCart/{id}", method = RequestMethod.GET)
	public String addCart(HttpServletRequest request, HttpSession session, @PathVariable("id") Integer id) {
		// Kiểm tra số lượng sản phẩm
		if (!cartService.isProductAvailable(id)) {
			// Nếu sản phẩm không còn, chuyển hướng về trang hiện tại và thêm tham số lỗi
			String referer = request.getHeader("Referer");
			return "redirect:" + referer + (referer.contains("?") ? "&" : "?") + "error=product-unavailable";
		}
		// Lấy giỏ hàng từ session
		HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
		if (cart == null) {
			cart = new HashMap<>();
		}

		// Lấy thông tin người dùng
		AccountEntity user = (AccountEntity) session.getAttribute("user");
		int userId = (user != null) ? user.getUserInfo().getUserId() : 0;

		// Thêm sản phẩm vào giỏ
		cart = cartService.AddCart(id, cart, userId);

		// Cập nhật lại session
		session.setAttribute("Cart", cart);
		session.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
		session.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));

		return "redirect:" + request.getHeader("Referer");
	}

	@Transactional
	@RequestMapping(value = "/DeleteCart/{id}")
	public String DeleteCart(HttpServletRequest request, HttpSession session, @PathVariable("id") int id) {
		// Lấy giỏ hàng từ session
		HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
		if (cart == null) {
			cart = new HashMap<>();
		}

		// Xóa sản phẩm khỏi giỏ hàng trong session
		cart = cartService.DeleteCart(id, cart);

		// Kiểm tra nếu người dùng đã đăng nhập
		AccountEntity user = (AccountEntity) session.getAttribute("user");
		if (user != null) {
			// Nếu đăng nhập, xóa sản phẩm khỏi cơ sở dữ liệu
			cartService.deleteCartFromDatabase(user.getUserInfo().getUserId(), id);
		}

		// Cập nhật session sau khi xóa
		session.setAttribute("Cart", cart);
		session.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
		session.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));

		return "redirect:" + request.getHeader("Referer");
	}

	@Transactional
	@RequestMapping(value = "/EditCart/{id}/{quanty}", method = RequestMethod.GET)
	public String EditCart(HttpServletRequest request, HttpSession session, 
	        @PathVariable("id") int id, @PathVariable("quanty") int quanty) {
	    // Kiểm tra số lượng sản phẩm
	    if (!cartService.isProductAvailable(id)) {
	        String referer = request.getHeader("Referer");
	        return "redirect:" + referer + (referer.contains("?") ? "&" : "?") + "error=product-unavailable";
	    }

	    // Kiểm tra và cập nhật số lượng trong cơ sở dữ liệu
	    AccountEntity user = (AccountEntity) session.getAttribute("user");
	    if (user != null) {
	        cartService.updateProductQuantityInDatabase(user.getUserInfo().getUserId(), id, quanty);
	    }

	    // Cập nhật lại giỏ hàng trong session để đồng bộ
	    HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
	    if (cart == null) {
	        cart = new HashMap<>();
	    }

	    // Cập nhật session giỏ hàng
	    cart = cartService.EditCart(id, quanty, cart);

	    session.setAttribute("Cart", cart);
	    session.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
	    session.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));

	    return "redirect:" + request.getHeader("Referer");
	}


	@RequestMapping(value = "cart/selected", method = RequestMethod.POST)
	public ResponseEntity<Void> saveSelectedCartIds(@RequestBody Map<String, List<Integer>> data, HttpSession session) {
		// Lấy danh sách idCart từ request
		List<Integer> idCart = data.get("idCart");
		if (idCart != null && !idCart.isEmpty()) {
			// Lưu danh sách vào HttpSession
			session.removeAttribute("cartIdSelecteds");
			session.setAttribute("cartIdSelecteds", idCart);
			return new ResponseEntity<>(HttpStatus.OK); // Trả về trạng thái 200 OK
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Trả về trạng thái 400 Bad Request
	}
}
