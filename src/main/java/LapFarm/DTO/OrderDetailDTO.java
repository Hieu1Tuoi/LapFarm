package LapFarm.DTO;


public class OrderDetailDTO {
    ProductDTO product;
    private int quantity;
    private int price;

    // Constructor
    public OrderDetailDTO(ProductDTO product, int quantity, int price) {
    	super();
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
    
    public OrderDetailDTO() {
    	super();
    }

    // Getters and Setters
    
    public int getQuantity() {
        return quantity;
    }

    public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // toString() method
    @Override
    public String toString() {
        return "OrderDetailDTO{" +
        		"ProductDTO=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}