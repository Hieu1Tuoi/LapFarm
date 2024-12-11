package LapFarm.DTO;

import java.util.Map;

public class ProductDTO {
	private int idProduct;
	private String nameProduct;
	private int idBrand;
	private String brandName; // Tên thương hiệu thay vì đối tượng Brand
	private int idCategory;
	private String categoryName;// Tên danh mục thay vì đối tượng Category
	private String description;
	private Integer quantity;
	private Double discount;
	private Double originalPrice;
	private Double salePrice;
	private String state;
	private String image; // Đường dẫn ảnh chính (nếu cần)
	 private String encryptedId; 

	// Constructor

	public ProductDTO(Integer idProduct, String nameProduct, int idBrand, String brandName, String categoryName, int idCategory,
			String description, Integer quantity, Double discount, Double originalPrice, Double salePrice, String state,
			String image) {
		this.idProduct = idProduct;
		this.nameProduct = nameProduct;
		this.idBrand = idBrand;
		this.brandName = brandName;
		this.idCategory = idCategory;
		this.categoryName = categoryName;
		this.description = description;
		this.quantity = quantity;
		this.discount = discount;
		this.originalPrice = originalPrice;
		this.salePrice = salePrice;
		this.state = state;
		this.image = image;
	}
	
	public int getIdBrand() {
		return idBrand;
	}

	public void setIdBrand(int idBrand) {
		this.idBrand = idBrand;
	}

	public ProductDTO() {
		super();
	}


	// Getter và Setter
	public int getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(int idProduct) {
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

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
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

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	

	// Các phương thức bổ sung nếu cần (ví dụ như tính toán giá đã giảm, định dạng
	// tiền tệ...)
	public long calOriginalPrice() {
		return (long) (this.originalPrice * 1);
	}

	public long calPrice() {
		return (long) (this.salePrice - (this.discount * this.salePrice));
	}

	public long calSalePrice() {
		return (long) (this.salePrice * 1);
	}
	private Map<String, Object> ratingSummary;

    // Getter và Setter cho ratingSummary
    public Map<String, Object> getRatingSummary() {
        return ratingSummary;
    }

    public void setRatingSummary(Map<String, Object> ratingSummary) {
        this.ratingSummary = ratingSummary;
    }
    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }
}