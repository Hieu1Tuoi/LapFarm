package LapFarm.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "brand")
public class BrandEntity {
	@Id
	@Column(name = "idBrand", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idBrand;
	
	@Column(name = "Brand", nullable = false)
	private String nameBrand;

	
	
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
