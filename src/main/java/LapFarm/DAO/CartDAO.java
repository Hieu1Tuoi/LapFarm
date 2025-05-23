package LapFarm.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import LapFarm.DTO.CartDTO;
import LapFarm.DTO.CartProductDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.CartEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Entity.UserInfoEntity;
import jakarta.persistence.NoResultException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartDAO {

	@Autowired
	@Qualifier("sessionFactoryUser")
	private SessionFactory factoryUser;
	
	@Autowired
	@Qualifier("sessionFactoryVisitor")
	private SessionFactory factoryVisitor;

	@Autowired
	ProductDAO productDAO = new ProductDAO();

	@Transactional("transactionManagerUser")
	public List<CartEntity> getCartByUserEmail(String email) {
		Session session = factoryUser.getCurrentSession();
		String hql = "FROM CartEntity ce WHERE ce.userInfo.account.email = :email";
		Query<CartEntity> query = session.createQuery(hql, CartEntity.class);
		query.setParameter("email", email);
		return query.getResultList();
	}
	
	@Transactional("transactionManagerUser")
	public CartEntity getCartItem(UserInfoEntity userInfoEntity, ProductEntity productEntity) {
	    // Lấy session hiện tại
	    Session session = factoryUser.getCurrentSession();

	    // Tạo câu lệnh HQL
	    String hql = "FROM CartEntity c WHERE c.userInfo = :userInfo AND c.product = :product";

	    // Thực thi truy vấn
	    Query<CartEntity> query = session.createQuery(hql, CartEntity.class);
	    query.setParameter("userInfo", userInfoEntity);
	    query.setParameter("product", productEntity);

	    // Trả về kết quả (nếu có)
	    try {
	        return query.getSingleResult();
	    } catch (NoResultException e) {
			System.out.println("Lỗi getCartItem: " + e);
	        return null;
	    }
	}

	@Transactional("transactionManagerUser")
	public CartEntity getCartById(int id) {
		Session session = factoryUser.getCurrentSession();
		String hql = "FROM CartEntity ce WHERE ce.id = :id";
		Query<CartEntity> query = session.createQuery(hql, CartEntity.class);
		query.setParameter("id", id);
		return query.uniqueResult(); // Trả về duy nhất một đối tượng
	}

	@Transactional("transactionManagerUser")
	public void deleteCartById(int cartId) {
		Session session = factoryUser.getCurrentSession();
		String hql = "DELETE FROM CartEntity c WHERE c.id = :cartId";
		session.createQuery(hql).setParameter("cartId", cartId).executeUpdate();
	}

	// Tạo mới Cart
	@Transactional("transactionManagerUser")
	public boolean createCart(CartEntity cartEntity) {
		Session session = null;
		Transaction transaction = null;
		
		CartEntity cartEntity0 = getCartItem(cartEntity.getUserInfo(), cartEntity.getProduct());
		if(cartEntity0 == null) {
			try {
				session = factoryUser.openSession();
				transaction = session.beginTransaction();
				session.save(cartEntity); // Sử dụng save để tạo mới
				transaction.commit();
				return true;
			} catch (Exception e) {
				System.out.println("Lỗi thêm vào giỏ hàng: " + e);
				return false;
			} finally {
				if (session != null)
					session.close();
			}
		}else {
			cartEntity0.setQuantity(cartEntity0.getQuantity() + 1);
			updateCart(cartEntity0);
			return false;
		}
	}

	// Cập nhật Cart
	@Transactional("transactionManagerUser")
	public boolean updateCart(CartEntity cartEntity) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = factoryUser.openSession();
			transaction = session.beginTransaction();
			session.update(cartEntity); // Sử dụng update để cập nhật
			transaction.commit();
			return true;
		} catch (Exception e) {
			System.out.println("Lỗi thêm vào giỏ hàng: " + e);
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Transactional("transactionManagerUser")
	public void syncProductQuantityToDatabase(int userId, int productId, int increment) {
		Session session = factoryUser.getCurrentSession();

		// Kiểm tra sản phẩm đã tồn tại trong giỏ hàng chưa
		String hql = "FROM CartEntity c WHERE c.userInfo.userId = :userId AND c.product.idProduct = :productId";
		Query<CartEntity> query = session.createQuery(hql, CartEntity.class);
		query.setParameter("userId", userId);
		query.setParameter("productId", productId);

		List<CartEntity> cartList = query.getResultList();

		if (!cartList.isEmpty()) {
			// Nếu tồn tại, tăng số lượng
			CartEntity existingCart = cartList.get(0);
			existingCart.setQuantity(existingCart.getQuantity() + increment);
			session.update(existingCart);
		} else {
			// Nếu chưa có, thêm mới sản phẩm
			CartEntity newCartEntity = new CartEntity();
			UserInfoEntity userInfo = session.get(UserInfoEntity.class, userId);
			ProductEntity product = session.get(ProductEntity.class, productId);

			if (userInfo != null && product != null) {
				newCartEntity.setUserInfo(userInfo);
				newCartEntity.setProduct(product);
				newCartEntity.setQuantity(increment);
				session.save(newCartEntity);
			} else {
				throw new IllegalArgumentException("User hoặc Product không tồn tại");
			}
		}
	}

	// Them gio hang
	@Transactional("transactionManagerUser")
	public HashMap<Integer, CartDTO> AddCart(int id, HashMap<Integer, CartDTO> cart, int userId) {
		Session session = factoryUser.getCurrentSession();

		// Lấy thông tin sản phẩm từ database
		ProductDTO product = productDAO.findProductDTOById(id);

		if (product != null) {
			CartDTO itemCart = cart.get(id);

			if (itemCart != null) {
				// Nếu sản phẩm đã có trong session, tăng số lượng
				itemCart.setQuantity(itemCart.getQuantity() + 1);
				itemCart.setTotalPrice(itemCart.getQuantity() * product.calPrice());
			} else {
				// Nếu chưa có trong session, thêm mới sản phẩm
				itemCart = new CartDTO();
				itemCart.setId(cart.size() + 1);
				itemCart.setProduct(product);
				itemCart.setQuantity(1);
				itemCart.setTotalPrice(product.calPrice());
				cart.put(cart.size() + 1, itemCart);
			}

			// Đồng bộ với database nếu người dùng đã đăng nhập
			if (userId > 0) {
				syncProductQuantityToDatabase(userId, id, 1); // Tăng thêm 1
			}
		}

		return cart;
	}

	// Chỉnh sửa giỏ hàng
	@Transactional("transactionManagerUser")
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

	// Xóa khỏi giỏ hàng
	@Transactional("transactionManagerUser")
	public HashMap<Integer, CartDTO> DeleteCart(int id, HashMap<Integer, CartDTO> cart) {
		if (cart == null) {
			return cart;
		}
		if (cart.containsKey(id)) {
			cart.remove(id); // Xóa sản phẩm khỏi giỏ hàng
		}
		return cart;
	}

	// Tính tổng số lượng trong giỏ
	@Transactional("transactionManagerUser")
	public int TotalQuanty(HashMap<Integer, CartDTO> cart) {
		int totalQuanty = 0;
		for (Map.Entry<Integer, CartDTO> itemCart : cart.entrySet()) {
			totalQuanty += itemCart.getValue().getQuantity();
		}
		return totalQuanty;
	}

	// Tính tổng giá tiền trong giỏ
	@Transactional("transactionManagerUser")
	public double TotalPrice(HashMap<Integer, CartDTO> cart) {
		int totalPrice = 0;
		for (Map.Entry<Integer, CartDTO> itemCart : cart.entrySet()) {
			totalPrice += itemCart.getValue().getTotalPrice();
		}
		return totalPrice;
	}

	// Lưu product cart với có user đăng nhập
	@Transactional("transactionManagerUser")
	public void saveCartToDatabase(AccountEntity user, HashMap<Integer, CartDTO> cartSession) {
		Session session = factoryUser.getCurrentSession();
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
				newCartEntity.setUserInfo(user.getUserInfo());
				newCartEntity.setProduct(productDAO.getProductById(productId));
				newCartEntity.setQuantity(cartDTO.getQuantity());
				session.save(newCartEntity);
			}
		}
	}

	@Transactional("transactionManagerUser")
	public HashMap<Integer, CartDTO> getCartFromDatabase(int userId) {
	    HashMap<Integer, CartDTO> cart = new HashMap<>();
	    Session session = null;
	    
	    try {
	        session = factoryUser.getCurrentSession();
	        String hql = "FROM CartEntity c WHERE c.userInfo.userId = :userId";
	        Query<CartEntity> query = session.createQuery(hql, CartEntity.class);
	        query.setParameter("userId", userId);

	        List<CartEntity> cartEntities = query.list();

	        // Xử lý dữ liệu giỏ hàng
	        for (CartEntity entity : cartEntities) {
	            CartDTO cartDTO = new CartDTO();
	            cartDTO.setId(entity.getId());
	            cartDTO.setProduct(productDAO.findProductDTOById(entity.getProduct().getIdProduct()));
	            cartDTO.setQuantity(entity.getQuantity());
	            cartDTO.setTotalPrice(cartDTO.getQuantity() * cartDTO.getProduct().calPrice());
	            cart.put(entity.getProduct().getIdProduct(), cartDTO);
	        }
	    } catch (Exception e) {
	        // Xử lý lỗi và ghi log nếu cần thiết
	        e.printStackTrace();  // In lỗi ra console (nên thay thế bằng một công cụ log thực tế như SLF4J, Log4j)
	        throw new RuntimeException("Lỗi khi lấy giỏ hàng từ cơ sở dữ liệu: " + e.getMessage(), e);
	    }
	    
	    return cart;
	}

	@Transactional("transactionManagerUser")
	public void syncCartToDatabase(int userId, HashMap<Integer, CartDTO> cart) {
		Session session = factoryUser.getCurrentSession();

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
				newCartEntity.setUserInfo(session.get(UserInfoEntity.class, userId));
				newCartEntity.setProduct(session.get(ProductEntity.class, cartDTO.getProduct().getIdProduct()));
				newCartEntity.setQuantity(cartDTO.getQuantity());

				// Lưu sản phẩm mới vào giỏ hàng
				session.save(newCartEntity);
			}
		}
	}

	@Transactional("transactionManagerUser")
	public void deleteCartFromDatabase(int userId, int productId) {
		Session session = factoryUser.getCurrentSession();
		String hql = "DELETE FROM CartEntity c WHERE c.userInfo.userId = :userId AND c.product.idProduct = :productId";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("productId", productId);
		query.executeUpdate();
	}

	@Transactional("transactionManagerUser")
	public void updateProductQuantityInDatabase(int userId, int idCart, int quantity) {
		Session session = factoryUser.getCurrentSession();

		// Tìm sản phẩm trong giỏ hàng của người dùng
		String hql = "FROM CartEntity c WHERE c.id = :idCart";
		Query<CartEntity> query = session.createQuery(hql, CartEntity.class);
		query.setParameter("idCart", idCart);

		List<CartEntity> cartList = query.getResultList();

		if (!cartList.isEmpty()) {
			// Nếu tìm thấy sản phẩm, cập nhật số lượng và tổng giá
			CartEntity cartEntity = cartList.get(0);
			cartEntity.setQuantity(quantity);
			session.update(cartEntity); // Cập nhật vào cơ sở dữ liệu
		}
	}

	// lấy số luong product theo id (Thanh Nhat)
	@Transactional("transactionManagerUser")
	public int getProductQuantity(int productId) {
		Session session = factoryUser.getCurrentSession();

		// Viết câu truy vấn HQL để lấy quantity của product
		String hql = "SELECT p.quantity FROM ProductEntity p WHERE p.id = :productId";

		Query query = session.createQuery(hql);
		query.setParameter("productId", productId);

		// Trả về số lượng sản phẩm
		return (int) query.uniqueResult();
	}

}