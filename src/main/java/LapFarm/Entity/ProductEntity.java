package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProduct")
    private Long idProduct;

    @Column(name = "NameProduct", nullable = false)
    private String nameProduct;

    @Column(name = "BrandProduct", nullable = false)
    private Integer brandProduct;

    @Column(name = "IdCategory", nullable = false)
    private Integer idCategory;

    @Column(name = "Describe")
    private String description;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "Discount")
    private Double discount;

    @Column(name = "OriginalPrice", nullable = false)
    private Double originalPrice;

    @Column(name = "SalePrice", nullable = false)
    private Double salePrice;

    @Column(name = "RelatedPromotions")
    private String relatedPromotions;

    @Column(name = "State", nullable = false)
    private String state;

    // Constructors
    public ProductEntity() {}
    


    public ProductEntity(Long idProduct, String nameProduct, Integer brandProduct, Integer idCategory,
			String description, Integer quantity, Double discount, Double originalPrice, Double salePrice,
			String relatedPromotions, String state) {
		super();
		this.idProduct = idProduct;
		this.nameProduct = nameProduct;
		this.brandProduct = brandProduct;
		this.idCategory = idCategory;
		this.description = description;
		this.quantity = quantity;
		this.discount = discount;
		this.originalPrice = originalPrice;
		this.salePrice = salePrice;
		this.relatedPromotions = relatedPromotions;
		this.state = state;
	}



	// Getters and Setters
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

    public Integer getBrandProduct() {
        return brandProduct;
    }

    public void setBrandProduct(Integer brandProduct) {
        this.brandProduct = brandProduct;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
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

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getRelatedPromotions() {
        return relatedPromotions;
    }

    public void setRelatedPromotions(String relatedPromotions) {
        this.relatedPromotions = relatedPromotions;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
