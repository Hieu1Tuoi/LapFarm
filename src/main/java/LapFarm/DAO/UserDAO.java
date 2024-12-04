package LapFarm.DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.binary.Hex;

import LapFarm.DTO.UserInfoDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.OrdersEntity;
import LapFarm.Entity.UserInfoEntity;

@Transactional
@Repository
public class UserDAO {
	@Autowired
	private SessionFactory factory;
	@Autowired
	private OrdersDAO ordersDAO;

	public List<UserInfoEntity> getAllUserInfo() {
		Session session = factory.openSession();
		try {
			String hql = "FROM UserInfoEntity";
			Query<UserInfoEntity> query = session.createQuery(hql, UserInfoEntity.class);
			return query.list(); // Trả về danh sách tất cả các bản ghi trong UserInfoEntity
		} finally {
			session.close();
		}
	}

	// Phương thức trả về danh sách người dùng với số lượng đơn hàng
	public List<UserInfoDTO> getAllUserInfoWithOrderCount() {
		Session session = factory.openSession();
		try {
			String hql = "FROM UserInfoEntity";
			Query<UserInfoEntity> query = session.createQuery(hql, UserInfoEntity.class);
			List<UserInfoEntity> userInfoList = query.list();

			// Duyệt qua tất cả người dùng và thêm số lượng đơn hàng
			List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
			for (UserInfoEntity userInfo : userInfoList) {
				// Gọi OrdersDAO để lấy số lượng đơn hàng của người dùng này
				long numberOfOrders = ordersDAO.countOrdersByUserId(userInfo.getUserId());

				// Lấy trạng thái (state) từ AccountEntity
				String state = userInfo.getAccount() != null ? userInfo.getAccount().getState() : null;

				// Tạo UserInfoDTO và thêm vào danh sách
				UserInfoDTO userInfoDTO = new UserInfoDTO(userInfo.getUserId(), userInfo.getAccount().getEmail(),
						userInfo.getFullName(), userInfo.getDob(), userInfo.getSex(), userInfo.getPhone(),
						userInfo.getAvatar(), userInfo.getAddress(), numberOfOrders, state);
				userInfoDTOList.add(userInfoDTO);
			}

			return userInfoDTOList; // Trả về danh sách UserInfoDTO
		} finally {
			session.close();
		}
	}

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

	public AccountEntity getAccountByEmail(String email) {
		// Mở session từ factory
		Session session = factory.openSession();
		try {
			// Tạo câu lệnh HQL
			String hql = "FROM AccountEntity WHERE email = :email";
			// Tạo query từ HQL
			Query query = session.createQuery(hql);
			// Đặt giá trị tham số email
			query.setParameter("email", email);
			// Lấy kết quả duy nhất (nếu có)
			AccountEntity existingAccount = (AccountEntity) query.uniqueResult();
			// Trả về đối tượng nếu tìm thấy, ngược lại trả về null
			return existingAccount;
		} finally {
			// Đóng session sau khi xong
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
	
	public boolean updateUserState(int userId, String state) {
		// Lấy session hiện tại từ factory
        Session session = factory.getCurrentSession();
        
        // Tìm đơn hàng theo id
        AccountEntity account = session.get(AccountEntity.class, userId);
        
        // Kiểm tra nếu đơn hàng tồn tại
        if (account != null) {
            // Cập nhật trạng thái của đơn hàng
            account.setState(state);
            
            // Lưu lại trạng thái mới của đơn hàng vào cơ sở dữ liệu
            session.merge(account);// Sử dụng `update` thay vì `saveOrUpdate` vì chúng ta chỉ cập nhật trạng thái
            return true;
        }
        return false;
	}
}