package LapFarm.DAO;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import LapFarm.Entity.ProductDetailEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDetailDAO {
	
	@Autowired
	private SessionFactory factory;
	
	@Transactional
	public void addProductDetail(ProductDetailEntity productDetail) {
        // Lấy session từ factory
		Session session = factory.getCurrentSession();
        
        // Thêm ProductDetail vào cơ sở dữ liệu
        session.persist(productDetail);
    }
}
