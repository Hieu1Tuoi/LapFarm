package LapFarm.Service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import LapFarm.DTO.CartDTO;
import LapFarm.Entity.AccountEntity;

@Service
public interface ICartService {
	public HashMap<Integer, CartDTO> AddCart(int id, HashMap<Integer, CartDTO> cart);
	public HashMap<Integer, CartDTO> EditCart(int id, int quanty, HashMap<Integer, CartDTO> cart);
	public HashMap<Integer, CartDTO> DeleteCart(int id, HashMap<Integer, CartDTO> cart);
	public int TotalQuanty(HashMap<Integer, CartDTO> cart);
	public double TotalPrice(HashMap<Integer, CartDTO> cart);
	public void saveCartToDatabase(AccountEntity user, HashMap<Integer, CartDTO> cart);
}
