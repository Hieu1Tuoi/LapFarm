package LapFarm.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import LapFarm.DAO.ProductDAO;
import LapFarm.Entity.ProductEntity;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    // Lấy danh sách tất cả sản phẩm
    public List<ProductEntity> getAllProducts() {
        return productDAO.getAllProducts();
    }

    // Lấy sản phẩm theo ID
    public ProductEntity getProductById(Integer id) {
        return productDAO.getProductById(id);
    }

    // Các phương thức khác nếu cần, ví dụ: lấy sản phẩm theo danh mục, thương hiệu, top sản phẩm
}
