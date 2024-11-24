package LapFarm.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.ProductDTO;
@Service
public class ProductServiceImp implements IProductService {

	@Autowired
	private ProductDAO productDAO;

	@Override
	public Long getTotalProductQuantity() {
		return productDAO.getTotalProductQuantity();
	}

	@Override
	public List<ProductDTO> GetDataProductPaginates(int start, int end, String searchText, int category) {
		return productDAO.getDataProductPaginates(start, end, searchText, category);
	}

	@Override
	public List<ProductDTO> getAllProductsDTO() {
		return productDAO.getAllProductsDTO();
	}

	@Override
	public List<ProductDTO> findProductsByPriceRange(int minPrice, int maxPrice) {
		return  productDAO.findByPriceBetween(minPrice, maxPrice);
	}

	@Override
	public List<ProductDTO> findProductsByPriceGreaterThan(int minPrice) {
		return productDAO.findByPriceGreaterThan(minPrice);
	}
	
}
