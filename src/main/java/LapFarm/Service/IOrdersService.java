package LapFarm.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.OrderDetailDTO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.Entity.OrderDetailsEntity;
import LapFarm.Entity.OrdersEntity;
@Service
public interface IOrdersService {
	 public List<OrdersDTO> getAllOrdersWithUserFullname();
	 public List<OrdersDTO> getOrdersByUserId(int idUser);
	 public List<OrderDetailDTO> getOrderDetail(int orderId);
	 public OrdersEntity getOrderById(int orderId);
	 public String getStateById(int orderId);
}
