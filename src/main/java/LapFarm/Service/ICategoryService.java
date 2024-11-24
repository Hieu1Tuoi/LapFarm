package LapFarm.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.CategoryEntity;
@Service
public interface ICategoryService {
	public List<ProductDTO> getProductsByCategory(int idCategory);
	public CategoryEntity getCategoryById(int idCategory);
	List<ProductDTO> GetDataProductPaginates(int start, int end, String searchText, int category);
	
}
