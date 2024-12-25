package LapFarm.DAO;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.PaymentDTO;
import LapFarm.Entity.OrdersEntity;
import LapFarm.Entity.PaymentEntity;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class PaymentDAO {
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory factory;
	
    @Autowired
    @Qualifier("sessionFactoryUser")
    private SessionFactory factoryUser;
	
	@Transactional("transactionManager")
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
	
	@Transactional("transactionManager")
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
	
	 @Transactional("transactionManagerUser")
	    public List<PaymentEntity> getPaymentsByUserId(int userId) {
	        try {
	            Session session = factoryUser.getCurrentSession();
	            String hql = "FROM PaymentEntity p WHERE p.userPayment.id = :userId ORDER BY p.timePayment DESC";
	            return session.createQuery(hql, PaymentEntity.class)
	                         .setParameter("userId", userId)
	                         .getResultList();
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("Lỗi khi lấy lịch sử thanh toán: " + e.getMessage());
	        }
	    }
	 
	 @Transactional("transactionManagerUser")
		public List<PaymentEntity> searchPayments(String searchQuery) {
		    // Kiểm tra nếu người dùng tìm kiếm theo số
		    boolean isNumeric = searchQuery.matches("^[0-9]+$");

		    // Lấy phiên làm việc hiện tại
		    Session session = factoryUser.getCurrentSession();
		    String hql;

		    if (isNumeric) {
		        // Tìm kiếm theo ID (sử dụng LIKE để tìm theo chuỗi số)
		        hql = "SELECT p FROM PaymentEntity p WHERE CAST(p.idPayment AS string) LIKE :searchQuery OR CAST(p.orderPayment.idOrder AS string) LIKE :searchQuery ORDER BY p.timePayment DESC";
		    } else {
		        // Tìm kiếm theo fullName (sử dụng LIKE để tìm kiếm tên chứa chuỗi tìm kiếm)
		        hql = "SELECT p FROM PaymentEntity p WHERE p.userPayment.fullName LIKE :searchQuery";
		    }

		    // Tạo truy vấn
		    Query<PaymentEntity> query = session.createQuery(hql, PaymentEntity.class);

		    // Thiết lập tham số tìm kiếm, thêm dấu % nếu tìm kiếm theo fullName
		    query.setParameter("searchQuery", isNumeric ? "%" + Integer.parseInt(searchQuery) + "%" : "%" + searchQuery + "%");

		    // Lấy danh sách kết quả
		    List<PaymentEntity> payments = query.getResultList();

		    // Chuyển đổi từ OrdersEntity sang OrdersDTO
		    return payments;
		}
}
