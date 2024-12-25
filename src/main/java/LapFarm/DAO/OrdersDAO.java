package LapFarm.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.OrderDetailsEntity;
import LapFarm.Entity.OrdersEntity;
import org.springframework.transaction.annotation.Transactional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import LapFarm.DTO.OrderDetailDTO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.OrderDetailsEntity;
import LapFarm.Entity.OrdersEntity;

@Repository
@Transactional("transactionManager")
public class OrdersDAO {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory factory;

	@Transactional("transactionManager")
	public List<OrdersDTO> getAllOrdersWithUserFullname() {
		Session session = factory.getCurrentSession();

		// Truy vấn lấy danh sách tất cả Orders cùng với thông tin User (sử dụng JOIN
		// FETCH để tối ưu)
		String hql = "SELECT o FROM OrdersEntity o JOIN FETCH o.userInfo";
		List<OrdersEntity> ordersList = session.createQuery(hql, OrdersEntity.class).getResultList();

		// Chuyển đổi từ OrdersEntity sang OrdersDTO
		return ordersList.stream()
				.map(order -> new OrdersDTO(order.getIdOrder(), order.getTime(), order.getState(),
						order.getTotalPrice(), order.getUserInfo().getFullName(), order.getPaymentMethod(),
						order.getNote(), order.getAddress()))
				.collect(Collectors.toList());
	}

	@Transactional("transactionManager")
	public List<OrdersDTO> getOrdersWithUserFullnameByUserId(int id) {
		// Lấy phiên làm việc hiện tại
		Session session = factory.getCurrentSession();

		// Truy vấn lấy danh sách Orders cùng với thông tin User
		String hql = "SELECT o FROM OrdersEntity o JOIN FETCH o.userInfo u WHERE u.userId = :id";
		Query<OrdersEntity> query = session.createQuery(hql, OrdersEntity.class);
		query.setParameter("id", id);

		// Lấy danh sách kết quả
		List<OrdersEntity> ordersList = query.getResultList();

		// Chuyển đổi từ OrdersEntity sang OrdersDTO
		return ordersList.stream()
				.map(order -> new OrdersDTO(order.getIdOrder(), order.getTime(), order.getState(),
						order.getTotalPrice(), order.getUserInfo().getFullName(), order.getPaymentMethod(),
						order.getNote(), order.getAddress()))
				.collect(Collectors.toList());
	}

	@Transactional("transactionManager")
	public OrdersEntity getOrderById(int orderId) {
		Session session = factory.getCurrentSession();
		OrdersEntity order = session.get(OrdersEntity.class, orderId);
		return order;
	}

//	@Transactional("transactionManager")
//	public void saveOrder(OrdersEntity order) {
//		Session session = factory.getCurrentSession();
//		session.merge(order); // Save or update the order if it already exists
//	}
	
	@Transactional("transactionManager")
	public int saveOrder(OrdersEntity order) {
		Session session = factory.getCurrentSession();
	    session.persist(order); // Lưu order
	    session.flush(); // Đẩy ngay lập tức để MySQL cấp phát ID
	    return order.getIdOrder(); // Lấy ID vừa được cấp phát
	}


	@Transactional("transactionManager")
	public void saveOrderDetail(OrderDetailsEntity orderDetails) {
		System.out.println(orderDetails);
		System.out.println(orderDetails.getOrder());
		Session session = factory.getCurrentSession();
		session.merge(orderDetails); // This will save or update the order detail entity
	}

	@Transactional("transactionManager")
	public Long countOrdersByUserId(int userId) {
		Session session = factory.getCurrentSession();
		try {
			String hql = "SELECT COUNT(o) FROM OrdersEntity o WHERE o.userInfo.userId = :userId";
			Query<Long> query = session.createQuery(hql, Long.class);
			query.setParameter("userId", userId);
			return query.uniqueResult(); // Trả về số lượng đơn hàng cho userId
		} catch (Exception e) {
			e.printStackTrace();
			return (long) -1; // Trả về 0 nếu có lỗi xảy ra
		}
	}

