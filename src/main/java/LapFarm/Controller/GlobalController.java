package LapFarm.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import LapFarm.DTO.CartDTO;
import LapFarm.DTO.NotificationDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Service.BaseServiceImp;
import LapFarm.Service.CartServiceImp;
import LapFarm.Service.NotificationServiceImp;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private BaseServiceImp baseService;
    
    @Autowired
    private CartServiceImp cartService;
    
    @Autowired
    private NotificationServiceImp notificationService;

    @ModelAttribute("categories")
    public Object getCategories() {
        return baseService.getCategoryEntities();
    }
    
	/*
	 * @ModelAttribute("CartSession") public HashMap<Integer, CartDTO>
	 * getCart(HttpSession session) { // Kiểm tra người dùng đã đăng nhập chưa
	 * AccountEntity account = (AccountEntity) session.getAttribute("user"); if
	 * (account != null) { // Lấy giỏ hàng từ cơ sở dữ liệu nếu đã đăng nhập return
	 * cartService.getCartFromDatabase(account.getUserInfo().getUserId()); } else {
	 * // Nếu chưa đăng nhập, trả về g iỏ hàng rỗng return new HashMap<>(); } }
	 */
    
    // Cung cấp danh sách thông báo cho tất cả các trang
    @ModelAttribute("notifications")
    public List<NotificationDTO> getNotifications(HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute("user");
        // Kiểm tra xem account có null không
        if (account != null && account.getUserInfo() != null) {
            int userId = account.getUserInfo().getUserId();
            // Gọi service để lấy thông báo
            return notificationService.getNotificationsByUserId(userId);
        } else {
            // Trả về danh sách thông báo rỗng nếu chưa đăng nhập
            return new ArrayList<>();
        }
    }

    @ModelAttribute("unreadNotificationsCount")
    public int getUnreadNotificationsCount(HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute("user");

        // Kiểm tra xem account có null không
        if (account != null && account.getUserInfo() != null) {
            int userId = account.getUserInfo().getUserId();
            // Gọi service để lấy số lượng thông báo chưa đọc
            return notificationService.getUnreadNotificationsCount(userId);
        } else {
            // Trả về 0 nếu chưa đăng nhập
            return 0;
        }
    
    }
    
    

}
