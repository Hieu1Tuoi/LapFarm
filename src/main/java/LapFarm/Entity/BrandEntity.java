package LapFarm.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "brand")
public class BrandEntity {
	@Id
	@Column(name = "idBrand", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idBrand;

	@Column(name = "Brand", nullable = false)
	private String nameBrand;

	@OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductEntity> products;

	@Transient
	private String encryptedId; // ID đã mã hóa không lưu trong DB

	// Getters và Setters mới
	public String getEncryptedId() {
		return encryptedId;
	}

	public void setEncryptedId(String encryptedId) {
		this.encryptedId = encryptedId;
	}

	public BrandEntity() {

	}

	public BrandEntity(int idBrand, String nameBrand) {
		super();
		this.idBrand = idBrand;
		this.nameBrand = nameBrand;
	}

	public int getIdBrand() {
		return idBrand;
	}

	public void setIdBrand(int idBrand) {
		this.idBrand = idBrand;
	}

	public String getNameBrand() {
		return nameBrand;
	}

	public void setNameBrand(String nameBrand) {
		this.nameBrand = nameBrand;
	}

}