	@Transactional("transactionManager")
	public boolean updateStateById(int id, String state) {
		// Lấy session hiện tại từ factory
		Session session = factory.getCurrentSession();

		// Tìm đơn hàng theo id
		OrdersEntity orders = session.get(OrdersEntity.class, id);

		// Kiểm tra nếu đơn hàng tồn tại
		if (orders != null) {
			// Cập nhật trạng thái của đơn hàng
			orders.setState(state);

			// Lưu lại trạng thái mới của đơn hàng vào cơ sở dữ liệu
			session.merge(orders);// Sử dụng `update` thay vì `saveOrUpdate` vì chúng ta chỉ cập nhật trạng thái
			return true;
		}
		return false;
	}

	// Thanh Nhật thêm
	@Transactional("transactionManager")
	public List<OrdersDTO> getOrdersByUserId(int userId) {
		Session session = factory.getCurrentSession();

		// Truy vấn danh sách đơn hàng của người dùng đang đăng nhập theo userId
		String hql = "SELECT o FROM OrdersEntity o JOIN FETCH o.userInfo u WHERE u.userId = :userId";
		List<OrdersEntity> ordersList = session.createQuery(hql, OrdersEntity.class).setParameter("userId", userId)
				.getResultList();

		// Chuyển đổi từ OrdersEntity sang OrdersDTO
		return ordersList.stream()
				.map(order -> new OrdersDTO(order.getIdOrder(), order.getTime(), order.getState(),
						order.getTotalPrice(), order.getUserInfo().getFullName(), order.getPaymentMethod(),
						order.getNote(), order.getAddress()))
				.collect(Collectors.toList());
	}

	// Lấy chi tiết đơn hàng
	@Transactional("transactionManager")
	public List<OrderDetailDTO> getOrderDetail(int orderId) {
		Session session = factory.getCurrentSession();

		// Truy vấn lấy chi tiết đơn hàng theo orderId
		String hql = "SELECT od FROM OrderDetailsEntity od JOIN FETCH od.product WHERE od.order.idOrder = :orderId";
		List<OrderDetailsEntity> orderDetailsList = session.createQuery(hql, OrderDetailsEntity.class)
				.setParameter("orderId", orderId).getResultList();

		// Chuyển đổi từ OrderDetailsEntity sang OrderDetailDTO
		return orderDetailsList.stream().map(od -> new OrderDetailDTO(od.getOrder().getIdOrder(), // ID của đơn hàng
				new ProductDTO(od.getProduct().getIdProduct(), od.getProduct().getNameProduct(), // Tên sản phẩm
						od.getProduct().getBrand().getIdBrand(), od.getProduct().getBrand().getNameBrand(), // Tên
																											// thương
																											// hiệu
						od.getProduct().getCategory().getNameCategory(), // Tên danh mục
						od.getProduct().getCategory().getIdCategory(), od.getProduct().getDescription(),
						od.getProduct().getQuantity(), od.getProduct().getDiscount(),
						od.getProduct().getOriginalPrice(), od.getProduct().getSalePrice(), od.getProduct().getState(),
						od.getProduct().getImages().get(0).getImageUrl()), // Sản phẩm tương ứng
				od.getQuantity(), // Số lượng
				od.getPrice() // Giá sản phẩm
		)).collect(Collectors.toList());
	}

	@Transactional("transactionManager")
	public String getStateById(int orderId) {
		Session session = factory.getCurrentSession();

		// Truy vấn lấy đơn hàng theo orderId
		OrdersEntity order = session.get(OrdersEntity.class, orderId);

		// Kiểm tra nếu đơn hàng tồn tại, trả về trạng thái
		if (order != null) {
			return order.getState(); // Trả về trạng thái của đơn hàng
		}

		// Nếu không tìm thấy đơn hàng, trả về null hoặc bạn có thể ném một ngoại lệ tùy
		// theo yêu cầu
		return null;
	}

