package LapFarm.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.ProductDTO;
@Service
public interface ICategoryService {
	public List<ProductDTO> getProductsByCategory(int idCategory);
	public List<ProductDTO> GetDataProductPaginates(int start, int end);
	
	
}
