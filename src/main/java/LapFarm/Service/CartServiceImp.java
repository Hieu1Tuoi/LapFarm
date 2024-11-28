package LapFarm.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LapFarm.DAO.CartDAO;
import LapFarm.DTO.CartDTO;
import LapFarm.DTO.UserInfoDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.CartEntity;

@Service
public class CartServiceImp implements ICartService {
	@Autowired
	private CartDAO cartDao;

	@Override
	public HashMap<Integer, CartDTO> AddCart(int id, HashMap<Integer, CartDTO> cart) {
		// TODO Auto-generated method stub
		return cartDao.addCart(id, cart);
	}

	@Override
	public HashMap<Integer, CartDTO> EditCart(int id, int quanty, HashMap<Integer, CartDTO> cart) {
		// TODO Auto-generated method stub
		return cartDao.EditCart(id, quanty, cart);
	}

	@Override
	public HashMap<Integer, CartDTO> DeleteCart(int id, HashMap<Integer, CartDTO> cart) {
		// TODO Auto-generated method stub
		return cartDao.DeleteCart(id, cart);
	}

	@Override
	public int TotalQuanty(HashMap<Integer, CartDTO> cart) {
		// TODO Auto-generated method stub
		return cartDao.TotalQuanty(cart);
	}

	@Override
	public double TotalPrice(HashMap<Integer, CartDTO> cart) {
		// TODO Auto-generated method stub
		return cartDao.TotalPrice(cart);
	}

	@Override
	public void saveCartToDatabase(AccountEntity user, HashMap<Integer, CartDTO> cart) {
		cartDao.saveCartToDatabase(user, cart);
	}
}
