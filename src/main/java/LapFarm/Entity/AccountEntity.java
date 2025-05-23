package LapFarm.Entity;
import java.io.ObjectInputFilter.Status;
import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    @Column(name = "Email", nullable = false, length = 50)
    private String email;
    @Column(name = "Password", nullable = false, length = 50)
    private String password;
    @Column(name = "State", nullable = false, length = 20)
    private String state;
    @ManyToOne
    @JoinColumn(name = "Role", referencedColumnName = "Id", nullable = false)
    private RoleEntity role;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserInfoEntity userInfo;

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
        if (userInfo != null) {
            userInfo.setAccount(this);
        }
    }
    // Constructors
    public AccountEntity() {
        this.role = new RoleEntity(0, "user");
        this.state = "Hoạt động";
    }
    public AccountEntity(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = new RoleEntity(0, "user");
        this.state = "Hoạt động";
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public RoleEntity getRole() {
        return role;
    }
    public void setRole(RoleEntity role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return "AccountEntity{" + "email='" + email + '\'' + ", state='" + state + '\'' + ", role=" + role + '}';
    }
    
    @Column(name = "lastPasswordChangeDate")
    private LocalDateTime lastPasswordChangeDate;

    // Getter và setter cho lastPasswordChangeDate
    public LocalDateTime getLastPasswordChangeDate() {
        return lastPasswordChangeDate;
    }

    public void setLastPasswordChangeDate(LocalDateTime lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }
    

    @Column(name = "failedAttempts")
    private Integer failedAttempts; // Thay đổi từ int thành Integer

    // Các getter và setter
    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
    
    @Column(name = "lockedUntil")
    private LocalDateTime lockedUntil;

    // Getter và setter cho lockedUntil
    public LocalDateTime getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(LocalDateTime lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

}