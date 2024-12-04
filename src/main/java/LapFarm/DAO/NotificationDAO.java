package LapFarm.DAO;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import LapFarm.DTO.NotificationDTO;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.NotificationEntity;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Transactional
@Repository
public class NotificationDAO {

    @Autowired
    private SessionFactory factory;

    // Hàm lấy thông báo theo idUser và sắp xếp theo thời gian (mới nhất lên đầu)
    public List<NotificationDTO> getNotificationsByUserId(int userId) {
        Session session = factory.getCurrentSession();

        String hql = "FROM NotificationEntity ne WHERE ne.userNoti.userId = :userId ORDER BY ne.time DESC";
        Query<NotificationEntity> query = session.createQuery(hql, NotificationEntity.class);
        query.setParameter("userId", userId);

        List<NotificationEntity> notifications = query.getResultList();

        // Chuyển từ NotificationEntity sang NotificationDTO
        return notifications.stream()
                            .map(ne -> new NotificationDTO(
                                    ne.getNotiId(),                           // ID thông báo
                                    ne.getUserNoti().getUserId(),             // ID người dùng
                                    ne.getContent(),                          // Nội dung thông báo
                                    convertTimestampToLocalDateTime(ne.getTime()), // Chuyển đổi từ Timestamp sang LocalDateTime
                                    ne.getState(),                           // Trạng thái thông báo
                                    ne.getOrder().getIdOrder()                          // Thêm ID đơn hàng nếu có
                            ))
                            .collect(Collectors.toList());
    }

    // Hàm chuyển đổi Timestamp sang LocalDateTime
    private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toLocalDateTime(); // Chuyển đổi Timestamp thành LocalDateTime
        }
        return null; // Trả về null nếu Timestamp là null
    }

    // Hàm cập nhật trạng thái thông báo
    public boolean updateNotificationStatus(int notiId, int state) {
        Session session = factory.getCurrentSession();

        // Tìm thông báo theo ID
        NotificationEntity notification = session.get(NotificationEntity.class, notiId);
        
        if (notification != null) {
            // Cập nhật trạng thái
            notification.setState(state);
            session.update(notification); // Cập nhật thông báo trong cơ sở dữ liệu
            return true;
        }

        return false; // Nếu không tìm thấy thông báo
    }
    
 // Hàm lấy thông báo chi tiết theo notiId
    public NotificationDTO getNotificationDetails(int notiId) {
        Session session = factory.getCurrentSession();
        NotificationEntity notification = session.get(NotificationEntity.class, notiId);

        if (notification != null) {
            return new NotificationDTO(
                notification.getNotiId(),
                notification.getUserNoti().getUserId(),
                notification.getContent(),
                convertTimestampToLocalDateTime(notification.getTime()),
                notification.getState(),
                notification.getOrder().getIdOrder()
            );
        }

        return null;
    }

    // Hàm lấy số lượng thông báo chưa đọc
    public int getUnreadNotificationsCount(int userId) {
        Session session = factory.getCurrentSession();

        // Sử dụng HQL để đếm số lượng thông báo chưa đọc (state = 0)
        String hql = "SELECT COUNT(ne) FROM NotificationEntity ne WHERE ne.userNoti.userId = :userId AND ne.state = 0";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("userId", userId);

        // Trả về số lượng thông báo chưa đọc
        Long count = query.uniqueResult();
        return count != null ? count.intValue() : 0;
    }
    
    public void addNotification(NotificationEntity note) {
        Session session = factory.getCurrentSession();

        // Sử dụng phương thức persist để lưu đối tượng vào database
        session.persist(note);
    }
    
}
