package LapFarm.DTO;

import java.time.LocalDateTime;

public class NotificationDTO {

    private int notiId;
    private int idUser;
    private String content;
    private LocalDateTime time;  // Sử dụng LocalDateTime cho dễ thao tác với thời gian
    private int state;
    private int orderId;  // Thêm idOrder từ bảng Orders
    private String encryptOrderId;
    private String encryptedId;
    // Constructors
    public NotificationDTO() {
    }

    public NotificationDTO(int notiId, int idUser, String content, LocalDateTime time, int state, int orderId) {
        this.notiId = notiId;
        this.idUser = idUser;
        this.content = content;
        this.time = time;
        this.state = state;
        this.orderId = orderId;
    }

    // Getters and Setters
    public int getNotiId() {
        return notiId;
    }

    public void setNotiId(int notiId) {
        this.notiId = notiId;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    

    public String getEncryptOrderId() {
		return encryptOrderId;
	}

	public void setEncryptOrderId(String encryptOrderId) {
		this.encryptOrderId = encryptOrderId;
	}

	public String getEncryptedId() {
		return encryptedId;
	}

	public void setEncryptedId(String encryptedId) {
		this.encryptedId = encryptedId;
	}

	@Override
    public String toString() {
        return "NotificationDTO{" +
                "notiId=" + notiId +
                ", idUser=" + idUser +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", state=" + state +
                ", orderId=" + orderId +
                '}';
    }
}
