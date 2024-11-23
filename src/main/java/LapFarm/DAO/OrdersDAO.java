package LapFarm.DAO;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import LapFarm.DTO.OrdersDTO;
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
                        order.getUserInfo().getFullName()))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public List<OrdersEntity> getAllOrders() {
        Session session = factory.getCurrentSession();

        // Truy vấn lấy danh sách tất cả Orders mà không cần thông tin User
        String hql = "SELECT o FROM OrdersEntity o";
        List<OrdersEntity> ordersList = session.createQuery(hql, OrdersEntity.class).getResultList();

        // Trả về danh sách OrdersEntity
        return ordersList;
    }
}
