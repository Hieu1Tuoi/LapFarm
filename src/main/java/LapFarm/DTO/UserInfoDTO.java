package LapFarm.DTO;

public class UserInfoDTO {
    private int userId;
    private String email;
    private String fullName;
    private String dob; // Date of birth
    private String sex;
    private String phone;
    private String avatar;
    private String address;
    private long numberOfOrders;
    private String state;

    // Constructor
    public UserInfoDTO(int userId, String email, String fullName, String dob, 
                       String sex, String phone, String avatar, String address, 
                       long numberOfOrders, String state) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.dob = dob;
        this.sex = sex;
        this.phone = phone;
        this.avatar = avatar;
        this.address = address;
        this.numberOfOrders = numberOfOrders;
        this.state = state;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(long numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
   
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// Optional: Override toString() for debugging purposes
    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dob='" + dob + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", numberOfOrders=" + numberOfOrders +
                ", state=" + state +
                '}';
    }
}
