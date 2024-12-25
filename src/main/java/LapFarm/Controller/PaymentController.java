package LapFarm.Controller;

import LapFarm.DAO.CartDAO;
import LapFarm.DAO.NotificationDAO;
import LapFarm.DAO.OrdersDAO;
import LapFarm.DAO.PaymentDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DAO.UserDAO;
import LapFarm.DTO.CartProductDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.CartEntity;
import LapFarm.Entity.NotificationEntity;
import LapFarm.Entity.OrderDetailId;
import LapFarm.Entity.OrderDetailsEntity;
import LapFarm.Entity.OrdersEntity;
import LapFarm.Entity.PaymentEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Entity.UserInfoEntity;
import LapFarm.Model.Config;
import LapFarm.Service.PaymentService;
import LapFarm.Utils.XSSUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/payment")
public class PaymentController {

	@Autowired
	private CartDAO cartDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private OrdersDAO ordersDAO;

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private NotificationDAO notificationDAO;
	
	@Autowired
	private PaymentDAO paymentDAO;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(HttpSession httpSession, Model model) {
		AccountEntity account = (AccountEntity) httpSession.getAttribute("user");
		List<Integer> cartIdSelecteds = (List<Integer>) httpSession.getAttribute("cartIdSelecteds");

		if (account == null) {
			return "redirect:/login"; // Redirect to login page if the user is not logged in
		}

		// Retrieve the user info and cart products
		UserInfoEntity userInfo = account.getUserInfo(); // Get the user's info
		List<CartEntity> cartEntities = cartDAO.getCartByUserEmail(account.getEmail());
		if (cartIdSelecteds == null) {
			return "redirect:/cart";
		}
		List<CartProductDTO> cartProductDTOList = new ArrayList<>();

		double totalAmount = 0.0;
		for (Integer cartId : cartIdSelecteds) {
			CartEntity cartEntity = cartDAO.getCartById(cartId);
			ProductEntity product = cartEntity.getProduct();
			Double price = (1 - product.getDiscount()) * product.getSalePrice();
			String formattedPrice = new DecimalFormat("#,###").format(price);

			CartProductDTO cartProductDTO = new CartProductDTO(product.getNameProduct(), cartEntity.getQuantity(),
					formattedPrice, product.getSalePrice() * cartEntity.getQuantity());

			cartProductDTOList.add(cartProductDTO);
			totalAmount += price * cartEntity.getQuantity();
		}

		// Add the cart products and total amount to the model
		model.addAttribute("cartProducts", cartProductDTOList);
		model.addAttribute("totalAmount", new DecimalFormat("#,###").format(totalAmount));
		long totalAmountLong = (long) totalAmount;
		model.addAttribute("totalAmountDefaut", totalAmountLong);

		model.addAttribute("userInfo", userInfo);

		return "payment"; // Return the payment view
	}

	@Autowired
	PaymentService paymentService;
	@Autowired
	Config config;

