package LapFarm.DAO;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import LapFarm.Entity.ProductDetailEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDetailDAO {
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory factory;
	
    @Autowired
    @Qualifier("sessionFactoryVisitor")
    private SessionFactory factoryVisitor;
	
	@Transactional("transactionManager")
	public void addProductDetail(ProductDetailEntity productDetail) {
        // Lấy session từ factory
		Session session = factory.getCurrentSession();
        
        // Thêm ProductDetail vào cơ sở dữ liệu
        session.persist(productDetail);
    }
	
	@Transactional("transactionManagerVisitor")
	public ProductDetailEntity getProductDetailById(int idProduct) {
	    // Lấy session từ factory
	    Session session = factoryVisitor.getCurrentSession();
	    
	    // Viết câu lệnh HQL để lấy ProductDetail theo IdProduct
	    String hql = "FROM ProductDetailEntity pd " +
	                 "JOIN FETCH pd.product p " + 
	                 "WHERE p.idProduct = :idProduct";
	    
	    // Tạo query và set parameter
	    Query<ProductDetailEntity> query = session.createQuery(hql, ProductDetailEntity.class);
	    query.setParameter("idProduct", idProduct);
	    
	    // Thực thi query và trả về kết quả
	    return query.uniqueResult();
	}
	
	@Transactional("transactionManager")
	public void updateProductDetail(ProductDetailEntity productDetail) {
	    // Lấy session từ factory
	    Session session = factory.getCurrentSession();

	    // Cập nhật ProductDetail vào cơ sở dữ liệu
	    // Sử dụng phương thức merge() thay vì persist() để cập nhật một entity đã tồn tại
	    session.merge(productDetail);
	}


}
