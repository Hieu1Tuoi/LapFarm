package LapFarm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.ProductEntity;

@Controller
public class ProductController {
	
	@RequestMapping(value = {"/product-detail", "/product-detail/*"}, method = RequestMethod.GET)
	public String product() {

		return "product";
	}
	@Autowired
    private ProductDAO productDAO;
	
    
    @RequestMapping(value = "/product-detail/{idProduct}", method = RequestMethod.GET)
    public String productDetail(@PathVariable("idProduct") int idProduct, Model model) {
        ProductEntity product = productDAO.getProductById(idProduct);

        if (product == null) {
            model.addAttribute("errorMessage", "Không tìm thấy sản phẩm.");
            return "error";
        }

        // Lấy danh sách sản phẩm liên quan theo brand
        int brandId = product.getBrand().getIdBrand();
        List<ProductDTO> relatedProducts = productDAO.getRelatedProductsByBrand(brandId, idProduct, 4);

        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);

        return "product";
    }
    @RequestMapping(value = "/related-products/{idBrand}", method = RequestMethod.GET)
    public String viewAllRelatedProducts(@PathVariable("idBrand") int idBrand, Model model) {
        // Lấy danh sách tất cả các sản phẩm thuộc thương hiệu
        List<ProductDTO> relatedProducts = productDAO.getAllProductsByBrand(idBrand);

        // Kiểm tra nếu không có sản phẩm
        if (relatedProducts.isEmpty()) {
            model.addAttribute("errorMessage", "Không có sản phẩm nào liên quan.");
            return "error";
        }

        // Truyền danh sách sản phẩm liên quan vào model
        model.addAttribute("relatedProducts", relatedProducts);

        return "related-products"; // Trả về JSP hiển thị danh sách sản phẩm liên quan
    }

}

