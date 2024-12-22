package LapFarm.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;

@Repository
public class BrandDAO {
    @Autowired
    private SessionFactory factory;

    @Transactional
    public List<BrandEntity> getAllBrands() {
        Session session = factory.getCurrentSession();
        String hql = "FROM BrandEntity";
        Query<BrandEntity> query = session.createQuery(hql, BrandEntity.class);
        return query.list();
    }
    
    @Transactional
    public BrandEntity getBrandById(int idBrand) {
        Session session = factory.getCurrentSession();

        // HQL để lấy thông tin Category theo idCategory
        String hql = "FROM BrandEntity b WHERE b.idBrand = :idBrand";
        Query<BrandEntity> query = session.createQuery(hql, BrandEntity.class);
        query.setParameter("idBrand", idBrand);

        return query.uniqueResult();
    }
    
    @Transactional
    public BrandEntity getBrandByName(String brandName) {
        Session session = factory.getCurrentSession();
        String hql = "FROM BrandEntity b WHERE b.nameBrand = :brandName";
        Query<BrandEntity> query = session.createQuery(hql, BrandEntity.class);
        query.setParameter("brandName", brandName);
        return query.uniqueResult();
    }

    
    @Transactional
    public boolean checkBrandByName(String brandName) {
        Session session = factory.getCurrentSession();

        // HQL để kiểm tra sự tồn tại của loại hàng
        String hql = "SELECT count(c) FROM BrandEntity c WHERE c.nameBrand = :brandName";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("brandName", brandName);

        // Trả về true nếu có ít nhất một bản ghi
        return query.uniqueResult() > 0;
    }
    
    @Transactional
    public void saveBrand(BrandEntity brand) {
        Session session = factory.getCurrentSession();

        // Sử dụng phương thức persist để lưu đối tượng vào database
        session.persist(brand);
    }
    
    @Transactional
    public boolean updateBrand(BrandEntity brand) {
        Session session = factory.getCurrentSession();

        try {
            // Sử dụng phương thức update để cập nhật đối tượng vào database
            session.merge(brand);
            return true; // Cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Có lỗi xảy ra trong quá trình cập nhật
        }
    }
    
    @Transactional
	public List<BrandEntity> searchBrands(String searchQuery) {
	    // Kiểm tra nếu người dùng tìm kiếm theo số
	    boolean isNumeric = searchQuery.matches("^[0-9]+$"); // Kiểm tra nếu từ khóa tìm kiếm là số

	    Session session = factory.getCurrentSession();
	    String hql;

	    if (isNumeric) {
	        // Tìm kiếm theo ID, chuyển ID thành chuỗi và sử dụng LIKE
	        hql = "FROM BrandEntity c WHERE CAST(c.idBrand AS string) LIKE :searchQuery";
	    } else {
	        // Tìm kiếm theo tên, sử dụng LIKE
	        hql = "FROM BrandEntity c WHERE c.nameBrand LIKE :searchQuery";
	    }

	    Query<BrandEntity> query = session.createQuery(hql, BrandEntity.class);
	    // Nếu tìm kiếm là số, thêm dấu % để tìm kiếm chứa chuỗi số đó ở bất kỳ đâu
	 // Thiết lập tham số tìm kiếm, thêm dấu % nếu tìm kiếm theo fullName
	    query.setParameter("searchQuery", isNumeric ? "%" + Integer.parseInt(searchQuery) + "%" : "%" + searchQuery + "%");
	    
	    return query.getResultList();
	}
}