	@Transactional("transactionManager")
	public List<OrdersDTO> getOrdersByYear(int year) {
		Session session = factory.getCurrentSession();

		// Truy vấn lấy đơn hàng theo năm (dùng HQL để lọc theo năm)
		String hql = "SELECT o FROM OrdersEntity o WHERE YEAR(o.time) = :year";
		List<OrdersEntity> ordersList = session.createQuery(hql, OrdersEntity.class).setParameter("year", year)
				.getResultList();

		// Chuyển đổi từ OrdersEntity sang OrdersDTO
		return ordersList.stream()
				.map(order -> new OrdersDTO(order.getIdOrder(), order.getTime(), order.getState(),
						order.getTotalPrice(), order.getUserInfo().getFullName(), order.getPaymentMethod(),
						order.getNote(), order.getAddress()))
				.collect(Collectors.toList());
	}

	@Transactional("transactionManager")
	public List<OrdersDTO> getOrdersByMonthAndYear(int month, int year) {
		Session session = factory.getCurrentSession();

		// Truy vấn lấy đơn hàng theo tháng và năm (dùng HQL để lọc theo tháng và năm)
		String hql = "SELECT o FROM OrdersEntity o WHERE MONTH(o.time) = :month AND YEAR(o.time) = :year";
		List<OrdersEntity> ordersList = session.createQuery(hql, OrdersEntity.class).setParameter("month", month)
				.setParameter("year", year).getResultList();

		// Chuyển đổi từ OrdersEntity sang OrdersDTO
		return ordersList.stream()
				.map(order -> new OrdersDTO(order.getIdOrder(), order.getTime(), order.getState(),
						order.getTotalPrice(), order.getUserInfo().getFullName(), order.getPaymentMethod(),
						order.getNote(), order.getAddress()))
				.collect(Collectors.toList());
	}

	@Transactional("transactionManager")
	public int getTotalRevenueByMonthAndYear(int month, int year) {
		Session session = factory.getCurrentSession();

		String hql = "SELECT SUM(o.totalPrice) FROM OrdersEntity o WHERE MONTH(o.time) = :month AND YEAR(o.time) = :year";
		Query<Long> query = session.createQuery(hql, Long.class);
		query.setParameter("month", month);
		query.setParameter("year", year);

		Long totalRevenue = query.uniqueResult();
		return totalRevenue != null ? totalRevenue.intValue() : 0;
	}

	@Transactional("transactionManager")
	public int getCompletedRevenueByMonthAndYear(int month, int year) {
		Session session = factory.getCurrentSession();

		// Chỉ tính doanh thu của đơn hàng "Hoàn thành"
		String hql = "SELECT SUM(o.totalPrice) FROM OrdersEntity o "
				+ "WHERE MONTH(o.time) = :month AND YEAR(o.time) = :year AND o.state = :state";
		Query<Long> query = session.createQuery(hql, Long.class);
		query.setParameter("month", month);
		query.setParameter("year", year);
		query.setParameter("state", "Hoàn thành");

		Long totalRevenue = query.uniqueResult();
		return totalRevenue != null ? totalRevenue.intValue() : 0;
	}

