package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
public class NotificationEntity {

	@Id
	private Long id; // Đây là trường khóa chính (primary key)

	// Các trường khác của lớp NotificationEntity
	private String message;
	private String timestamp;

	// Getters và Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}