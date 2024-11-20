package LapFarm.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import LapFarm.Entity.ProductEntity;

@Repository
public class ProductDAO {

    @Autowired
    private SessionFactory factory;

    // Hàm lấy danh sách tất cả sản phẩm
    public List<ProductEntity> getAllProducts() {
        // Lấy session từ factory
        Session session = factory.getCurrentSession();

        // Viết câu truy vấn HQL để lấy tất cả sản phẩm
        String hql = "FROM ProductEntity"; // Truy vấn tất cả sản phẩm từ bảng ProductEntity
        Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
        query.setMaxResults(9);
        // Trả về danh sách sản phẩm
        return query.list();
    }
}
