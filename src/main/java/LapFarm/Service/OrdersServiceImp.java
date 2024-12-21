package LapFarm.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LapFarm.DAO.OrdersDAO;
import LapFarm.DTO.OrderDetailDTO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.Entity.OrderDetailsEntity;
import LapFarm.Entity.OrdersEntity;
@Service
public class OrdersServiceImp implements IOrdersService {

	@Autowired
	private OrdersDAO orderDao;
	
	@Override
	public List<OrdersDTO> getAllOrdersWithUserFullname() {
		// TODO Auto-generated method stub
		return orderDao.getAllOrdersWithUserFullname();
	}

	@Override
	public List<OrdersDTO> getOrdersByUserId(int idUser) {
		// TODO Auto-generated method stub
		return orderDao.getOrdersByUserId(idUser);
	}

	@Override
	public List<OrderDetailDTO> getOrderDetail(int orderId) {
		// TODO Auto-generated method stub
		return orderDao.getOrderDetail(orderId);
	}
	
	@Override
	public OrdersEntity getOrderById(int orderId) {
		// TODO Auto-generated method stub
		return orderDao.getOrderById(orderId);
	}

	@Override
	public String getStateById(int orderId) {
		// TODO Auto-generated method stub
		return orderDao.getStateById(orderId);
	}

}
