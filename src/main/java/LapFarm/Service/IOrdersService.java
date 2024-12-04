package LapFarm.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.OrderDetailDTO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.Entity.OrderDetailsEntity;
@Service
public interface IOrdersService {
	 public List<OrdersDTO> getAllOrdersWithUserFullname();
	 public List<OrdersDTO> getOrdersByUserId(int idUser);
	 public List<OrderDetailDTO> getOrderDetail(int orderId);
	 public String getStateById(int orderId);
}