	@RequestMapping(value = "/vnpay", method = RequestMethod.GET)
	public String vnpay(@RequestParam("totalAmount") long totalAmount, @RequestParam("fullName") String fullName,
			@RequestParam("address") String address, @RequestParam("tel") String tel,
			@RequestParam(value = "note", required = false) String note, HttpSession httpSession, Model model,
			HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String language = "vn";
		System.out.println(totalAmount);
		// Kiểm tra XSS
		boolean hasXSS = false;
		if (XSSUtils.containsXSS(fullName)) {
		    redirectAttributes.addFlashAttribute("errorFullName", "Họ và tên chứa ký tự không hợp lệ.");
		    hasXSS = true;
		}
		if (XSSUtils.containsXSS(address)) {
		    redirectAttributes.addFlashAttribute("errorAddress", "Địa chỉ chứa ký tự không hợp lệ.");
		    hasXSS = true;
		}
		if (XSSUtils.containsXSS(tel)) {
		    redirectAttributes.addFlashAttribute("errorTel", "Số điện thoại chứa ký tự không hợp lệ.");
		    hasXSS = true;
		}
		if (note != null && XSSUtils.containsXSS(note)) {
		    redirectAttributes.addFlashAttribute("errorNote", "Ghi chú chứa ký tự không hợp lệ.");
		    hasXSS = true;
		}
	    // Nếu phát hiện lỗi, quay lại form với thông báo lỗi
	    if (hasXSS) {
	        return "redirect:/payment";
	    }
		Map<String, Object> OrderInfo = new HashMap<String, Object>();
		OrderInfo.put("totalAmount", totalAmount);
		OrderInfo.put("address", address);
		OrderInfo.put("note", note);
		OrderInfo.put("tel", tel);
		OrderInfo.put("fullName", fullName);
		httpSession.setAttribute("OrderInfo", OrderInfo);

		try {
			// Gọi service tạo URL thanh toán
			JsonObject paymentResponse = paymentService.createPayment(request, totalAmount, "", language);

			// Kiểm tra kết quả trả về
			if (paymentResponse != null && "00".equals(paymentResponse.get("code").getAsString())) {
				// Lấy URL thanh toán
				String paymentUrl = paymentResponse.get("data").getAsString();

				// Chuyển hướng người dùng tới trang thanh toán
				return "redirect:" + paymentUrl;
			} else {
				// Xử lý lỗi nếu có
				model.addAttribute("error", "Không thể tạo thanh toán. Vui lòng thử lại!");
				return "error"; // Trang hiển thị lỗi
			}
		} catch (Exception e) {
			// Log lỗi và hiển thị thông báo lỗi
			e.printStackTrace();
			model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
			return "error"; // Trang hiển thị lỗi
		}
	}

	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String perform(@RequestParam("totalAmount") int totalAmount, @RequestParam("fullName") String fullName,
			@RequestParam("address") String address, @RequestParam("tel") String tel,
			@RequestParam(value = "note", required = false) String note, HttpSession httpSession, Model model,RedirectAttributes redirectAttributes) {

		// Kiểm tra XSS
		boolean hasXSS = false;
		if (XSSUtils.containsXSS(fullName)) {
		    redirectAttributes.addFlashAttribute("errorFullName", "Họ và tên chứa ký tự không hợp lệ.");
		    hasXSS = true;
		}
		if (XSSUtils.containsXSS(address)) {
		    redirectAttributes.addFlashAttribute("errorAddress", "Địa chỉ chứa ký tự không hợp lệ.");
		    hasXSS = true;
		}
		if (XSSUtils.containsXSS(tel)) {
		    redirectAttributes.addFlashAttribute("errorTel", "Số điện thoại chứa ký tự không hợp lệ.");
		    hasXSS = true;
		}
		if (note != null && XSSUtils.containsXSS(note)) {
		    redirectAttributes.addFlashAttribute("errorNote", "Ghi chú chứa ký tự không hợp lệ.");
		    hasXSS = true;
		}

	    // Nếu phát hiện lỗi, quay lại form với thông báo lỗi
	    if (hasXSS) {
	        return "redirect:/payment";
	    }
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

			List<Integer> cartIds = (List<Integer>) httpSession.getAttribute("cartIdSelecteds");

			if (cartIds.isEmpty()) {
				model.addAttribute("error", "Bạn chưa chọn sản phẩm.");
				return "cart"; // Quay lại trang giỏ hàng nếu không có sản phẩm
			}
			Map<String, Object> orderMap = new HashMap<String, Object>();

			// Tạo đơn hàng mới
			OrdersEntity order = new OrdersEntity();
			order.setUserInfo(account.getUserInfo());
			order.setState("Chờ lấy hàng"); // Trạng thái ban đầu là "Chờ lấy hàng"
			order.setPaymentMethod((byte) 0);
			order.setTotalPrice(totalAmount);

			order.setTime(new Timestamp(System.currentTimeMillis())); // Thời gian hiện tại
			order.setAddress(address);
			order.getUserInfo().setPhone(tel);
			order.setNote(note);

			orderMap.put("order", order);
			// Lưu đơn hàng vào database
			ordersDAO.saveOrder(order);
			List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();

			// Lưu chi tiết đơn hàng (OrderDetails) cho từng sản phẩm trong giỏ hàng
			for (int cartId : cartIds) {
				ProductDTO productDTO = new ProductDTO();

				CartEntity cartItem = cartDAO.getCartById(cartId);
				OrderDetailsEntity orderDetails = new OrderDetailsEntity();
				OrderDetailId orderDetailId = new OrderDetailId();
				orderDetailId.setOrder(order.getIdOrder());
				orderDetailId.setProduct(cartItem.getProduct().getIdProduct());

				productDTO = productDAO.findProductDTOById(cartItem.getProduct().getIdProduct());
				productDTO.setQuantity(cartItem.getQuantity());
				productsDTO.add(productDTO);

				orderDetails.setId(orderDetailId);
				orderDetails.setOrder(order);
				orderDetails.setProduct(cartItem.getProduct());
				orderDetails.setQuantity(cartItem.getQuantity());
				ProductEntity product = productDAO.getProductById(cartItem.getProduct().getIdProduct());
				product.setQuantity(product.getQuantity() - cartItem.getQuantity());
				productDAO.updateProduct(product);
				orderDetails.setPrice((int) Math
						.round((1 - cartItem.getProduct().getDiscount()) * cartItem.getProduct().getSalePrice()));

				// Lưu chi tiết đơn hàng
				ordersDAO.saveOrderDetail(orderDetails);
				cartDAO.deleteCartById(cartId);
			}
			orderMap.put("orderdetail", productsDTO);
			
			// Lưu thanh toán
			PaymentEntity payment = new PaymentEntity();
			payment.setOrderPayment(order);
			payment.setUserPayment(account.getUserInfo());
			payment.setStatePayment("Chờ thanh toán");
			payment.setPricePayment(totalAmount);
			payment.setMethodPayment((byte)0);
			payment.setTimePayment(new Timestamp(System.currentTimeMillis()));
			paymentDAO.addPayment(payment);
			
			// Tạo thông báo người dùng
			NotificationEntity notification = new NotificationEntity();
		    notification.setOrder(order);
		    notification.setUserNoti(order.getUserInfo());
		    notification.setState(0);
		    String content = "";
		    switch(order.getState()) {
		    case "Chờ lấy hàng":
		    	content = "Đơn hàng có mã " + order.getIdOrder() + " đã đặt thành công. Vui lòng kiểm tra lại thông tin trong phần chi tiết đơn hàng và email (nếu có) từ shop.";
		    	break;
		    case "Đang giao hàng":
		    	content = "Đơn hàng " + order.getIdOrder() + " đã được LapFarm giao cho đơn vị vận chuyển và dự kiến được giao trong 3-5 ngày tới!";
		    	break;
		    case "Hoàn thành":
		    	content = "Đơn hàng " + order.getIdOrder() + " đã được giao thành công đến bạn. Vui lòng kiểm tra và liên hệ lại nếu có vấn đề về sản phẩm!";
		    	break;
		    case "Đã hủy":
		    	content = "Đơn hàng " + order.getIdOrder() + " đã bị hủy. Chúc bạn mua sắm vui vẻ!";
		    	break;
		    default:
		    	content = "LapFarm đang rất nhớ bạn <3";
		    }
		    notification.setContent(content);
		    notification.setTime(new Timestamp(System.currentTimeMillis()));
		    notificationDAO.addNotification(notification);

			httpSession.setAttribute("order", orderMap);

			userDAO.updateUserinfo(account.getEmail(), fullName, account.getUserInfo().getDob(), tel, address,
					account.getUserInfo().getSex());
			account = userDAO.getAccountByEmail(account.getEmail());
			httpSession.removeAttribute("user");
			httpSession.setAttribute("user", account);
			// Xóa giỏ hàng sau khi thanh toán
			httpSession.removeAttribute("cartIdSelecteds");
			httpSession.removeAttribute("OrderInfo");

			// Sau khi lưu đơn hàng và chi tiết, chuyển hướng tới trang thanh toán thành
			// công
			return "paymentSuccess";
		} else {
			// Nếu không có tài khoản (người dùng chưa đăng nhập), chuyển hướng về trang
			// đăng nhập
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public String result(@RequestParam("vnp_Amount") double vnp_Amount,
			@RequestParam("vnp_TransactionStatus") String vnp_TransactionStatus, HttpServletRequest request,
			Model model, HttpSession httpSession) {

		Map<String, Object> orderInfo = (Map<String, Object>) httpSession.getAttribute("OrderInfo");
		String fullName = (String) orderInfo.get("fullName");
		String address = (String) orderInfo.get("address");
		String tel = (String) orderInfo.get("tel");
		String note = (String) orderInfo.get("note");

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

			List<Integer> cartIds = (List<Integer>) httpSession.getAttribute("cartIdSelecteds");

			if (cartIds.isEmpty()) {
				model.addAttribute("error", "Bạn chưa chọn sản phẩm.");
				return "cart"; // Quay lại trang giỏ hàng nếu không có sản phẩm
			}
			Map<String, Object> orderMap = new HashMap<String, Object>();

			// Tạo đơn hàng mới
			OrdersEntity order = new OrdersEntity();
			order.setUserInfo(account.getUserInfo());
			order.setState("Chờ lấy hàng"); // Trạng thái ban đầu là "Chờ lấy hàng"
			order.setPaymentMethod((byte) 1);
			order.setTotalPrice((int) (vnp_Amount / 100)); // Tính tổng giá trị đơn hàng từ các sản
			// phẩm
			// trong giỏ hàng
			order.setTime(new Timestamp(System.currentTimeMillis())); // Thời gian hiện tại
			order.setAddress(address);
			order.getUserInfo().setPhone(tel);
			order.setNote(note);
			orderMap.put("order", order);

			List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();

			// Lưu chi tiết đơn hàng (OrderDetails) cho từng sản phẩm trong giỏ hàng
			for (int cartId : cartIds) {
				ProductDTO productDTO = new ProductDTO();

				CartEntity cartItem = cartDAO.getCartById(cartId);

				productDTO = productDAO.findProductDTOById(cartItem.getProduct().getIdProduct());
				productDTO.setQuantity(cartItem.getQuantity());
				productsDTO.add(productDTO);
			}
			orderMap.put("orderdetail", productsDTO);

			httpSession.setAttribute("order", orderMap);

			Map<String, String> fields = new HashMap<>();
			try {
				// Lấy tất cả các tham số từ request và mã hóa chúng
				for (Enumeration<?> params = request.getParameterNames(); params.hasMoreElements();) {
					String fieldName = URLEncoder.encode((String) params.nextElement(),
							StandardCharsets.US_ASCII.toString());
					String fieldValue = URLEncoder.encode(request.getParameter(fieldName),
							StandardCharsets.US_ASCII.toString());
					if ((fieldValue != null) && (fieldValue.length() > 0)) {
						fields.put(fieldName, fieldValue);
					}
				}
			} catch (UnsupportedEncodingException e) {
				// Xử lý ngoại lệ nếu có
				model.addAttribute("message", "Lỗi mã hóa: " + e.getMessage());
				return "result"; // Trả về trang lỗi nếu có lỗi mã hóa
			}

			// Lấy giá trị vnp_SecureHash từ request
			String vnp_SecureHash = request.getParameter("vnp_SecureHash");

			// Xóa các tham số không cần thiết khỏi Map
			fields.remove("vnp_SecureHashType");
			fields.remove("vnp_SecureHash");

			// Tạo chữ ký từ các tham số
			String signValue = Config.hashAllFields(fields);

			// So sánh chữ ký đã tạo với vnp_SecureHash nhận được
			if (signValue.equals(vnp_SecureHash)) {
				// Kiểm tra trạng thái giao dịch
				if ("00".equals(vnp_TransactionStatus)) {

					// Lưu đơn hàng vào database
					ordersDAO.saveOrder(order);
					for (int cartId : cartIds) {
						CartEntity cartItem = cartDAO.getCartById(cartId);
						OrderDetailsEntity orderDetails = new OrderDetailsEntity();
						OrderDetailId orderDetailId = new OrderDetailId();
						orderDetailId.setOrder(order.getIdOrder());
						orderDetailId.setProduct(cartItem.getProduct().getIdProduct());

						orderDetails.setId(orderDetailId);
						orderDetails.setOrder(order);
						orderDetails.setProduct(cartItem.getProduct());
						orderDetails.setQuantity(cartItem.getQuantity());
						ProductEntity product = productDAO.getProductById(cartItem.getProduct().getIdProduct());
						product.setQuantity(product.getQuantity() - cartItem.getQuantity());
						productDAO.updateProduct(product);
						orderDetails.setPrice((int) Math.round(
								(1 - cartItem.getProduct().getDiscount()) * cartItem.getProduct().getSalePrice()));

						// Lưu chi tiết đơn hàng
						ordersDAO.saveOrderDetail(orderDetails);
						cartDAO.deleteCartById(cartId);
					}

					userDAO.updateUserinfo(account.getEmail(), fullName, account.getUserInfo().getDob(), tel, address,
							account.getUserInfo().getSex());
					account = userDAO.getAccountByEmail(account.getEmail());
					
					// Lưu thanh toán
					PaymentEntity payment = new PaymentEntity();
					payment.setOrderPayment(order);
					payment.setUserPayment(account.getUserInfo());
					payment.setStatePayment("Thành công");
					payment.setPricePayment(order.getTotalPrice());
					payment.setMethodPayment((byte)1);
					payment.setTimePayment(new Timestamp(System.currentTimeMillis()));
					paymentDAO.addPayment(payment);
					
					httpSession.removeAttribute("user");
					httpSession.setAttribute("user", account);
					// Xóa giỏ hàng sau khi thanh toán
					httpSession.removeAttribute("cartIdSelecteds");
					httpSession.removeAttribute("OrderInfo");

					model.addAttribute("message", "Thanh toán thành công!");
				} else {
					// Giao dịch không thành công
					
					// Lưu thanh toán
					PaymentEntity payment = new PaymentEntity();
					payment.setOrderPayment(order);
					payment.setUserPayment(account.getUserInfo());
					payment.setStatePayment("Thất bại");
					payment.setPricePayment(order.getTotalPrice());
					payment.setMethodPayment((byte)1);
					payment.setTimePayment(new Timestamp(System.currentTimeMillis()));
					paymentDAO.addPayment(payment);
					
					model.addAttribute("message", "Thanh toán không thành công!");

				}
			} else {
				// Nếu chữ ký không khớp
				model.addAttribute("message", "Lỗi xác thực chữ ký!");

			}

			return "result"; // Trả về trang kết quả
		}
		return "result";
	}
}