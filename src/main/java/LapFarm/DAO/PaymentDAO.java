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
	
	@Transactional
	// Hàm lấy danh sách tất cả thanh toán
	public void addPayment(PaymentEntity payment) {
		// Lấy session từ factory
		Session session = factory.getCurrentSession();

		 try {
	            // Lưu vào cơ sở dữ liệu
	            session.persist(payment);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("Lỗi khi lưu thông tin thanh toán vào cơ sở dữ liệu: " + e.getMessage());
	        }
	}
	
	 @Transactional
	    public List<PaymentEntity> getPaymentsByUserId(int userId) {
	        try {
	            Session session = factory.getCurrentSession();
	            String hql = "FROM PaymentEntity p WHERE p.userPayment.id = :userId ORDER BY p.timePayment DESC";
	            return session.createQuery(hql, PaymentEntity.class)
	                         .setParameter("userId", userId)
	                         .getResultList();
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("Lỗi khi lấy lịch sử thanh toán: " + e.getMessage());
	        }
	    }
}
