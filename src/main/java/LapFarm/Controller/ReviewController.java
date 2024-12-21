package LapFarm.Controller;

import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Entity.ReviewEntity;
import LapFarm.Entity.UserInfoEntity;
import LapFarm.Service.ReviewService;
import LapFarm.Utils.SecureUrlUtil;
import LapFarm.Utils.XSSUtils;
import LapFarm.Service.ProductService;

import java.security.Timestamp;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;  // Inject ReviewService để thao tác với cơ sở dữ liệu

    @Autowired
    private ProductService productService;  // Inject ProductService để tìm sản phẩm

    @RequestMapping(value = "/submitReview", method = RequestMethod.POST)
    public String submitReview(@RequestParam("encryptedProductId") String encryptedProductId, // ID sản phẩm đã mã hóa
                               @RequestParam("review") String review,
                               @RequestParam("rating") int rating,
                               HttpSession session, Model model) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        AccountEntity user = (AccountEntity) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errorMessage", "Bạn cần đăng nhập để gửi đánh giá.");
            return "redirect:/login"; // Chuyển hướng người dùng đến trang đăng nhập
        }

        try {
            // Giải mã encryptedProductId để lấy productId
            int productId;
            try {
                String decryptedId = SecureUrlUtil.decrypt(encryptedProductId);  // Giải mã ID sản phẩm
                if (decryptedId == null || decryptedId.isEmpty() || !decryptedId.matches("\\d+")) {
                    throw new IllegalArgumentException("ID sản phẩm không hợp lệ.");
                }
                productId = Integer.parseInt(decryptedId);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "ID sản phẩm không hợp lệ hoặc bị thay đổi.");
                return "redirect:/products"; // Chuyển hướng đến trang sản phẩm nếu ID không hợp lệ
            }

            // Kiểm tra nội dung đánh giá có chứa script hay không
            if (XSSUtils.containsXSS(review)) {
                model.addAttribute("errorMessage", "Đánh giá của bạn có chứa nội dung không hợp lệ.");
                return "redirect:/product-detail/" + encryptedProductId; // Không cho phép lưu nếu có nội dung không hợp lệ
            }

            // Tìm sản phẩm từ ID
            ProductEntity product = productService.getProductById(productId);  // Lấy sản phẩm theo ID
            if (product == null) {
                model.addAttribute("errorMessage", "Sản phẩm không tồn tại.");
                return "redirect:/products"; // Chuyển hướng đến trang sản phẩm nếu không tìm thấy sản phẩm
            }

            // Xử lý lưu thông tin đánh giá vào cơ sở dữ liệu
            ReviewEntity newReview = new ReviewEntity();
            newReview.setProduct(product);  // Gán sản phẩm vào đánh giá
            newReview.setComment(review);
            newReview.setRating(rating);
            newReview.setUser(user); // Gán người dùng đã đăng nhập
            reviewService.saveReview(newReview); // Lưu đánh giá vào cơ sở dữ liệu

            model.addAttribute("successMessage", "Đánh giá của bạn đã được gửi thành công.");
            return "redirect:/product-detail/" + encryptedProductId; // Chuyển hướng người dùng về trang sản phẩm với ID mã hóa
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã có lỗi xảy ra trong quá trình gửi đánh giá.");
            return "redirect:/product-detail/" + encryptedProductId; // Chuyển hướng về trang sản phẩm nếu có lỗi
        }
    }

}
