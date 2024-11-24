package LapFarm.DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.apache.commons.codec.binary.Hex;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.UserInfoEntity;

@Repository
public class UserDAO {
	@Autowired
	private SessionFactory factory;

	public boolean checkEmailExists(String email) {
		Session session = factory.openSession();
		try {
			String hql = "FROM AccountEntity WHERE email = :email";
			Query query = session.createQuery(hql);
			query.setParameter("email", email);
			AccountEntity existingAccount = (AccountEntity) query.uniqueResult();
			return existingAccount != null;
		} finally {
			session.close();
		}
	}

	public void saveAccount(AccountEntity acc) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		acc.setPassword(hashPasswordWithMD5(acc.getPassword()));

		try {
			session.save(acc);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public void createUserinfo(String email) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			String hql = "FROM AccountEntity WHERE email = :email";
			Query query = session.createQuery(hql);
			query.setParameter("email", email);
			AccountEntity account = (AccountEntity) query.uniqueResult();

			if (account != null) {
				UserInfoEntity userinfo = new UserInfoEntity();
				userinfo.setAccount(account);

				session.save(userinfo);
				t.commit();
			} else {
				throw new RuntimeException("Tài khoản không tồn tại!");
			}
		} catch (Exception e) {
			t.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public void updateUserinfo(String email, String fullname, String DOB, String phone, String address, String sex) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			// Lấy đối tượng UserInfoEntity từ cơ sở dữ liệu dựa trên email
			String hql = "FROM UserInfoEntity WHERE account.email = :email";
			Query query = session.createQuery(hql);
			query.setParameter("email", email);
			UserInfoEntity userInfo = (UserInfoEntity) query.uniqueResult();

			// Kiểm tra nếu userInfo tồn tại
			if (userInfo != null) {
				// Cập nhật thông tin
				userInfo.setFullName(fullname);
				userInfo.setDob(DOB);
				userInfo.setPhone(phone);
				userInfo.setSex(sex);
				userInfo.setAddress(address);

				// Lưu thay đổi vào cơ sở dữ liệu
				session.update(userInfo);
				t.commit();
			} else {
				System.out.println("User không tồn tại với email: " + email);
				t.rollback();
			}
		} catch (Exception e) {
			t.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public void updatePassword(String email, String newPassword) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			String hql = "FROM AccountEntity WHERE email = :email";
			Query query = session.createQuery(hql);
			query.setParameter("email", email);
			AccountEntity existingAccount = (AccountEntity) query.uniqueResult();

			if (existingAccount != null) {
				String hashedPassword = hashPasswordWithMD5(newPassword);
				existingAccount.setPassword(hashedPassword);
				session.update(existingAccount);
				t.commit();
			} else {
				throw new RuntimeException("Email không tồn tại trong hệ thống!");
			}
		} catch (Exception e) {
			t.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	private String hashPasswordWithMD5(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(password.getBytes());
			return Hex.encodeHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 algorithm not found", e);
		}
	}
}