package LapFarm.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import LapFarm.Entity.CartEntity;

import java.util.List;

@Repository
public class CartDAO {

	@Autowired
	private SessionFactory factory;

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

}