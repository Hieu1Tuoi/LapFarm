package LapFarm.Service;

import java.util.List;
import java.util.Map;

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
	public List<ProductDTO> GetDataProductPaginates(int start, int end, String searchText, int category, String priceRange) {
		return productDAO.getDataProductPaginates(start, end, searchText, category, priceRange);
	}

	@Override
	public List<ProductDTO> getAllProductsDTO() {
		return productDAO.getAllProductsDTO();
	}

	@Override
	public Map<String, Double> getMinMaxPrices() {
		return productDAO.getMinMaxPrices();
	}
}
