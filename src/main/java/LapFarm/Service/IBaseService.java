package LapFarm.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ProductEntity;
@Service
public interface IBaseService {
	public List<CategoryEntity> getCategoryEntities();
	// Lấy danh sách Brand
	List<BrandEntity> getBrandEntities();

	List<ProductEntity> getAllProducts();
	
	// Lấy số lượng sản phẩm theo tất cả danh mục
    Map<Integer, Long> getProductCountByAllCategories(List<CategoryEntity> categories);
 
 // Lấy số lượng sản phẩm theo tất cả brand
    Map<Integer, Long> getProductCountByAllBrands(List<BrandEntity> brands);
    
    List<ProductDTO> getTop5ProductsByLowestQuantity();
	
}
