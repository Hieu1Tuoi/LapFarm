package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shoppingcart")
public class ShoppingCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tạo giá trị cho khóa chính
    @Column(name = "Id")
    private int id; // Thêm trường id để làm khóa chính

    @ManyToOne
    @JoinColumn(name = "UserCart", referencedColumnName = "UserId", nullable = false)
    private UserInfoEntity userInfo;

    @ManyToOne
    @JoinColumn(name = "ProductCart", referencedColumnName = "IdProduct", nullable = false)
    private ProductEntity product;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public UserInfoEntity getUserInfo() { return userInfo; }
    public void setUserInfo(UserInfoEntity userInfo) { this.userInfo = userInfo; }

    public ProductEntity getProduct() { return product; }
    public void setProduct(ProductEntity product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "ShoppingCartEntity{" + "id=" + id + ", userInfo=" + userInfo + ", product=" + product + ", quantity=" + quantity + '}';
    }
}