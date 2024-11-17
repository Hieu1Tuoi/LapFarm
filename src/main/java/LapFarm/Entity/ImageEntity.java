package LapFarm.Entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdImage")
    private int idImage;

    // Chế độ EAGER: tải đối tượng liên kết ngay khi tải ImageEntity
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductImage")
    private ProductEntity product;

    @Column(name = "Image", nullable = false, columnDefinition = "TEXT")
    private String image;

    // Constructor không tham số
    public ImageEntity() {
    }

    // Getter và Setter cho IdImage
    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    // Getter và Setter cho ProductEntity
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    // Getter và Setter cho Image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Optional: Override các phương thức toString, equals, hashCode nếu cần
    @Override
    public String toString() {
        return "ImageEntity{idImage=" + idImage + ", product=" + product + ", image='" + image + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntity that = (ImageEntity) o;
        return idImage == that.idImage;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idImage);
    }
}
