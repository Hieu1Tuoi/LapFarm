package LapFarm.DAO;

import java.util.List;
import java.util.stream.Collectors;
import LapFarm.Entity.OrderDetailsEntity;
import LapFarm.Entity.OrdersEntity;
import jakarta.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import LapFarm.DTO.OrderDetailDTO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.OrderDetailsEntity;
import LapFarm.Entity.OrdersEntity;

@Repository
public class OrdersDAO {

    @Autowired
    private SessionFactory factory;

    @Transactional
    public List<OrdersDTO> getAllOrdersWithUserFullname() {
        Session session = factory.getCurrentSession();

        // Truy vấn lấy danh sách tất cả Orders cùng với thông tin User (sử dụng JOIN FETCH để tối ưu)
        String hql = "SELECT o FROM OrdersEntity o JOIN FETCH o.userInfo";
        List<OrdersEntity> ordersList = session.createQuery(hql, OrdersEntity.class).getResultList();

        // Chuyển đổi từ OrdersEntity sang OrdersDTO
        return ordersList.stream()
                .map(order -> new OrdersDTO(
                        order.getIdOrder(),
                        order.getTime(),
                        order.getState(),
                        order.getTotalPrice(),
                        order.getUserInfo().getFullName(),
                		order.getPaymentMethod(),
                		order.getNote()
                		))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public OrdersEntity getOrderById(int orderId) {
        Session session = factory.getCurrentSession();
        OrdersEntity order = session.get(OrdersEntity.class, orderId);
        return order;
    }

    @Transactional
    public void saveOrder(OrdersEntity order) {
        Session session = factory.getCurrentSession();
        session.saveOrUpdate(order);  // Save or update the order if it already exists
    }
    
    @Transactional
    public void saveOrderDetail(OrderDetailsEntity orderDetails) {
        Session session = factory.getCurrentSession();
        session.saveOrUpdate(orderDetails); // This will save or update the order detail entity
    }
    
    @Transactional
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
    
    @Transactional
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

    
    //Thanh Nhật thêm
    @Transactional
    public List<OrdersDTO> getOrdersByUserId(int userId) {
        Session session = factory.getCurrentSession();

        // Truy vấn danh sách đơn hàng của người dùng đang đăng nhập theo userId
        String hql = "SELECT o FROM OrdersEntity o JOIN FETCH o.userInfo u WHERE u.userId = :userId";
        List<OrdersEntity> ordersList = session.createQuery(hql, OrdersEntity.class)
                                               .setParameter("userId", userId)
                                               .getResultList();

        // Chuyển đổi từ OrdersEntity sang OrdersDTO
        return ordersList.stream()
                .map(order -> new OrdersDTO(
                        order.getIdOrder(),
                        order.getTime(),
                        order.getState(),
                        order.getTotalPrice(),
                        order.getUserInfo().getFullName(),
                        order.getPaymentMethod(),
                        order.getNote()
                ))
                .collect(Collectors.toList());
    }


    //Lấy chi tiết đơn hàng
    @Transactional
    public List<OrderDetailDTO> getOrderDetail(int orderId) {
        Session session = factory.getCurrentSession();

        // Truy vấn lấy chi tiết đơn hàng theo orderId
        String hql = "SELECT od FROM OrderDetailsEntity od JOIN FETCH od.product WHERE od.order.idOrder = :orderId";
        List<OrderDetailsEntity> orderDetailsList = session.createQuery(hql, OrderDetailsEntity.class)
                                                           .setParameter("orderId", orderId)
                                                           .getResultList();

        // Chuyển đổi từ OrderDetailsEntity sang OrderDetailDTO
        return orderDetailsList.stream()
                .map(od -> new OrderDetailDTO(
                        od.getOrder().getIdOrder(),  // ID của đơn hàng
                        new ProductDTO(
                                od.getProduct().getIdProduct(),
                                od.getProduct().getNameProduct(),  // Tên sản phẩm
                                od.getProduct().getBrand().getIdBrand(),
                                od.getProduct().getBrand().getNameBrand(),  // Tên thương hiệu
                                od.getProduct().getCategory().getNameCategory(),  // Tên danh mục
                                od.getProduct().getCategory().getIdCategory(),
                                od.getProduct().getDescription(),
                                od.getProduct().getQuantity(),
                                od.getProduct().getDiscount(),
                                od.getProduct().getOriginalPrice(),
                                od.getProduct().getSalePrice(),
                                od.getProduct().getState(),
                                od.getProduct().getImages().get(0).getImageUrl()
                        ),  // Sản phẩm tương ứng
                        od.getQuantity(),  // Số lượng
                        od.getPrice()      // Giá sản phẩm
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public String getStateById(int orderId) {
        Session session = factory.getCurrentSession();

        // Truy vấn lấy đơn hàng theo orderId
        OrdersEntity order = session.get(OrdersEntity.class, orderId);
        
        // Kiểm tra nếu đơn hàng tồn tại, trả về trạng thái
        if (order != null) {
            return order.getState(); // Trả về trạng thái của đơn hàng
        }
        
        // Nếu không tìm thấy đơn hàng, trả về null hoặc bạn có thể ném một ngoại lệ tùy theo yêu cầu
        return null;
    }


}