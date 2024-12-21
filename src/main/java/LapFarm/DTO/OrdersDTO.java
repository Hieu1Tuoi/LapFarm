package LapFarm.DTO;

import java.util.Date;
import java.util.List;

public class OrdersDTO {
    private int orderId;
    private Date time;
    private String state;
    private long  totalPrice;
    private String userFullname;
    private byte paymentMethod;  // PaymentMethod (0 or 1)
    private String note;        // Order note
    private List<OrderDetailDTO> orderDetails; // List of order details (product info)
    private String encryptedId; 
    private String address;
    
    public OrdersDTO(int orderId, Date time, String state, long totalPrice, String userFullname, byte paymentMethod, String note, String address) {
        this.orderId = orderId;
        this.time = time;
        this.state = state;
        this.totalPrice = totalPrice;
        this.userFullname = userFullname;
        this.paymentMethod = paymentMethod;
        this.note = note;
        this.address = address;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public int getPaymentMethod() {
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

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
    
    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }
    
    

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// toString() method
    @Override
    public String toString() {
        return "OrdersDTO{" +
                "orderId=" + orderId +
                ", time=" + time +
                ", state='" + state + '\'' +
                ", totalPrice=" + totalPrice +
                ", userFullname='" + userFullname + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", note='" + note + '\'' +
                ", orderDetails=" + orderDetails +
                ", address" + address +
                '}';
    }
}