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
import LapFarm.Utils.SecureUrlUtil;
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
    
    // Mã hóa userId khi lấy thông báo
    // Cung cấp danh sách thông báo với mã hóa notiId
    @ModelAttribute("notifications")
    public List<NotificationDTO> getNotifications(HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute("user");
        if (account != null && account.getUserInfo() != null) {
            int userId = account.getUserInfo().getUserId();
            List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(userId);
            // Mã hóa notiId cho mỗi thông báo
            for (NotificationDTO notification : notifications) {
                try {
                    String encryptedNotiId = SecureUrlUtil.encrypt(String.valueOf(notification.getNotiId()));
                    notification.setEncryptedId(encryptedNotiId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return notifications;
        } else {
            return new ArrayList<>();
        }
    }

    // Mã hóa notiId khi lấy số lượng thông báo chưa đọc (nếu cần)
    @ModelAttribute("unreadNotificationsCount")
    public int getUnreadNotificationsCount(HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute("user");
        if (account != null && account.getUserInfo() != null) {
            int userId = account.getUserInfo().getUserId();
            return notificationService.getUnreadNotificationsCount(userId);
        } else {
            return 0;
        }
    }
}
