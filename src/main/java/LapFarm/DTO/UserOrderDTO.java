package LapFarm.DTO;

public class UserOrderDTO {
    private String fullName;
    private String phone;
    private String address;
    private String email;
    private double totalAmount;
    
    public UserOrderDTO(String fullName, String phone, String address, String email, double totalAmount) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.totalAmount = totalAmount;
    }
    
    // Getters and setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}