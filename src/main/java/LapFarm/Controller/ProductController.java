package LapFarm.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import LapFarm.DAO.ProductDAO;
import LapFarm.DTO.ProductDTO;
import LapFarm.DTO.ViewedItem;
import LapFarm.Entity.ProductEntity;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	
	@RequestMapping(value = {"/product-detail", "/product-detail/*"}, method = RequestMethod.GET)
	public String product() {

		return "product";
	}
	@Autowired
    private ProductDAO productDAO;
	
    
    @RequestMapping(value = "/product-detail/{idProduct}", method = RequestMethod.GET)
    public String productDetail(@PathVariable("idProduct") int idProduct, Model model, HttpSession httpSession) {
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
        
        List<ViewedItem> viewedItems = (List<ViewedItem>) httpSession.getAttribute("viewedItems");
        if (viewedItems == null) {
            viewedItems = new ArrayList<>();
        }

        // Check if the product is already in the viewed items
        boolean alreadyViewed = viewedItems.stream().anyMatch(item -> item.getId() == idProduct);
        if (!alreadyViewed) {
            ViewedItem viewedItem = new ViewedItem();
            viewedItem.setId(product.getIdProduct()); // Assuming getIdProduct() returns the product ID
            viewedItem.setName(product.getNameProduct()); // Assuming getNameProduct() returns the product name
            
            // Extract the image URL from the first image entity (or adjust according to your logic)
            String imageUrl = product.getImages().isEmpty() ? "" : product.getImages().get(0).getImageUrl(); // Assuming getUrl() gives the image URL
            viewedItem.setImage(imageUrl); // Set the image URL (should be a String)
            
            viewedItem.setPrice(product.calPrice()); // Assuming getSalePrice() returns the product price
            viewedItems.add(viewedItem);
        }

        // Save the updated list back to the session
        httpSession.setAttribute("viewedItems", viewedItems);

		 

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

