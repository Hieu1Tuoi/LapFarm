package LapFarm.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LapFarm.DAO.BrandDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.BrandEntity;

@Service
public class BrandServiceImp implements IBrandService {

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private BrandDAO brandDAO;

	@Override
	public List<ProductDTO> GetDataProductPaginates(int start, int end, String searchText, int category,
			String priceRange) {
		return productDAO.getDataProductPaginates(start, end, searchText, category, priceRange);
	}

	@Override
	public List<ProductDTO> getProductsByBrand(int idBrand) {
		return productDAO.getProductsByBrand(idBrand);
	}

	@Override
	public BrandEntity getBrandById(int idBrand) {
		return brandDAO.getBrandById(idBrand);
	}

}
