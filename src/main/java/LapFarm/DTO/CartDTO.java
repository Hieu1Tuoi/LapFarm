package LapFarm.DTO;

public class CartDTO {
	private int quantity;
	private ProductDTO product;
	private double totalPrice;

	public CartDTO(int quantity, ProductDTO product, double totalPrice) {
		super();
		this.quantity = quantity;
		this.product = product;
		this.totalPrice = totalPrice;
	}

	public CartDTO() {
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
