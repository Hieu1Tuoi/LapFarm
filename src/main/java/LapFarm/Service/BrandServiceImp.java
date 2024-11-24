package LapFarm.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.ProductDTO;
@Service
public class BrandServiceImp implements IBrandService {

	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public List<ProductDTO> GetDataProductPaginates(int start, int end) {
		return productDAO.getDataProductPaginates(start, end);
	}

	@Override
	public List<ProductDTO> getProductsByBrand(int idBrand) {
		return productDAO.getProductsByBrand(idBrand);
	}

}
