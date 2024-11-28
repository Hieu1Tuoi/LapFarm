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
import LapFarm.Entity.ProductEntity;
import LapFarm.Entity.UserInfoEntity;

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
	    // Lấy sản phẩm từ database
	    ProductDTO product = productDAO.findProductDTOById(id);

	    if (product != null) {
	        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
	        CartDTO itemCart = cart.get(id);
	        
	        if (itemCart != null) {
	            // Nếu sản phẩm đã có trong giỏ hàng, tăng số lượng
	            itemCart.setQuantity(itemCart.getQuantity() + 1);
	        } else {
	            // Nếu sản phẩm chưa có trong giỏ hàng, tạo mới CartDTO và thêm vào giỏ hàng
	            itemCart = new CartDTO();
	            itemCart.setProduct(product);
	            itemCart.setQuantity(1);
	        }

	        // Tính lại giá trị tổng cho sản phẩm trong giỏ
	        itemCart.setTotalPrice(itemCart.getQuantity() * product.calPrice());

	        // Cập nhật giỏ hàng
	        cart.put(id, itemCart);
	    }

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
	        cart.remove(id); // Xóa sản phẩm khỏi giỏ hàng
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
	
	//Lưu product cart với có user đăng nhập
	@Transactional
	public void saveCartToDatabase(AccountEntity user, HashMap<Integer, CartDTO> cartSession) {
	    Session session = factory.getCurrentSession();
	    HashMap<Integer, CartDTO> cartDatabase = getCartFromDatabase(user.getUserInfo().getUserId());

	    for (Map.Entry<Integer, CartDTO> entry : cartSession.entrySet()) {
	        int productId = entry.getKey();
	        CartDTO cartDTO = entry.getValue();

	        if (cartDatabase.containsKey(productId)) {
	            // Nếu sản phẩm đã tồn tại, cập nhật số lượng
	            CartDTO existingCart = cartDatabase.get(productId);
	            existingCart.setQuantity(existingCart.getQuantity() + cartDTO.getQuantity());
	            existingCart.setTotalPrice(existingCart.getQuantity() * existingCart.getProduct().calPrice());
	        } else {
	            // Nếu không tồn tại, thêm mới
	            CartEntity newCartEntity = new CartEntity();
	            newCartEntity.setId(new CartId(user.getUserInfo().getUserId(), productId));
	            newCartEntity.setUserInfo(user.getUserInfo());
	            newCartEntity.setProduct(productDAO.getProductById(productId));
	            newCartEntity.setQuantity(cartDTO.getQuantity());
	            session.save(newCartEntity);
	        }
	    }
	}


	
	 @Transactional
	    public HashMap<Integer, CartDTO> getCartFromDatabase(int userId) {
	        Session session = factory.getCurrentSession();
	        String hql = "FROM CartEntity c WHERE c.userInfo.userId = :userId";
	        Query<CartEntity> query = session.createQuery(hql, CartEntity.class);
	        query.setParameter("userId", userId);

	        List<CartEntity> cartEntities = query.list();
	        HashMap<Integer, CartDTO> cart = new HashMap<>();

	        for (CartEntity entity : cartEntities) {
	            CartDTO cartDTO = new CartDTO();
	            cartDTO.setProduct(productDAO.findProductDTOById(entity.getProduct().getIdProduct()));
	            cartDTO.setQuantity(entity.getQuantity());
	            cartDTO.setTotalPrice(cartDTO.getQuantity() * cartDTO.getProduct().calPrice());
	            cart.put(entity.getProduct().getIdProduct(), cartDTO);
	        }
	        return cart;
	    }

	 @Transactional
	 public void syncCartToDatabase(int userId, HashMap<Integer, CartDTO> cart) {
	     Session session = factory.getCurrentSession();

	     // Duyệt qua tất cả các sản phẩm trong giỏ hàng (cart)
	     for (CartDTO cartDTO : cart.values()) {
	         // Truy vấn để tìm CartEntity theo userId và productId
	         String hql = "FROM CartEntity c WHERE c.userInfo.userId = :userId AND c.product.idProduct = :productId";
	         Query<CartEntity> query = session.createQuery(hql, CartEntity.class);
	         query.setParameter("userId", userId);
	         query.setParameter("productId", cartDTO.getProduct().getIdProduct());

	         List<CartEntity> existingCartList = query.getResultList();

	         if (!existingCartList.isEmpty()) {
	             // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
	             CartEntity existingCart = existingCartList.get(0);
	             existingCart.setQuantity(existingCart.getQuantity() + cartDTO.getQuantity());
	             session.update(existingCart);
	         } else {
	             // Nếu sản phẩm chưa có trong giỏ hàng, tạo mới với số lượng là 1
	             CartEntity newCartEntity = new CartEntity();
	             newCartEntity.setId(new CartId(userId, cartDTO.getProduct().getIdProduct()));
	             newCartEntity.setUserInfo(session.get(UserInfoEntity.class, userId));
	             newCartEntity.setProduct(session.get(ProductEntity.class, cartDTO.getProduct().getIdProduct()));
	             newCartEntity.setQuantity(cartDTO.getQuantity());

	             // Lưu sản phẩm mới vào giỏ hàng
	             session.save(newCartEntity);
	         }
	     }
	 }

	 @Transactional
	 public void deleteCartFromDatabase(int userId, int productId) {
	     Session session = factory.getCurrentSession();
	     String hql = "DELETE FROM CartEntity c WHERE c.userInfo.userId = :userId AND c.product.idProduct = :productId";
	     Query query = session.createQuery(hql);
	     query.setParameter("userId", userId);
	     query.setParameter("productId", productId);
	     query.executeUpdate();
	 }

	 
	 @Transactional
	 public void updateProductQuantityInDatabase(int userId, int productId, int quantity) {
	     Session session = factory.getCurrentSession();

	     // Tìm sản phẩm trong giỏ hàng của người dùng
	     String hql = "FROM CartEntity c WHERE c.userInfo.userId = :userId AND c.product.idProduct = :productId";
	     Query<CartEntity> query = session.createQuery(hql, CartEntity.class);
	     query.setParameter("userId", userId);
	     query.setParameter("productId", productId);

	     List<CartEntity> cartList = query.getResultList();

	     if (!cartList.isEmpty()) {
	         // Nếu tìm thấy sản phẩm, cập nhật số lượng và tổng giá
	         CartEntity cartEntity = cartList.get(0);
	         cartEntity.setQuantity(quantity);
	         session.update(cartEntity);  // Cập nhật vào cơ sở dữ liệu
	     }
	 }



}