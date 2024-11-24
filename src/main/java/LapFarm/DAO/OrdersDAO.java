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

import LapFarm.DTO.OrdersDTO;
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


}