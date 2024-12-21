package LapFarm.DTO;

import java.sql.Timestamp;

import LapFarm.Entity.OrdersEntity;
import LapFarm.Entity.UserInfoEntity;

public class PaymentDTO {

    private int idPayment;
    private OrdersEntity orderPayment;  // Chứa đơn hàng (Order)
    private UserInfoEntity userPayment;   // Chứa người dùng (User)
    private Timestamp timePayment;
    private String statePayment;
    private int pricePayment;
    private byte methodPayment;
    private String encryptedId;
    
    

    public PaymentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaymentDTO(int idPayment, OrdersEntity orderPayment, UserInfoEntity userPayment, Timestamp timePayment, String statePayment,
			int pricePayment, byte methodPayment, String encryptedId, String userName) {
		super();
		this.idPayment = idPayment;
		this.orderPayment = orderPayment;
		this.userPayment = userPayment;
		this.timePayment = timePayment;
		this.statePayment = statePayment;
		this.pricePayment = pricePayment;
		this.methodPayment = methodPayment;
		this.encryptedId = encryptedId;
	}

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

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

	@Override
    public String toString() {
        return "PaymentDTO{" +
                "idPayment=" + idPayment +
                ", orderPaymentId=" + orderPayment +
                ", userPaymentId=" + userPayment +
                ", timePayment=" + timePayment +
                ", statePayment='" + statePayment + '\'' +
                ", pricePayment=" + pricePayment +
                ", methodPayment=" + methodPayment +
                ", encryptedId='" + encryptedId + '\'' +
                '}';
    }
}
