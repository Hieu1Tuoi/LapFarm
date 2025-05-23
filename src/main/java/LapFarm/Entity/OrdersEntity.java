package LapFarm.Entity;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdOrder")
    private int idOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserOrder", nullable = false)
    private UserInfoEntity userInfo;

    @Column(name = "Time", nullable = false)
    private Timestamp time;

    @Column(name = "State", columnDefinition = "TEXT COLLATE utf8mb4_general_ci", nullable = false, length = 30)
    private String state;

    @Column(name = "TotalPrice", nullable = false)
    private int totalPrice;

    @Column(name = "PaymentMethod", nullable = false)
    private byte paymentMethod; // PaymentMethod: 0 or 1 (TINYINT)

    @Column(name = "Note", columnDefinition = "TEXT COLLATE utf8mb4_general_ci", nullable = true)
    private String note; // Note (TEXT)
    
    @Column(name = "Address", columnDefinition = "TEXT COLLATE utf8mb4_general_ci", nullable = false)
    private String address; // Note (TEXT)

    // Getters and Setters
    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public byte getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(byte paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
    public String toString() {
        return "OrdersEntity{" +
                "idOrder=" + idOrder +
                ", userInfo=" + (userInfo != null ? userInfo.getUserId() : null) +
                ", time=" + time +
                ", state='" + state + '\'' +
                ", totalPrice=" + totalPrice +
                ", paymentMethod=" + paymentMethod +
                ", note='" + note + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}