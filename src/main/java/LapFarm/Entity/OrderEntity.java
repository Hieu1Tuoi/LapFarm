package LapFarm.Entity;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdOrder")
    private int idOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserOrder", nullable = false)
    private UserInfoEntity userInfo;

    @Column(name = "Time", nullable = false)
    private Timestamp time;

    @Column(name = "State", nullable = false, length = 20)
    private String state;

    @Column(name = "TotalPrice", nullable = false)
    private int totalPrice;

    // Getters and Setters
    public int getIdOrder() { return idOrder; }
    public void setIdOrder(int idOrder) { this.idOrder = idOrder; }

    public UserInfoEntity getUserInfo() { return userInfo; }
    public void setUserInfo(UserInfoEntity userInfo) { this.userInfo = userInfo; }

    public Timestamp getTime() { return time; }
    public void setTime(Timestamp time) { this.time = time; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "idOrder=" + idOrder +
                ", userInfo=" + (userInfo != null ? userInfo.getUserId() : null) +
                ", time=" + time +
                ", state='" + state + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}