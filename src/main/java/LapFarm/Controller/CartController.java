package LapFarm.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import LapFarm.DTO.CartDTO;
import LapFarm.DTO.UserInfoDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Service.CartServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController extends BaseController {

	@Autowired
	CartServiceImp cartService = new CartServiceImp();

	@RequestMapping("cart")
	public String showCart() {
		return "cart";
    }

	
	@RequestMapping(value = "addCart/{id}", method = RequestMethod.GET)
	public String addCart(HttpServletRequest request, HttpSession session, @PathVariable("id") Integer id) {
        HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        cart = cartService.AddCart(id, cart);
        session.setAttribute("Cart", cart);
        session.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
        session.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));
        return "redirect:" + request.getHeader("Referer");
    }

	@RequestMapping(value = "checkout", method = RequestMethod.GET)
	public String checkout(HttpSession session) {
	    AccountEntity user = (AccountEntity) session.getAttribute("user");
	    if (user == null) {
	        _mvShare.addObject("warning", "Bạn cần đăng nhập để thanh toán!");
	        return "redirect:/login";
	    }

	    HashMap<Integer, CartDTO> cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
	    if (cart != null && !cart.isEmpty()) {
	        try {
	            cartService.saveCartToDatabase(user, cart);
	            session.removeAttribute("Cart");
	            session.removeAttribute("TotalQuantyCart");
	            session.removeAttribute("TotalPriceCart");
	        } catch (Exception e) {
	            // Log lỗi và chuyển hướng đến trang lỗi nếu có vấn đề
	            System.err.println("Lỗi khi lưu giỏ hàng: " + e.getMessage());
	            return "redirect:/error";
	        }
	    }

	    return "redirect:/order-success";
	}



}
