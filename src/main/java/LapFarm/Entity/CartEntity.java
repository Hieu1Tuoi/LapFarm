package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class CartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdCart") // Correct the column name here
	private int id;

	@ManyToOne
	@JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false)
	private UserInfoEntity userInfo;

	@ManyToOne
	@JoinColumn(name = "ProductId", referencedColumnName = "IdProduct", nullable = false)
	private ProductEntity product;

	@Column(name = "Quantity", nullable = false)
	private int quantity;

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserInfoEntity getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoEntity userInfo) {
		this.userInfo = userInfo;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ShoppingCartEntity{" + "id=" + id + ", userInfo=" + userInfo + ", product=" + product + ", quantity="
				+ quantity + '}';
	}
}