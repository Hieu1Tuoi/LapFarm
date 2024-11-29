package LapFarm.Service;

import java.util.HashMap;
import java.util.List;
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



	@Override
	public List<CartEntity> getCartByUserEmail(String email) {
		// TODO Auto-generated method stub
		return cartDao.getCartByUserEmail(email);
	}

	@Override
	public HashMap<Integer, CartDTO> getCartFromDatabase(int userId) {
		// TODO Auto-generated method stub
		return cartDao.getCartFromDatabase(userId);
	}

	@Override
	public void syncCartToDatabase(int userId, HashMap<Integer, CartDTO> cart) {
		// TODO Auto-generated method stub
		cartDao.syncCartToDatabase(userId, cart);
	}

	@Override
	public void deleteCartFromDatabase(int userId, int productId) {
		// TODO Auto-generated method stub
		cartDao.deleteCartFromDatabase(userId, productId);
	}

	@Override
	public void updateProductQuantityInDatabase(int userId, int productId, int quantity) {
		// TODO Auto-generated method stub
		cartDao.updateProductQuantityInDatabase(userId, productId, quantity);
	}

	@Override
	public HashMap<Integer, CartDTO> AddCart(int id, HashMap<Integer, CartDTO> cart, int userId) {
		// TODO Auto-generated method stub
		return cartDao.AddCart(id, cart, userId);
	}

	
}
