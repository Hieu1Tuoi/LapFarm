package LapFarm.Service;

import LapFarm.Entity.ProductEntity;
import LapFarm.DAO.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    // Hàm đã có
    public ProductEntity findProductById(int productId) {
        return productDAO.findProductById(productId);
    }

    // Thêm hàm getProductById với kiểu Long cho ID
    public ProductEntity getProductById(int productId) {
        return productDAO.getProductById(productId);  // Trả về sản phẩm nếu tồn tại, nếu không trả về null
    }
}
