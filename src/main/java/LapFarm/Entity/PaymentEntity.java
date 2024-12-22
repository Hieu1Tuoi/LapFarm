package LapFarm.Entity;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPayment")
    private int idPayment;

    @ManyToOne
    @JoinColumn(name = "OrderPayment", nullable = false)
    private OrdersEntity orderPayment;

    @ManyToOne
    @JoinColumn(name = "UserPayment",referencedColumnName = "UserId", nullable = false)
    private UserInfoEntity userPayment;

    @Column(name = "TimePayment", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp timePayment;

    @Column(name = "StatePayment", length = 20)
    private String statePayment;

    @Column(name = "PricePayment", nullable = false)
    private int pricePayment;

    @Column(name = "MethodPayment", nullable = false)
    private byte methodPayment;

    // Getters and Setters
    public int getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }

    public OrdersEntity getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(OrdersEntity orderPayment) {
        this.orderPayment = orderPayment;
    }

    public UserInfoEntity getUserPayment() {
        return userPayment;
    }

    public void setUserPayment(UserInfoEntity userPayment) {
        this.userPayment = userPayment;
    }

    public Timestamp getTimePayment() {
        return timePayment;
    }

    public void setTimePayment(Timestamp timePayment) {
        this.timePayment = timePayment;
    }

    public String getStatePayment() {
        return statePayment;
    }

    public void setStatePayment(String statePayment) {
        this.statePayment = statePayment;
    }

    public int getPricePayment() {
        return pricePayment;
    }

    public void setPricePayment(int pricePayment) {
        this.pricePayment = pricePayment;
    }

    public byte getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(byte methodPayment) {
        this.methodPayment = methodPayment;
    }
    
 // toString method
    @Override
    public String toString() {
        return "PaymentEntity{" +
                "idPayment=" + idPayment +
                ", orderPayment=" + (orderPayment != null ? orderPayment.getIdOrder() : "null") +
                ", userPayment=" + (userPayment != null ? userPayment.getUserId() : "null") +
                ", timePayment=" + timePayment +
                ", statePayment='" + statePayment + '\'' +
                ", pricePayment=" + pricePayment +
                ", methodPayment=" + methodPayment +
                '}';
    }
}
