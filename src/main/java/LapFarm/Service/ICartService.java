package LapFarm.Service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import LapFarm.DTO.CartDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.CartEntity;

@Service
public interface ICartService {
	public HashMap<Integer, CartDTO> AddCart(int id, HashMap<Integer, CartDTO> cart);
	public HashMap<Integer, CartDTO> EditCart(int id, int quanty, HashMap<Integer, CartDTO> cart);
	public HashMap<Integer, CartDTO> DeleteCart(int id, HashMap<Integer, CartDTO> cart);
	public int TotalQuanty(HashMap<Integer, CartDTO> cart);
	public double TotalPrice(HashMap<Integer, CartDTO> cart);
	public void saveCartToDatabase(AccountEntity user, HashMap<Integer, CartDTO> cart);
	
	public List<CartEntity> getCartByUserEmail(String email) ;
	public HashMap<Integer, CartDTO> getCartFromDatabase(int userId);
	 public void syncCartToDatabase(int userId, HashMap<Integer, CartDTO> cart) ;
	 public void deleteCartFromDatabase(int userId, int productId);
	 public void updateProductQuantityInDatabase(int userId, int productId, int quantity) ;
}
