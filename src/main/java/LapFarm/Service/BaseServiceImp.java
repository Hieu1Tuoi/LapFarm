package LapFarm.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LapFarm.Bean.Mailer;
import LapFarm.DAO.BrandDAO;
import LapFarm.DAO.CartDAO;
import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CartEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ProductEntity;
import jakarta.servlet.ServletContext;
@Service
public class BaseServiceImp implements IBaseService {



	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private BrandDAO brandDAO;

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CartDAO cartDAO;

	@Override
	public List<CategoryEntity> getCategoryEntities() {	
		return categoryDAO.getAllCategories();
	}

	@Override
	public List<BrandEntity> getBrandEntities() {
		return brandDAO.getAllBrands();
	}

	@Override
	public List<ProductEntity> getAllProducts() {
		return productDAO.getAllProducts();
	}



	@Override
	public Map<Integer, Long> getProductCountByAllCategories(List<CategoryEntity> categories) {
		return  productDAO.getProductCountByAllCategories(categories);
	}

	@Override
	public Map<Integer, Long> getProductCountByAllBrands(List<BrandEntity> brands) {
		return productDAO.getProductCountByAllBrands(brands);
	}

	@Override
	public List<ProductDTO> getTop5ProductsByLowestQuantity() {
		return productDAO.getTop5ProductsByLowestQuantity();
	}
	



}
