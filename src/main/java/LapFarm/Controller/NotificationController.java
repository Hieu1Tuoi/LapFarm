package LapFarm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import LapFarm.DAO.NotificationDAO;
import LapFarm.DTO.NotificationDTO;
@Controller
public class NotificationController {

    @Autowired
    private NotificationDAO notificationDAO;

    // Cập nhật trạng thái của thông báo và chuyển hướng đến trang chi tiết
    @GetMapping("/notification/{notiId}")
    public String updateNotificationStatusAndRedirect(@RequestParam("state") int state, @PathVariable("notiId") int notiId, Model model) {
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
            model.addAttribute("notification", notification);
        } else {
            model.addAttribute("message", "Không tìm thấy thông báo.");
        }

        return "user/notifications/notification";
    
    }
    
}
