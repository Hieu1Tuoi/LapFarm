package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productdetail")
public class ProductDetailEntity {
	
	@EmbeddedId
	@OneToOne(fetch = FetchType.EAGER) // Fetch EAGER
    @JoinColumn(name = "IdProduct", referencedColumnName = "IdProduct", nullable = false)
    private ProductEntity product; // Liên kết với bảng Product (1:1)

    @ManyToOne(fetch = FetchType.EAGER) // Fetch EAGER
    @JoinColumn(name = "IdCategory", referencedColumnName = "IdCategory", nullable = false)
    private CategoryEntity category; // Liên kết với bảng Category

    @Column(name = "MoreInfo")
    private String moreInfo; // Thông tin chi tiết về sản phẩm

    // Getter và Setter
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    // Constructors
    public ProductDetailEntity() {
    }

    public ProductDetailEntity(ProductEntity product, CategoryEntity category, String moreInfo) {
        this.product = product;
        this.category = category;
        this.moreInfo = moreInfo;
    }
}
