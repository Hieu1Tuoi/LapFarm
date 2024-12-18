package LapFarm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import LapFarm.DAO.NotificationDAO;
import LapFarm.DTO.NotificationDTO;
import LapFarm.Utils.SecureUrlUtil;

@Controller
public class NotificationController {

    @Autowired
    private NotificationDAO notificationDAO;

    @GetMapping("/notification/{notiId}")
    public String updateNotificationStatusAndRedirect(@RequestParam("state") int state, 
                                                      @PathVariable("notiId") String encryptedNotiId, 
                                                      Model model) {
        try {
            // Giải mã notiId
            String decryptedNotiId = SecureUrlUtil.decrypt(encryptedNotiId);
            int notiId = Integer.parseInt(decryptedNotiId);

            // Đánh dấu thông báo là đã đọc
            boolean updated = notificationDAO.updateNotificationStatus(notiId, state);

            if (updated) {
                model.addAttribute("message", "Trạng thái thông báo đã được cập nhật.");
            } else {
                model.addAttribute("message", "Không tìm thấy thông báo.");
            }

            // Lấy chi tiết thông báo và chuyển hướng đến trang chi tiết thông báo
            NotificationDTO notification = notificationDAO.getNotificationDetails(notiId);
            if (notification != null) {
            	 try {
                     // Mã hóa orderId
                     String encryptedOrderId = SecureUrlUtil.encrypt(String.valueOf(notification.getOrderId()));
                     notification.setEncryptOrderId(encryptedOrderId); // Gán giá trị mã hóa vào notification
                 } catch (Exception e) {
                     e.printStackTrace();
                     model.addAttribute("error", "Lỗi mã hóa ID đơn hàng.");
                 }

                model.addAttribute("notification", notification);
            } else {
                model.addAttribute("message", "Không tìm thấy thông báo.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi giải mã ID thông báo.");
        }

        return "user/notifications/notification";
    }
}
