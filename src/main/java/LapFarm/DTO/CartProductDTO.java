package LapFarm.DTO;

public class CartProductDTO {
    private String productName;
    private int quantity;
    private String formattedPrice;
    private double totalPrice;

    public CartProductDTO(String productName, int quantity, String formattedPrice, double totalPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.formattedPrice = formattedPrice;
        this.totalPrice = totalPrice;
    }
    

    public CartProductDTO() {
	}


	// Getters and Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFormattedPrice() {
        return formattedPrice;
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}