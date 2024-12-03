package LapFarm.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import LapFarm.Entity.ImageEntity;
import jakarta.transaction.Transactional;

@Repository
public class ImageDAO {
	@Autowired
	private SessionFactory factory;
	
	@Transactional
	public List<ImageEntity> getImagesByProductId(int idProduct) {
	    Session session = factory.getCurrentSession();

	    // HQL query để lấy danh sách ImageEntity theo idProduct
	    String hql = "FROM ImageEntity i WHERE i.product.idProduct = :idProduct";
	    Query<ImageEntity> query = session.createQuery(hql, ImageEntity.class);
	    query.setParameter("idProduct", idProduct);

	    // Trả về danh sách ImageEntity
	    return query.list();
	}

}
