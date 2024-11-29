package LapFarm.DTO;

public class CartDTO {
    private int id; // Thêm trường id
    private int quantity;
    private ProductDTO product;
    private double totalPrice;

    // Constructor đầy đủ bao gồm id
    public CartDTO(int id, int quantity, ProductDTO product, double totalPrice) {
        super();
        this.id = id; // Khởi tạo id
        this.quantity = quantity;
        this.product = product;
        this.totalPrice = totalPrice;
    }

    // Constructor không bao gồm id
    public CartDTO(int quantity, ProductDTO product, double totalPrice) {
        super();
        this.quantity = quantity;
        this.product = product;
        this.totalPrice = totalPrice;
    }

    // Default constructor
    public CartDTO() {
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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