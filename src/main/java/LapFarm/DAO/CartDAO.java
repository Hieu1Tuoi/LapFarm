package LapFarm.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import LapFarm.DTO.CartDTO;
import LapFarm.DTO.CartProductDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.CartEntity;
import LapFarm.Entity.CartId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartDAO {

	@Autowired
	private SessionFactory factory;
	
	@Autowired
	ProductDAO productDAO = new ProductDAO();

	@Transactional
	public List<CartEntity> getCartByUserEmail(String email) {
		Session session = factory.getCurrentSession();
		String hql = "FROM CartEntity ce WHERE ce.userInfo.account.email = :email";
		Query<CartEntity> query = session.createQuery(hql, CartEntity.class);
		query.setParameter("email", email);
		return query.getResultList();
	}

	@Transactional
	public void clearCart(int userId) {
		Session session = factory.getCurrentSession();
		String hql = "DELETE FROM CartEntity c WHERE c.id.userId = :userId";
		session.createQuery(hql).setParameter("userId", userId).executeUpdate();
	}

	// Them gio hang
	@Transactional
	public HashMap<Integer, CartDTO> addCart(int id, HashMap<Integer, CartDTO> cart) {
		CartDTO itemCart = new CartDTO();
		ProductDTO product = productDAO.findProductDTOById(id);
		
		if (product != null && cart.containsKey(id)) {
			itemCart = cart.get(id);
			itemCart.setQuantity(itemCart.getQuantity()+1);
			itemCart.setTotalPrice(itemCart.getQuantity()*itemCart.getProduct().calPrice());
		}else {
			itemCart.setProduct(product);
			itemCart.setQuantity(1);
			itemCart.setTotalPrice(product.calPrice());
		}
		cart.put(id, itemCart);
		return cart;
	}

	// Chỉnh sửa giỏ hàng
	@Transactional
	public HashMap<Integer, CartDTO> EditCart(int id, int quanty, HashMap<Integer, CartDTO> cart) {
		if (cart == null) {
			return cart;
		}
		CartDTO itemCart = new CartDTO();
		if (cart.containsKey(id)) {
			itemCart = cart.get(id);
			itemCart.setQuantity(quanty);
			double totalPrice = itemCart.getProduct().calPrice() * quanty;
			itemCart.setTotalPrice(totalPrice);
		}
		cart.put(id, itemCart);
		return cart;
	}
	//Xóa khỏi giỏ hàng
	@Transactional
	public HashMap<Integer, CartDTO> DeleteCart(int id, HashMap<Integer, CartDTO> cart) {
		if (cart == null) {
			return cart;
		}
		if (cart.containsKey(id)) {
			cart.remove(id);
		}
		return cart;
	}
	
	//Tính tổng số lượng trong giỏ
	@Transactional
	public int TotalQuanty(HashMap<Integer, CartDTO> cart) {
		int totalQuanty=0;
		for(Map.Entry<Integer, CartDTO> itemCart:cart.entrySet()) {
			totalQuanty+=itemCart.getValue().getQuantity();
		}
		return totalQuanty;
	}
	
	//Tính tổng giá tiền trong giỏ
	@Transactional
	public double TotalPrice(HashMap<Integer, CartDTO> cart) {
		int totalPrice=0;
		for(Map.Entry<Integer, CartDTO> itemCart:cart.entrySet()) {
			totalPrice+=itemCart.getValue().getTotalPrice();
		}
		return totalPrice;
	}
	@Transactional
	public void saveCartToDatabase(AccountEntity user, HashMap<Integer, CartDTO> cart) {
	    Session session = factory.getCurrentSession();
	    for (Map.Entry<Integer, CartDTO> entry : cart.entrySet()) {
	        CartDTO item = entry.getValue();

	        CartEntity cartEntity = new CartEntity();
	        
	        // Khởi tạo composite key
	        CartId cartId = new CartId(user.getUserInfo().getUserId(), entry.getKey());
	        cartEntity.setId(cartId);

	        cartEntity.setUserInfo(user.getUserInfo());
	        cartEntity.setProduct(productDAO.getProductById(entry.getKey()));
	        cartEntity.setQuantity(item.getQuantity());

	        session.save(cartEntity);
	    }
	}


}