package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "supplier")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSupplier")
    private int idSupplier;

    @Column(name = "SupplierName", nullable = false, length = 100)
    private String supplierName;

    @Column(name = "Phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "Address", nullable = false, length = 200)
    private String address;

    @Column(name = "Email", nullable = false, length = 50)
    private String email;

    // Getters and Setters
    public int getIdSupplier() { return idSupplier; }
    public void setIdSupplier(int idSupplier) { this.idSupplier = idSupplier; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "SupplierEntity{" + "idSupplier=" + idSupplier + ", supplierName='" + supplierName + '\'' + ", phone='" + phone + '\'' + ", address='" + address + '\'' + ", email='" + email + '\'' + '}';
    }
}