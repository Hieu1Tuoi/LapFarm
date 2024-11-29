package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class CartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdCart", nullable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false)
	private UserInfoEntity userInfo;

	@ManyToOne
	@JoinColumn(name = "ProductId", referencedColumnName = "IdProduct", nullable = false)
	private ProductEntity product;

	@Column(name = "Quantity", nullable = false)
	private int quantity;

	// Getter và Setter cho id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// Getter và Setter cho userInfo
	public UserInfoEntity getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoEntity userInfo) {
		this.userInfo = userInfo;
	}

	// Getter và Setter cho product
	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	// Getter và Setter cho quantity
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartEntity{" + "id=" + id + ", userInfo=" + userInfo + ", product=" + product + ", quantity=" + quantity
				+ '}';
	}
}