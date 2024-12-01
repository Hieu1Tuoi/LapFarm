package LapFarm.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.OrdersDTO;
@Service
public interface IOrdersService {
	 public List<OrdersDTO> getAllOrdersWithUserFullname();
	 public List<OrdersDTO> getOrdersByUserId(int idUser);
}
