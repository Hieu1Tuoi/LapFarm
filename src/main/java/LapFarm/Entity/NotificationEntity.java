package LapFarm.Entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotiId")
    private int notiId;

    @ManyToOne(fetch = FetchType.LAZY)  // Lấy thông tin người dùng theo yêu cầu
    @JoinColumn(name = "UserNoti", referencedColumnName = "UserId")
    private UserInfoEntity userNoti;

    @Column(name = "Content", nullable = false)
    private String content;

    @Column(name = "Time", nullable = false, updatable = false)
    private Timestamp time;  // Thời gian tạo thông báo

    @Column(name = "State", nullable = false)
    private int state; // Trạng thái thông báo

    @ManyToOne(fetch = FetchType.LAZY)  // Lấy thông tin đơn hàng liên quan đến thông báo
    @JoinColumn(name = "IdOrder", referencedColumnName = "IdOrder")
    private OrdersEntity order;  // Liên kết đến bảng đơn hàng

    // Getters and Setters
    public int getNotiId() {
        return notiId;
    }

    public void setNotiId(int notiId) {
        this.notiId = notiId;
    }

    public UserInfoEntity getUserNoti() {
        return userNoti;
    }

    public void setUserNoti(UserInfoEntity userNoti) {
        this.userNoti = userNoti;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public OrdersEntity getOrder() {
        return order;
    }

    public void setOrder(OrdersEntity order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "NotificationEntity{" +
                "notiId=" + notiId +
                ", userNoti=" + (userNoti != null ? userNoti.getUserId() : null) +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", state=" + state +
                ", order=" + (order != null ? order.getIdOrder() : null) +
                '}';
    }
}
