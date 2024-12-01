package LapFarm.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LapFarm.DAO.OrdersDAO;
import LapFarm.DTO.OrdersDTO;
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

}
