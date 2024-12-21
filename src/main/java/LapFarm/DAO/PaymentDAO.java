package LapFarm.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import LapFarm.Entity.PaymentEntity;
import jakarta.transaction.Transactional;

@Repository
public class PaymentDAO {
	
	@Autowired
	private SessionFactory factory;
	
	@Transactional
	// Hàm lấy danh sách tất cả thanh toán
	public List<PaymentEntity> getAllPayments() {
		// Lấy session từ factory
		Session session = factory.getCurrentSession();

		// Viết câu truy vấn HQL để lấy tất cả thanh toán
		String hql = "FROM PaymentEntity"; // Truy vấn tất cả thanh toán
		Query<PaymentEntity> query = session.createQuery(hql, PaymentEntity.class);
		// Trả về danh sách thanh toán
		
		return query.list();
	}
}
