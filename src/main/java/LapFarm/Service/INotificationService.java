package LapFarm.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.NotificationDTO;

@Service
public interface INotificationService {
	 public List<NotificationDTO> getNotificationsByUserId(int userId);
	 public int getUnreadNotificationsCount(int userId) ;
}
