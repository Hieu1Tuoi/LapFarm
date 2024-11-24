package LapFarm.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.ProductDTO;

@Service
public interface IProductService {
	public Long getTotalProductQuantity();


	public List<ProductDTO> getAllProductsDTO();


	List<ProductDTO> GetDataProductPaginates(int start, int end, String searchText, int category);

}
