package LapFarm.Entity;


import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProduct")
    private int idProduct;

	@Column(name = "NameProduct", nullable = false)
	private String nameProduct;

	@ManyToOne(fetch = FetchType.EAGER) // Fetch EAGER
	@JoinColumn(name = "BrandProduct", referencedColumnName = "IdBrand", nullable = false)
	private BrandEntity brand;

	@ManyToOne(fetch = FetchType.EAGER) // Fetch EAGER
	@JoinColumn(name = "IdCategory", referencedColumnName = "IdCategory", nullable = false)
	private CategoryEntity category;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ImageEntity> images;

	// Getter và Setter
	public List<ImageEntity> getImages() {
		return images;
	}

	public void setImages(List<ImageEntity> images) {
		this.images = images;
	}

	@Column(name = "Description")
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

    // Getters and Setters
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

	// Constructors
	public ProductEntity() {
	}
	

	public ProductEntity(int idProduct, String nameProduct, List<ImageEntity> images, Double salePrice) {
		super();
		this.idProduct = idProduct;
		this.nameProduct = nameProduct;
		this.images = images;
		this.salePrice = salePrice;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public BrandEntity getBrand() {
		return brand;
	}

	public void setBrand(BrandEntity brand) {
		this.brand = brand;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
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

	public long calPrice() {
	    // Tính giá sau khi giảm, đảm bảo không có lỗi khi discount = 0
	    return (long) (this.salePrice - (this.discount != null ? this.discount * this.salePrice : 0));
	}

	public long calSalePrice() {
		return (long) (this.salePrice * 1);
	}

}
