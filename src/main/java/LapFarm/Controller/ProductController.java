package LapFarm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


import LapFarm.DAO.ProductDAO;
import LapFarm.DAO.ReviewDAO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.ProductEntity;
import LapFarm.Entity.ReviewEntity;

@Controller
public class ProductController {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private ReviewDAO reviewDAO; // Thêm ReviewDAO
    @RequestMapping(value = "/product-detail/{idProduct}", method = RequestMethod.GET)
    public String productDetail(@PathVariable("idProduct") int productId,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
                                Model model) {
        // Lấy sản phẩm
        ProductEntity product = productDAO.getProductById(productId);

        if (product == null) {
            model.addAttribute("errorMessage", "Không tìm thấy sản phẩm.");
            return "error";
        }
        // Lấy danh sách sản phẩm liên quan theo brand
        int brandId = product.getBrand().getIdBrand();
        List<ProductDTO> relatedProducts = productDAO.getRelatedProductsByBrand(brandId, productId, 4);

        // Lấy dữ liệu đánh giá
        List<ReviewEntity> reviews = reviewDAO.getReviewsByProductIdWithPagination(productId, page, pageSize);

        // Lấy tổng số lượng đánh giá để tính tổng số trang
        int totalReviews = reviewDAO.countReviewsByProductId(productId);
        int totalPages = (int) Math.ceil((double) totalReviews / pageSize);
        Map<String, Object> ratingSummary = productDAO.getRatingSummary(productId);
        model.addAttribute("totalReviews", totalReviews);
        // Truyền dữ liệu vào model
        model.addAttribute("ratingSummary", ratingSummary);
        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);
        model.addAttribute("reviews", reviews);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);

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
