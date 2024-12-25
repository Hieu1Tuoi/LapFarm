package LapFarm.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import LapFarm.Entity.ImageEntity;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class ImageDAO {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory factory;
	
    @Autowired
    @Qualifier("sessionFactoryVisitor")
    private SessionFactory factoryVisitor;
	
	@Transactional("transactionManagerVisitor")
	public List<ImageEntity> getImagesByProductId(int idProduct) {
	    Session session = factoryVisitor.getCurrentSession();

	    // HQL query để lấy danh sách ImageEntity theo idProduct
	    String hql = "FROM ImageEntity i WHERE i.product.idProduct = :idProduct";
	    Query<ImageEntity> query = session.createQuery(hql, ImageEntity.class);
	    query.setParameter("idProduct", idProduct);

	    // Trả về danh sách ImageEntity
	    return query.list();
	}
	
	@Transactional("transactionManager")
	public void deleteImageById(int id) {
	    Session session = factory.getCurrentSession();

	    // HQL query để xóa ImageEntity theo id
	    String hql = "DELETE FROM ImageEntity i WHERE i.idImage = :id";
	    @SuppressWarnings("deprecation")
		Query<?> query = session.createQuery(hql); // Không cần chỉ định kiểu dữ liệu cho DELETE
	    query.setParameter("id", id);

	    // Thực thi câu lệnh xóa
	    query.executeUpdate();
	}



}
