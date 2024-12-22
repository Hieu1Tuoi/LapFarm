package LapFarm.DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.binary.Hex;

import LapFarm.Bean.Mailer;
import LapFarm.DTO.OrdersDTO;
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
	@Autowired
    private Mailer mailer;

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
			String hql = "FROM UserInfoEntity u WHERE u.account.role.id = 0";
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
	        // Tìm tài khoản theo email
	        String hql = "FROM AccountEntity WHERE email = :email";
	        Query query = session.createQuery(hql);
	        query.setParameter("email", email);
	        AccountEntity existingAccount = (AccountEntity) query.uniqueResult();

	        if (existingAccount != null) {
	            // Mã hóa mật khẩu mới
	            String hashedPassword = hashPasswordWithMD5(newPassword);
	            existingAccount.setPassword(hashedPassword);

	            // Cập nhật ngày thay đổi mật khẩu
	            existingAccount.setLastPasswordChangeDate(LocalDateTime.now());  // Cập nhật ngày thay đổi mật khẩu

	            // Cập nhật tài khoản
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
	public boolean verifyPassword(String email, String inputPassword) {
	    Session session = factory.openSession();
	    try {
	        // Find account by email
	        String hql = "FROM AccountEntity WHERE email = :email";
	        Query query = session.createQuery(hql);
	        query.setParameter("email", email);
	        AccountEntity account = (AccountEntity) query.uniqueResult();
	        
	        if (account != null) {
	            // Hash the input password
	            String hashedInputPassword = hashPasswordWithMD5(inputPassword);
	            
	            // Compare the hashed input password with stored password
	            return hashedInputPassword.equals(account.getPassword());
	        }
	        return false; // Account not found
	    } finally {
	        session.close();
	    }
	}
	 public String sendVerificationCode(String email) {
	        try {
				// Sử dụng phương thức VerifyCode có sẵn từ Mailer để gửi mã
				return mailer.VerifyCode(email);
			} catch (Exception e) {
				throw new RuntimeException("Lỗi khi gửi mã xác nhận: " + e.getMessage());
			}
		}

	 @Transactional
	 public List<UserInfoDTO> searchUsers(String searchQuery) {
	     // Kiểm tra nếu người dùng tìm kiếm theo số
	     boolean isNumeric = searchQuery.matches("^[0-9]+$");
	     boolean isPhoneNumber = searchQuery.matches("^[0-9]{10}$"); // Kiểm tra định dạng số điện thoại (10-11 chữ số)

	     // Lấy phiên làm việc hiện tại
	     Session session = factory.getCurrentSession();
	     String hql;

	     if (isNumeric) {
	         if (isPhoneNumber) {
	             // Tìm kiếm theo ID hoặc số điện thoại
	             hql = "SELECT u FROM UserInfoEntity u WHERE (CAST(u.userId AS string) LIKE :searchQuery OR u.phone LIKE :searchQuery) AND u.account.role.id = 0";
	         } else {
	             // Tìm kiếm chỉ theo ID
	             hql = "SELECT u FROM UserInfoEntity u WHERE CAST(u.userId AS string) LIKE :searchQuery AND u.account.role.id = 0";
	         }
	     } else {
	         // Tìm kiếm theo fullName
	         hql = "SELECT u FROM UserInfoEntity u WHERE u.fullName LIKE :searchQuery AND u.account.role.id = 0";
	     }

	     // Tạo truy vấn
	     Query<UserInfoEntity> query = session.createQuery(hql, UserInfoEntity.class);

	     // Thiết lập tham số tìm kiếm, thêm dấu % nếu tìm kiếm không phải số chính xác
	     query.setParameter("searchQuery", "%" + searchQuery + "%");

	     // Lấy danh sách kết quả
	     List<UserInfoEntity> userInfoList = query.getResultList();

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

	     return userInfoDTOList;
	 }
	 
	 @Transactional
	 public boolean changeUserState(int userId, String newState) {
	     try {
	         // Lấy phiên làm việc hiện tại
	         Session session = factory.getCurrentSession();

	         // HQL để lấy thông tin UserInfoEntity theo userId
	         String hqlUserInfo = "SELECT u FROM UserInfoEntity u WHERE u.userId = :userId";
	         Query<UserInfoEntity> queryUserInfo = session.createQuery(hqlUserInfo, UserInfoEntity.class);
	         queryUserInfo.setParameter("userId", userId);

	         // Kiểm tra nếu UserInfoEntity tồn tại
	         UserInfoEntity userInfo = queryUserInfo.uniqueResult();
	         if (userInfo == null) {
	             System.out.println("Không tìm thấy UserInfoEntity với userId: " + userId);
	             return false;
	         }

	         // Lấy AccountEntity từ email của UserInfoEntity
	         String email = userInfo.getAccount().getEmail();
	         String hqlAccount = "SELECT a FROM AccountEntity a WHERE a.email = :email";
	         Query<AccountEntity> queryAccount = session.createQuery(hqlAccount, AccountEntity.class);
	         queryAccount.setParameter("email", email);

	         AccountEntity account = queryAccount.uniqueResult();
	         if (account == null) {
	             System.out.println("Không tìm thấy AccountEntity với email: " + email);
	             return false;
	         }

	         // Thay đổi state của AccountEntity
	         account.setState(newState);
	         session.merge(account);

	         System.out.println("Đã thay đổi trạng thái của userId " + userId + " thành: " + newState);
	         return true;

	     } catch (Exception e) {
	         e.printStackTrace();
	         return false;
	     }
	 }



	}