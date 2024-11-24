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
	public List<ProductDTO> GetDataProductPaginates(int start, int end) {
		return productDAO.getDataProductPaginates(start, end);
	}

	@Override
	public List<ProductDTO> getAllProductsDTO() {
		return productDAO.getAllProductsDTO();
	}
	
}
