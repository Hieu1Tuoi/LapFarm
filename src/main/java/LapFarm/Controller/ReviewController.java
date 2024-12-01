package LapFarm.Controller;

import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Entity.ReviewEntity;
import LapFarm.Entity.UserInfoEntity;
import LapFarm.Service.ReviewService;
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

    // Phương thức xử lý submit review
    @RequestMapping(value = "/submitReview", method = RequestMethod.POST)
    public String submitReview(@RequestParam("productId") int productId, // Chỉ cần Long ID
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
            return "redirect:/product-detail/" + productId; // Chuyển hướng người dùng về trang sản phẩm sau khi gửi đánh giá
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã có lỗi xảy ra trong quá trình gửi đánh giá.");
            return "redirect:/product-detail/" + productId; // Chuyển hướng về trang sản phẩm nếu có lỗi
        }
    }
}
