package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "userinfo")
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId", nullable = false)
    private int userId;

    @Column(name = "Email", nullable = false, length = 50)
    private String email;

    @Column(name = "FullName", nullable = false, length = 50)
    private String fullName;

    @Column(name = "DOB", length = 20)
    private String dob;

    @Column(name = "Sex", length = 10)
    private String sex;

    @Column(name = "Phone", length = 11)
    private String phone;

    @Column(name = "Avatar", length = 50)
    private String avatar;

    @Column(name = "Address", columnDefinition = "TEXT")
    private String address;

    // Constructors
    public UserInfoEntity() {
        this.fullName = "";
        this.dob = null;
        this.sex = null;
        this.phone = null;
        this.avatar = null;
        this.address = null;
    }

    public UserInfoEntity(String email, String fullName, String dob, String sex, String phone, String avatar, String address) {
        this.email = email;
        this.fullName = fullName;
        this.dob = dob;
        this.sex = sex;
        this.phone = phone;
        this.avatar = avatar;
        this.address = address;
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

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dob='" + dob + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}