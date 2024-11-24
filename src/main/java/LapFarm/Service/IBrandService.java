package LapFarm.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
@Service
public interface IBrandService {
	public List<ProductDTO> getProductsByBrand(int idBrand);
	public BrandEntity getBrandById(int idBrand);
	List<ProductDTO> GetDataProductPaginates(int start, int end, String searchText, int category);
}
