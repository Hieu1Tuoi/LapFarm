package LapFarm.Entity;

import java.text.DecimalFormat;

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

    @ManyToOne(fetch = FetchType.EAGER) // Fetch EAGER
    @JoinColumn(name = "BrandProduct", referencedColumnName = "IdBrand", nullable = false)
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.EAGER) // Fetch EAGER
    @JoinColumn(name = "IdCategory", referencedColumnName = "IdCategory", nullable = false)
    private CategoryEntity category;

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
    	return (long) (this.salePrice-(this.discount*this.salePrice));
    }
    public long calSalePrice() {
    	return (long) (this.salePrice*1);
    }
    

    // Định dạng giá tiền với dấu phân cách nghìn
    public String formatPrice(Long price) {
        DecimalFormat formatter = new DecimalFormat("#,###"); // Định dạng ###,###
        return formatter.format(price) + "₫";
    }

    // Phương thức hiển thị giá đã định dạng
    public String getFormattedCalPrice() {
        Long price = calPrice();
        return formatPrice(price);
    }

    public String getFormattedSalePrice() {
    	long price=calSalePrice();
        return formatPrice(price);
    }

    
}
