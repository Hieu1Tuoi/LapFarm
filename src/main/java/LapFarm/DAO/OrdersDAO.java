package LapFarm.DAO;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Transactional
    public void saveOrder(OrdersEntity order, List<OrderDetailsEntity> orderDetailsList) {
        Session session = factory.getCurrentSession();

        // Lưu đơn hàng
        session.save(order);

        // Lưu chi tiết đơn hàng
        for (OrderDetailsEntity orderDetail : orderDetailsList) {
            orderDetail.setOrder(order); // Liên kết chi tiết với đơn hàng
            session.save(orderDetail);
        }
    }
}