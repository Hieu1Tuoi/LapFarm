package LapFarm.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.ProductDTO;
@Service
public interface IBrandService {
	public List<ProductDTO> getProductsByBrand(int idBrand);
	public List<ProductDTO> GetDataProductPaginates(int start, int end);
}
