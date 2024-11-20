package LapFarm.DTO;

public class ProductDTO {
    private Long idProduct;
    private String nameProduct;
    private String brandName; // Tên thương hiệu thay vì đối tượng Brand
    private String categoryName; // Tên danh mục thay vì đối tượng Category
    private String description;
    private Integer quantity;
    private Double discount;
    private Double salePrice;
    private String image; // Đường dẫn ảnh chính (nếu cần)

    // Constructor
    public ProductDTO(Long idProduct, String nameProduct, String brandName, 
                      String categoryName, String description, Integer quantity,
                      Double discount, Double salePrice, String image) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.brandName = brandName;
        this.categoryName = categoryName;
        this.description = description;
        this.quantity = quantity;
        this.discount = discount;
        this.salePrice = salePrice;
        this.image = image;
    }

    // Getter và Setter
    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Các phương thức bổ sung nếu cần (ví dụ như tính toán giá đã giảm, định dạng tiền tệ...)
    public double calDiscountedPrice() {
        return salePrice - (salePrice * discount);
    }

    public String formatPrice(double price) {
        return String.format("%,.0f₫", price);
    }

    public String getFormattedDiscountedPrice() {
        return formatPrice(calDiscountedPrice());
    }

    public String getFormattedSalePrice() {
        return formatPrice(salePrice);
    }
}