	@Transactional("transactionManager")
	public Map<String, Map<Integer, Integer>> getProductCountByBrandForYears(List<Integer> years) {
		Session session = factory.getCurrentSession();

		// Truy vấn số lượng sản phẩm bán được theo mỗi hãng trong năm
		String hql = "SELECT p.brand.nameBrand, YEAR(od.order.time), SUM(od.quantity) " + "FROM OrderDetailsEntity od "
				+ "JOIN od.product p " + "WHERE YEAR(od.order.time) IN :years " + "AND od.order.state = 'Hoàn thành' "
				+ "GROUP BY p.brand.nameBrand, YEAR(od.order.time)";

		Query<Object[]> query = session.createQuery(hql, Object[].class);
		query.setParameterList("years", years);

		List<Object[]> results = query.list();
		Map<String, Map<Integer, Integer>> productCountByBrand = new HashMap<>();

		// Đưa kết quả vào Map để dễ dàng sử dụng trong frontend
		for (Object[] result : results) {
			String brandName = (String) result[0];
			Integer year = (Integer) result[1];
			Integer productCount = ((Long) result[2]).intValue();

			productCountByBrand.computeIfAbsent(brandName, k -> new HashMap<>()).put(year, productCount);
		}

		return productCountByBrand;
	}

	@Transactional("transactionManager")
	public Map<String, Map<Integer, Integer>> getProductCountByCategoryForYears(List<Integer> years) {
		Session session = factory.getCurrentSession();

		// Truy vấn số lượng sản phẩm bán được theo mỗi danh mục trong năm
		String hql = "SELECT p.category.nameCategory, YEAR(od.order.time), SUM(od.quantity) "
				+ "FROM OrderDetailsEntity od " + "JOIN od.product p " + "WHERE YEAR(od.order.time) IN :years "
				+ "AND od.order.state = 'Hoàn thành' " + "GROUP BY p.category.nameCategory, YEAR(od.order.time)";

		Query<Object[]> query = session.createQuery(hql, Object[].class);
		query.setParameterList("years", years);

		List<Object[]> results = query.list();
		Map<String, Map<Integer, Integer>> productCountByCategory = new HashMap<>();

		// Đưa kết quả vào Map để dễ dàng sử dụng trong frontend
		for (Object[] result : results) {
			String categoryName = (String) result[0];
			Integer year = (Integer) result[1];
			Integer productCount = ((Long) result[2]).intValue();

			productCountByCategory.computeIfAbsent(categoryName, k -> new HashMap<>()).put(year, productCount);
		}

		return productCountByCategory;
	}
	
	@Transactional("transactionManager")
	public List<OrdersDTO> searchOrders(String searchQuery) {
	    // Kiểm tra nếu người dùng tìm kiếm theo số
	    boolean isNumeric = searchQuery.matches("^[0-9]+$");

	    // Lấy phiên làm việc hiện tại
	    Session session = factory.getCurrentSession();
	    String hql;

	    if (isNumeric) {
	        // Tìm kiếm theo ID (sử dụng LIKE để tìm theo chuỗi số)
	        hql = "SELECT o FROM OrdersEntity o JOIN FETCH o.userInfo u WHERE CAST(o.idOrder AS string) LIKE :searchQuery";
	    } else {
	        // Tìm kiếm theo fullName (sử dụng LIKE để tìm kiếm tên chứa chuỗi tìm kiếm)
	        hql = "SELECT o FROM OrdersEntity o JOIN FETCH o.userInfo u WHERE u.fullName LIKE :searchQuery";
	    }

	    // Tạo truy vấn
	    Query<OrdersEntity> query = session.createQuery(hql, OrdersEntity.class);

	    // Thiết lập tham số tìm kiếm, thêm dấu % nếu tìm kiếm theo fullName
	    query.setParameter("searchQuery", isNumeric ? "%" + Integer.parseInt(searchQuery) + "%" : "%" + searchQuery + "%");

	    // Lấy danh sách kết quả
	    List<OrdersEntity> ordersList = query.getResultList();

	    // Chuyển đổi từ OrdersEntity sang OrdersDTO
	    return ordersList.stream()
	            .map(order -> new OrdersDTO(order.getIdOrder(), order.getTime(), order.getState(),
	                    order.getTotalPrice(), order.getUserInfo().getFullName(), order.getPaymentMethod(),
	                    order.getNote(), order.getAddress()))
	            .collect(Collectors.toList());
	}


}