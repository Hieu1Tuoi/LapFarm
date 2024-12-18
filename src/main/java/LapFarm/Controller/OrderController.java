package LapFarm.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import LapFarm.DTO.OrderDetailDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Utils.SecureUrlUtil;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String index(HttpSession httpSession, Model model) {
	    Map<String, Object> order = (Map<String, Object>) httpSession.getAttribute("order");
	    if (order == null) {
	        return "redirect:/";
	    }

	    // Lấy orderdetail và chuyển từ ProductDTO sang OrderDetailDTO nếu cần
	    List<ProductDTO> products = (List<ProductDTO>) order.get("orderdetail");
	    List<OrderDetailDTO> orderDetails = new ArrayList<>();
	    for (ProductDTO product : products) {
	        OrderDetailDTO detail = new OrderDetailDTO();
	        detail.setProduct(product);
	        try {
	            // Mã hóa idProduct
	            String encryptedId = SecureUrlUtil.encrypt(String.valueOf(product.getIdProduct()));
	            product.setEncryptedId(encryptedId);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        orderDetails.add(detail);
	    }

	    // Đưa dữ liệu vào model
	    model.addAttribute("order", order.get("order"));
	    model.addAttribute("orderdetail", orderDetails);

	    return "user/orders/detail";
	}


}
