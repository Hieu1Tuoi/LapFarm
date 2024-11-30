package LapFarm.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import LapFarm.DAO.ProductDAO;
import LapFarm.DAO.ReviewDAO;
import LapFarm.DTO.ProductDTO;
import LapFarm.DTO.ViewedItem;
import LapFarm.Entity.ProductEntity;

import LapFarm.Entity.ReviewEntity;

import jakarta.servlet.http.HttpSession;


@Controller
public class ProductController {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private ReviewDAO reviewDAO;

    @RequestMapping(value = "/product-detail/{idProduct}", method = RequestMethod.GET)
    public String productDetail(@PathVariable("idProduct") int productId,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
                                Model model,
                                HttpSession httpSession) {
        try {
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

            // Tính tổng số trang
            int totalReviews = reviewDAO.countReviewsByProductId(productId);
            int totalPages = (int) Math.ceil((double) totalReviews / pageSize);

            // Lấy tóm tắt đánh giá
            Map<String, Object> ratingSummary = productDAO.getRatingSummary(productId);

            // Lấy danh sách sản phẩm đã xem
            List<ViewedItem> viewedItems = (List<ViewedItem>) httpSession.getAttribute("viewedItems");
            if (viewedItems == null) {
                viewedItems = new ArrayList<>();
            }

            // Kiểm tra nếu sản phẩm đã được xem
            boolean alreadyViewed = viewedItems.stream().anyMatch(item -> item.getId() == productId);
            if (!alreadyViewed) {
                ViewedItem viewedItem = new ViewedItem();
                viewedItem.setId(product.getIdProduct());
                viewedItem.setName(product.getNameProduct());
                viewedItem.setImage((product.getImages() != null && !product.getImages().isEmpty())
                    ? product.getImages().get(0).getImageUrl()
                    : "");
                viewedItem.setPrice(product.calPrice());
                viewedItems.add(viewedItem);
            }
            httpSession.setAttribute("viewedItems", viewedItems);

            // Truyền dữ liệu vào model
            model.addAttribute("product", product);
            model.addAttribute("relatedProducts", relatedProducts);
            model.addAttribute("reviews", reviews);
            model.addAttribute("totalReviews", totalReviews);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("ratingSummary", ratingSummary);

            return "product";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình xử lý.");
            return "error";
        }
    }

    @RequestMapping(value = "/related-products/{idBrand}", method = RequestMethod.GET)
    public String viewAllRelatedProducts(@PathVariable("idBrand") int idBrand, Model model) {
        List<ProductDTO> relatedProducts = productDAO.getAllProductsByBrand(idBrand);

        if (relatedProducts.isEmpty()) {
            model.addAttribute("errorMessage", "Không có sản phẩm nào liên quan.");
            return "error";
        }

        model.addAttribute("relatedProducts", relatedProducts);
        return "related-products";
    }
}
