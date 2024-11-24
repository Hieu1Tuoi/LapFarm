package LapFarm.Controller;

import LapFarm.DAO.CartDAO;
import LapFarm.DAO.OrdersDAO;
import LapFarm.DAO.UserDAO;
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
	private UserDAO userDAO;

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
		if (cartEntities == null || cartEntities.isEmpty()) {
			return "redirect:/home";
		}
		List<CartProductDTO> cartProductDTOList = new ArrayList<>();
		System.out.println(cartProductDTOList);
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

		model.addAttribute("userInfo", userInfo);

		return "payment"; // Return the payment view
	}

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String perform(@RequestParam("payment") byte paymentMethod, @RequestParam("fullName") String fullName,
			@RequestParam("address") String address, @RequestParam("tel") String tel,
			@RequestParam(value = "note", required = false) String note, HttpSession httpSession, Model model) {

		fullName = fullName.replaceAll("^(,\\s*|\\s*,)|((\\s*,\\s*)$)", "").trim();
		address = address.replaceAll("^(,\\s*|\\s*,)|((\\s*,\\s*)$)", "").trim();
		tel = tel.replaceAll("^(,\\s*|\\s*,)|((\\s*,\\s*)$)", "").trim();
		if (note != null) {
			note = note.replaceAll("^(,\\s*|\\s*,)|((\\s*,\\s*)$)", "").trim();
			model.addAttribute("note", note);
		}
		// Lấy tài khoản từ session
		AccountEntity account = (AccountEntity) httpSession.getAttribute("user");

		if (account != null) {

			// Lấy thông tin giỏ hàng từ session (giả sử email người dùng đã có trong
			// session)
			String email = account.getEmail(); // Email của người dùng đã được lưu trong session
			List<CartEntity> cartItems = cartDAO.getCartByUserEmail(email);

			if (cartItems.isEmpty()) {
				model.addAttribute("error", "Giỏ hàng của bạn đang trống.");
				return "cart"; // Quay lại trang giỏ hàng nếu không có sản phẩm
			}

			// Tạo đơn hàng mới
			OrdersEntity order = new OrdersEntity();
			order.setUserInfo(account.getUserInfo());
			order.setState("Chờ xác nhận"); // Trạng thái ban đầu là "Chờ xác nhận"
			order.setPaymentMethod(paymentMethod);
			order.setTotalPrice((int) calculateTotalPrice(cartItems)); // Tính tổng giá trị đơn hàng từ các sản phẩm
																		// trong giỏ hàng
			order.setTime(new Timestamp(System.currentTimeMillis())); // Thời gian hiện tại
			order.getUserInfo().setAddress(address);
			order.getUserInfo().setPhone(tel);
			order.setNote(note);

			// Lưu đơn hàng vào database
			ordersDAO.saveOrder(order);

			// Lưu chi tiết đơn hàng (OrderDetails) cho từng sản phẩm trong giỏ hàng
			for (CartEntity cartItem : cartItems) {
				OrderDetailsEntity orderDetails = new OrderDetailsEntity();
				OrderDetailId orderDetailId = new OrderDetailId();
				orderDetailId.setOrder(order.getIdOrder());
				orderDetailId.setProduct(cartItem.getProduct().getIdProduct());

				orderDetails.setId(orderDetailId);
				orderDetails.setOrder(order);
				orderDetails.setProduct(cartItem.getProduct());
				orderDetails.setQuantity(cartItem.getQuantity());
				orderDetails.setPrice((int) Math.round(cartItem.getProduct().getSalePrice()));

				// Lưu chi tiết đơn hàng
				ordersDAO.saveOrderDetail(orderDetails);
			}

			// Xóa giỏ hàng sau khi thanh toán
			cartDAO.clearCart(account.getUserInfo().getUserId());
			userDAO.updateUserinfo(account.getEmail(), fullName, account.getUserInfo().getDob(), tel, address,
					account.getUserInfo().getSex());

			// Sau khi lưu đơn hàng và chi tiết, chuyển hướng tới trang thanh toán thành
			// công
			return "paymentSuccess";
		} else {
			// Nếu không có tài khoản (người dùng chưa đăng nhập), chuyển hướng về trang
			// đăng nhập
			return "redirect:/login";
		}
	}

	// Phương thức tính tổng giá trị đơn hàng từ các sản phẩm trong giỏ hàng
	private double calculateTotalPrice(List<CartEntity> cartItems) {
		double totalPrice = 0;
		for (CartEntity cartItem : cartItems) {
			totalPrice += cartItem.getProduct().getSalePrice() * cartItem.getQuantity();
		}
		return totalPrice;
	}

}