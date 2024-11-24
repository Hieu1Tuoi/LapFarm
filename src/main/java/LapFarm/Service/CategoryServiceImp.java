package LapFarm.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.CategoryEntity;
@Service
public class CategoryServiceImp implements ICategoryService {

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Override
	public List<ProductDTO> GetDataProductPaginates(int start, int end, String searchText, int category, String priceRange) {
		return productDAO.getDataProductPaginates(start, end, searchText, category, priceRange);
	}

	@Override
	public List<ProductDTO> getProductsByCategory(int idCategory) {
		return productDAO.getProductsByCategory(idCategory);
	}

	@Override
	public CategoryEntity getCategoryById(int idCategory) {
		return categoryDAO.getCategoryById(idCategory);
	}

}
