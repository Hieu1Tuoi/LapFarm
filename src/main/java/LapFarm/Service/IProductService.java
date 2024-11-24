package LapFarm.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.ProductDTO;

@Service
public interface IProductService {
	public Long getTotalProductQuantity();

	public List<ProductDTO> GetDataProductPaginates(int start, int end);

	public List<ProductDTO> getAllProductsDTO();

	public List<ProductDTO> findProductsByPriceRange(int minPrice, int maxPrice);

	public List<ProductDTO> findProductsByPriceGreaterThan(int minPrice);

}
