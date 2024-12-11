package LapFarm.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.DTO.PaginatesDto;
import LapFarm.DTO.ProductDTO;
import LapFarm.Service.BrandServiceImp;
import LapFarm.Service.PaginatesServiceImp;
import LapFarm.Service.ProductServiceImp;
import LapFarm.Utils.SecureUrlUtil;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class BrandController extends BaseController {

    @Autowired
    private BrandServiceImp brandService;
    @Autowired
    private ProductServiceImp productService;
    @Autowired
    private PaginatesServiceImp paginateService;

    private int totalProductPage = 9;

    @RequestMapping(value = "/products-brand", params = {"!page"})
    public ModelAndView indexWithoutPage(
            @RequestParam(value = "idBrand", required = false) Integer idBrand,
            @RequestParam(value = "nameBrand", required = false) String nameBrand) {
        return handleBrandRequest(idBrand, nameBrand, 1);
    }

    @RequestMapping(value = "/products-brand", params = {"page"})
    public ModelAndView indexWithPage(
            @RequestParam(value = "idBrand", required = false) Integer idBrand,
            @RequestParam(value = "nameBrand", required = false) String nameBrand,
            @RequestParam(value = "page", defaultValue = "1") int currentPage) {
        return handleBrandRequest(idBrand, nameBrand, currentPage);
    }

    private ModelAndView handleBrandRequest(Integer idBrand, String nameBrand, int currentPage) {
        Init();

        if (idBrand != null) {
            setupBrandView(idBrand, null, currentPage);
        } else if (nameBrand != null) {
            setupBrandView(null, nameBrand, currentPage);
        }

        // Truyền giá trị min và max cho phạm vi giá
        Map<String, Double> price = productService.getMinMaxPrices();
        _mvShare.addObject("priceMin", price.get("min"));
        _mvShare.addObject("priceMax", price.get("max"));
        _mvShare.setViewName("productsByBrand");

        return _mvShare;
    }

    private void setupBrandView(Integer idBrand, String nameBrand, int currentPage) {
        if (idBrand != null) {
            _mvShare.addObject("brand", brandService.getBrandById(idBrand));
            _mvShare.addObject("AllProductByID", brandService.getProductsByBrand(idBrand));
            setupPaginatedProducts(idBrand, null, currentPage);
        } else if (nameBrand != null) {
            _mvShare.addObject("brand", brandService.getBrandByName(nameBrand));
            _mvShare.addObject("AllProductByName", brandService.getProductsByBrandName(nameBrand));
            setupPaginatedProducts(null, nameBrand, currentPage);
        }
    }

    private void setupPaginatedProducts(Integer idBrand, String nameBrand, int currentPage) {
        int totalData = (idBrand != null)
                ? brandService.getProductsByBrand(idBrand).size()
                : brandService.getProductsByBrandName(nameBrand).size();

        PaginatesDto paginateInfo = paginateService.GetInfoPaginate(totalData, totalProductPage, currentPage);
        _mvShare.addObject("paginateInfo", paginateInfo);

        List<ProductDTO> allProducts = brandService.GetDataProductPaginates(
                paginateInfo.getStart(), paginateInfo.getEnd(), "", 0, nameBrand, idBrand);

        // Mã hóa ID sản phẩm trong danh sách
        allProducts.forEach(product -> {
            try {
                product.setEncryptedId(SecureUrlUtil.encrypt(String.valueOf(product.getIdProduct())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Lấy rating summary cho các sản phẩm
        List<Integer> productIds = allProducts.stream().map(ProductDTO::getIdProduct).collect(Collectors.toList());
        List<Map<String, Object>> ratingSummaries = productService.getAllRatingSummaries(productIds);

        // Gán rating summary vào sản phẩm
        for (ProductDTO product : allProducts) {
            for (Map<String, Object> summary : ratingSummaries) {
                if (product.getIdProduct() == (int) summary.get("productId")) {
                    product.setRatingSummary(summary);
                }
            }
        }

        _mvShare.addObject("ProductsPaginate", allProducts);
    }

}
