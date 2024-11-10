package LapFarm.Entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryEntity {
	@Id
	@Column(name = "IdCategory", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCategory;
	
	@Column(name = "Category", nullable = false)
	private String nameCategory;

	public CategoryEntity() {
		
	}

	public CategoryEntity(int idCategory, String nameCategory) {
		super();
		this.idCategory = idCategory;
		this.nameCategory = nameCategory;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getNameCategory() {
		return nameCategory;
	}

	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}
	
	
	
}
