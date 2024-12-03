package LapFarm.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import LapFarm.Entity.ReviewEntity;

import java.util.List;

@Repository
public class ReviewDAO {

    @Autowired
    private SessionFactory factory;

    // Phương thức lưu đánh giá vào cơ sở dữ liệu
    @Transactional
    public void saveReview(ReviewEntity reviewEntity) {
        if (reviewEntity == null || reviewEntity.getProduct() == null || reviewEntity.getUser() == null) {
            throw new IllegalArgumentException("Review or related entities are null.");
        }
        Session session = factory.getCurrentSession();
        session.saveOrUpdate(reviewEntity);  // Lưu hoặc cập nhật đánh giá
    }

    @Transactional
    public List<ReviewEntity> getReviewsByProductId(int productId, int page, int pageSize) {
        Session session = factory.getCurrentSession();
        String hql = "FROM ReviewEntity r WHERE r.product.idProduct = :productId ORDER BY r.reviewDate DESC";

        Query<ReviewEntity> query = session.createQuery(hql, ReviewEntity.class);
        query.setParameter("productId", productId);
        query.setFirstResult((page - 1) * pageSize); // Bắt đầu từ kết quả đầu tiên của trang
        query.setMaxResults(pageSize); // Số lượng kết quả trên mỗi trang

        return query.list();
    }

    @Transactional
    public List<ReviewEntity> getAllReviewsByProductId(int productId) {
        Session session = factory.getCurrentSession();
        // Sử dụng FETCH JOIN để đảm bảo lấy đầy đủ dữ liệu từ bảng liên kết
        String hql = "SELECT r FROM ReviewEntity r " +
                     "JOIN FETCH r.user u " +
                     "JOIN FETCH r.product p " +
                     "WHERE r.product.idProduct = :productId ORDER BY r.reviewDate DESC";
        Query<ReviewEntity> query = session.createQuery(hql, ReviewEntity.class);
        query.setParameter("productId", productId);
        return query.list();
    }

    @Transactional
    public List<ReviewEntity> getReviewsByProductIdWithPagination(int productId, int page, int pageSize) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT r FROM ReviewEntity r " +
                     "JOIN FETCH r.user u " +
                     "JOIN FETCH r.product p " +
                     "WHERE r.product.idProduct = :productId ORDER BY r.reviewDate DESC";
        Query<ReviewEntity> query = session.createQuery(hql, ReviewEntity.class);
        query.setParameter("productId", productId);

        // Thiết lập phân trang
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.list();
    }

    @Transactional
    public int countReviewsByProductId(int productId) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT COUNT(r) FROM ReviewEntity r WHERE r.product.idProduct = :productId";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("productId", productId);

        return query.uniqueResult().intValue();
    }
    @Transactional
    public List<ReviewEntity> getReviewsByUserId(int userId) {
        Session session = factory.getCurrentSession();
        String hql = "FROM ReviewEntity r WHERE r.user.id = :userId ORDER BY r.reviewDate DESC";
        Query<ReviewEntity> query = session.createQuery(hql, ReviewEntity.class);
        query.setParameter("userId", userId);
        return query.list();
    }

}
