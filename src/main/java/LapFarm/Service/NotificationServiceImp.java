package LapFarm.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LapFarm.DAO.NotificationDAO;
import LapFarm.DTO.NotificationDTO;
@Service
public class NotificationServiceImp implements INotificationService {

	@Autowired
	NotificationDAO notificationDAO;
	
	@Override
	public List<NotificationDTO> getNotificationsByUserId(int userId) {
		// TODO Auto-generated method stub
		return notificationDAO.getNotificationsByUserId(userId);
	}

	@Override
	public int getUnreadNotificationsCount(int userId) {
		// TODO Auto-generated method stub
		return notificationDAO.getUnreadNotificationsCount(userId);
	}

}
