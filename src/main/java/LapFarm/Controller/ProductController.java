package LapFarm.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import LapFarm.DAO.OrderDetailDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DAO.ProductDetailDAO;
import LapFarm.DAO.ReviewDAO;
import LapFarm.DTO.ProductDTO;
import LapFarm.DTO.ViewedItem;
import LapFarm.Entity.ProductDetailEntity;
import LapFarm.Entity.ProductEntity;

import LapFarm.Entity.ReviewEntity;
import LapFarm.Entity.UserInfoEntity;
import LapFarm.Utils.SecureUrlUtil;
import LapFarm.Utils.XSSUtils;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController extends BaseController {
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private ProductDetailDAO productDetailDAO;
    
    @Autowired
    private OrderDetailDAO orderDetailDAO;


    @Autowired
    private ReviewDAO reviewDAO;

    @RequestMapping(value = "/product-detail/{idProduct}", method = RequestMethod.GET)
    public String productDetail(@PathVariable("idProduct") String encryptedId,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
                                Model model,
                                HttpSession httpSession) {
        Init();
        
        try {
            // Giải mã idProduct từ URL
            int productId;
            try {
                String decryptedId = SecureUrlUtil.decrypt(encryptedId);
                if (decryptedId == null || decryptedId.isEmpty() || !decryptedId.matches("\\d+")) {
                    throw new IllegalArgumentException("ID sản phẩm không hợp lệ.");
                }
                productId = Integer.parseInt(decryptedId);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "ID sản phẩm không hợp lệ hoặc bị thay đổi.");
                return "error/404";  // Trả về trang lỗi nếu ID không hợp lệ
            }

            // Lấy sản phẩm từ cơ sở dữ liệu
            ProductEntity product = productDAO.getProductById(productId);
            ProductDetailEntity productDetail = productDetailDAO.getProductDetailById(productId);
            if (product == null) {
                model.addAttribute("errorMessage", "Không tìm thấy sản phẩm.");
                return "error/404";  // Trả về trang lỗi nếu không tìm thấy sản phẩm
            }

            // Lấy số lượt bán của sản phẩm
            int salesCount = orderDetailDAO.countSalesByProductId(productId);

            // Lấy danh sách sản phẩm liên quan theo thương hiệu (dựa vào productId đã giải mã)
            int brandId = product.getBrand().getIdBrand();
            List<ProductDTO> relatedProducts = productDAO.getRelatedProductsByBrand(brandId, productId, 4); // Sử dụng brandId và productId


            // Thêm encryptedId vào từng sản phẩm liên quan
            for (ProductDTO relatedProduct : relatedProducts) {
                String encryptedProductId = SecureUrlUtil.encrypt(String.valueOf(relatedProduct.getIdProduct()));
                relatedProduct.setEncryptedId(encryptedProductId);
            }
            // Lấy dữ liệu đánh giá
            List<ReviewEntity> reviews = reviewDAO.getReviewsByProductIdWithPagination(productId, page, pageSize);

            for (ReviewEntity review : reviews) {
                // Kiểm tra nếu nhận xét chứa các ký tự đặc biệt
                if (XSSUtils.containsXSS(review.getComment())) {
                    review.setComment(XSSUtils.escapeXSS(review.getComment()));  // Mã hóa nhận xét nếu chứa ký tự XSS
                }
            }
            // Tính tổng số trang phân trang
            int totalReviews = reviewDAO.countReviewsByProductId(productId);
            int totalPages = (int) Math.ceil((double) totalReviews / pageSize);

            // Lấy tóm tắt đánh giá
            Map<String, Object> ratingSummary = productDAO.getRatingSummary(productId);

            // Lấy danh sách sản phẩm đã xem từ session
            List<ViewedItem> viewedItems = (List<ViewedItem>) httpSession.getAttribute("viewedItems");
            if (viewedItems == null) {
                viewedItems = new ArrayList<>();
            }

            // Kiểm tra xem sản phẩm đã được xem chưa
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

            // Truyền dữ liệu vào model để view sử dụng
            model.addAttribute("product", product);
            model.addAttribute("productDetail", productDetail);
            model.addAttribute("relatedProducts", relatedProducts); // Thêm sản phẩm liên quan vào model
            model.addAttribute("reviews", reviews);
            model.addAttribute("totalReviews", totalReviews);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("ratingSummary", ratingSummary);
            model.addAttribute("salesCount", salesCount);
            model.addAttribute("mahoaID", encryptedId);
            return "product"; // Trả về view "product"
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình xử lý.");
            return "error/404"; // Trả về trang lỗi nếu có lỗi trong quá trình xử lý
        }
    }



    @RequestMapping(value = "/product-all-reviews/{encryptedId}", method = RequestMethod.GET)
    public String allReviews(@PathVariable("encryptedId") String encryptedId, Model model) {
        try {
            // Giải mã encryptedId để lấy productId
            int productId;
            try {
                String decryptedId = SecureUrlUtil.decrypt(encryptedId);
                if (decryptedId == null || decryptedId.isEmpty() || !decryptedId.matches("\\d+")) {
                    throw new IllegalArgumentException("ID sản phẩm không hợp lệ.");
                }
                productId = Integer.parseInt(decryptedId);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "ID sản phẩm không hợp lệ hoặc bị thay đổi.");
                return "error/404";  // Trả về trang lỗi nếu ID không hợp lệ
            }

            // Lấy sản phẩm từ cơ sở dữ liệu
            ProductEntity product = productDAO.getProductById(productId);
            if (product == null) {
                model.addAttribute("errorMessage", "Không tìm thấy sản phẩm.");
                return "error/404";  // Trả về trang lỗi nếu không tìm thấy sản phẩm
            }
          
            // Lấy tất cả các đánh giá của sản phẩm
            List<ReviewEntity> reviews = reviewDAO.getAllReviewsByProductId(productId);
            Map<String, Object> ratingSummary = productDAO.getRatingSummary(productId);

            // Truyền dữ liệu vào model
            model.addAttribute("product", product);
            model.addAttribute("reviews", reviews);
            model.addAttribute("ratingSummary", ratingSummary);
            model.addAttribute("mahoaID", encryptedId);

            return "product-all-reviews";  // Trả về view "product-all-reviews"
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình xử lý.");
            return "error/404";  // Trả về trang lỗi nếu có lỗi trong quá trình xử lý
        }
    }

    
}
