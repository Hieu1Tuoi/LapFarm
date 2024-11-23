package LapFarm.Controller;

import LapFarm.DAO.CartDAO;
import LapFarm.DAO.OrdersDAO;
import LapFarm.DTO.CartProductDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.CartEntity;
import LapFarm.Entity.OrderDetailId;
import LapFarm.Entity.OrderDetailsEntity;
import LapFarm.Entity.OrdersEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Entity.UserInfoEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PaymentController {

	@Autowired
	private CartDAO cartDAO;

	@Autowired
	private OrdersDAO ordersDAO;

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String index(HttpSession httpSession, Model model) {
		AccountEntity account = (AccountEntity) httpSession.getAttribute("user");

		if (account == null) {
			return "redirect:/login"; // Redirect to login page if the user is not logged in
		}

		// Retrieve the user info and cart products
		UserInfoEntity userInfo = account.getUserInfo(); // Get the user's info
		List<CartEntity> cartEntities = cartDAO.getCartByUserEmail(account.getEmail());
		List<CartProductDTO> cartProductDTOList = new ArrayList<>();
		double totalAmount = 0.0;

		for (CartEntity cartEntity : cartEntities) {
			ProductEntity product = cartEntity.getProduct();
			String formattedPrice = new DecimalFormat("#,###").format(product.getSalePrice());

			CartProductDTO cartProductDTO = new CartProductDTO(product.getNameProduct(), cartEntity.getQuantity(),
					formattedPrice, product.getSalePrice() * cartEntity.getQuantity());

			cartProductDTOList.add(cartProductDTO);
			totalAmount += cartProductDTO.getTotalPrice();
		}

		// Add the cart products and total amount to the model
		model.addAttribute("cartProducts", cartProductDTOList);
		model.addAttribute("totalAmount", new DecimalFormat("#,###").format(totalAmount));

		// Add the user information to the model for displaying in the view
		model.addAttribute("userInfo", userInfo);

		return "payment"; // Return the payment view
	}

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String perform(@RequestParam("payment") byte paymentMethod,
			@RequestParam("first-name") String firstName,
			@RequestParam("last-name") String lastName,
			@RequestParam("email") String email,
			@RequestParam("address") String address,
			@RequestParam("tel") String tel,
			@RequestParam(value = "note", required = false) String note, HttpSession session) {
		String fullname = firstName + lastName;
		
		return "paymentSuccess";
	}
}