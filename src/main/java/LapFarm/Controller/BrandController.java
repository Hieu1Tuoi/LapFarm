package LapFarm.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.DAO.BrandDAO;
import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.PaginatesDto;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Service.BrandServiceImp;
import LapFarm.Service.CategoryServiceImp;
import LapFarm.Service.PaginatesServiceImp;
import LapFarm.Service.ProductServiceImp;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class BrandController extends BaseController {

	@Autowired
	private BrandServiceImp brandService;
	@Autowired
	ProductServiceImp productService;
	@Autowired
	private PaginatesServiceImp paginateService;
	private int totalProductPage = 9;


	@RequestMapping(value = "/products-brand", params = "!page")
	public ModelAndView Index(@RequestParam(value = "idBrand", required = false) Integer idBrand,
			@RequestParam(value = "nameBrand", required = false) String nameBrand) {
		Init();

		if (idBrand != null) {
			_mvShare.addObject("brand", brandService.getBrandById(idBrand));
			_mvShare.addObject("AllProductByID", brandService.getProductsByBrand(idBrand));
			int totalData = brandService.getProductsByBrand(idBrand).size();
			PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
			_mvShare.addObject("paginateInfo", paginateInfo);
			_mvShare.addObject("ProductsPaginate", brandService.GetDataProductPaginates(paginateInfo.getStart(),
					paginateInfo.getEnd(), "", 0, "", idBrand));
		} else if (nameBrand != null) {
			_mvShare.addObject("brand", brandService.getBrandByName(nameBrand));
			_mvShare.addObject("AllProductByName", brandService.getProductsByBrandName(nameBrand));
			int totalData = brandService.getProductsByBrandName(nameBrand).size();
			PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, 1);
			_mvShare.addObject("paginateInfo", paginateInfo);
			_mvShare.addObject("ProductsPaginate", brandService.GetDataProductPaginates(paginateInfo.getStart(),
					paginateInfo.getEnd(), "", 0, "", idBrand));
		}

		Map<String, Double> price = productService.getMinMaxPrices();
		_mvShare.addObject("idBrand", idBrand);
		_mvShare.addObject("priceMin", price.get("min"));
		_mvShare.addObject("priceMax", price.get("max"));
		_mvShare.setViewName("productsByBrand");

		return _mvShare;
	}

	@RequestMapping(value = "/products-brand", params = "page")
	public ModelAndView Index(@RequestParam(value = "idBrand", required = false) Integer idBrand,
			@RequestParam(value = "nameBrand", required = false) String nameBrand,
			@RequestParam(value = "page", defaultValue = "1") int currentPage) {
		Init();

		if (idBrand != null) {
			_mvShare.addObject("brand", brandService.getBrandById(idBrand));
			_mvShare.addObject("AllProductByID", brandService.getProductsByBrand(idBrand));
			int totalData = brandService.getProductsByBrand(idBrand).size();
			PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
			_mvShare.addObject("paginateInfo", paginateInfo);
			_mvShare.addObject("ProductsPaginate", brandService.GetDataProductPaginates(paginateInfo.getStart(),
					paginateInfo.getEnd(), "", 0, "", idBrand));
		} else if (nameBrand != null) {
			_mvShare.addObject("brand", brandService.getBrandByName(nameBrand));
			_mvShare.addObject("AllProductByName", brandService.getProductsByBrandName(nameBrand));
			int totalData = brandService.getProductsByBrandName(nameBrand).size();
			PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
			_mvShare.addObject("paginateInfo", paginateInfo);
			_mvShare.addObject("ProductsPaginate", brandService.GetDataProductPaginates(paginateInfo.getStart(),
					paginateInfo.getEnd(), "", 0, "", idBrand));
		}

		Map<String, Double> price = productService.getMinMaxPrices();
		_mvShare.addObject("idBrand", idBrand);
		_mvShare.addObject("priceMin", price.get("min"));
		_mvShare.addObject("priceMax", price.get("max"));
		_mvShare.setViewName("productsByBrand");

		return _mvShare;
	}


}
