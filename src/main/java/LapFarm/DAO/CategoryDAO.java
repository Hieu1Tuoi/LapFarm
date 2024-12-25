package LapFarm.DAO;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import LapFarm.Entity.CategoryEntity;

@Repository
public class CategoryDAO {
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory factory;
    
    @Autowired
    @Qualifier("sessionFactoryVisitor")
    private SessionFactory factoryVisitor;
    
    @Transactional("transactionManagerVisitor")
    public List<CategoryEntity> getAllCategories() {
        Session session = factoryVisitor.getCurrentSession();
        String hql = "FROM CategoryEntity";
        Query<CategoryEntity> query = session.createQuery(hql, CategoryEntity.class);
        return query.list();
    }
    

  
    @Transactional("transactionManagerVisitor")
    public CategoryEntity getCategoryById(int idCategory) {
        Session session = factoryVisitor.getCurrentSession();

        // HQL để lấy thông tin Category theo idCategory
        String hql = "FROM CategoryEntity c WHERE c.idCategory = :idCategory";
        Query<CategoryEntity> query = session.createQuery(hql, CategoryEntity.class);
        query.setParameter("idCategory", idCategory);

        return query.uniqueResult();
    }
    
    @Transactional("transactionManagerVisitor")
    public CategoryEntity getCategoryByName(String nameCategory) {
        Session session = factoryVisitor.getCurrentSession();

        // HQL để lấy thông tin Category theo nameCategory
        String hql = "FROM CategoryEntity c WHERE c.nameCategory = :nameCategory";
        Query<CategoryEntity> query = session.createQuery(hql, CategoryEntity.class);
        query.setParameter("nameCategory", nameCategory);

        return query.uniqueResult();
    }


    @Transactional("transactionManager")
    public void saveCategory(CategoryEntity category) {
        Session session = factory.getCurrentSession();

        // Sử dụng phương thức persist để lưu đối tượng vào database
        session.persist(category);
    }

    @Transactional("transactionManagerVisitor")
    public boolean checkCategoryByName(String categoryName) {
        Session session = factoryVisitor.getCurrentSession();

        // HQL để kiểm tra sự tồn tại của loại hàng
        String hql = "SELECT count(c) FROM CategoryEntity c WHERE c.nameCategory = :categoryName";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("categoryName", categoryName);

        // Trả về true nếu có ít nhất một bản ghi
        return query.uniqueResult() > 0;
    }
    
		@Transactional("transactionManager")
	    public boolean updateCategory(CategoryEntity category) {
	        Session session = factory.getCurrentSession();
	
	        try {
	            // Sử dụng phương thức update để cập nhật đối tượng vào database
	            session.merge(category);
	            return true; // Cập nhật thành công
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false; // Có lỗi xảy ra trong quá trình cập nhật
	        }
	    }
		
		@Transactional("transactionManagerVisitor")
		public List<CategoryEntity> searchCategories(String searchQuery) {
		    // Kiểm tra nếu người dùng tìm kiếm theo số
		    boolean isNumeric = searchQuery.matches("^[0-9]+$"); // Kiểm tra nếu từ khóa tìm kiếm là số

		    Session session = factoryVisitor.getCurrentSession();
		    String hql;

		    if (isNumeric) {
		        // Tìm kiếm theo ID, chuyển ID thành chuỗi và sử dụng LIKE
		        hql = "FROM CategoryEntity c WHERE CAST(c.idCategory AS string) LIKE :searchQuery";
		    } else {
		        // Tìm kiếm theo tên, sử dụng LIKE
		        hql = "FROM CategoryEntity c WHERE c.nameCategory LIKE :searchQuery";
		    }

		    Query<CategoryEntity> query = session.createQuery(hql, CategoryEntity.class);
		    // Nếu tìm kiếm là số, thêm dấu % để tìm kiếm chứa chuỗi số đó ở bất kỳ đâu
		 // Thiết lập tham số tìm kiếm, thêm dấu % nếu tìm kiếm theo fullName
		    query.setParameter("searchQuery", isNumeric ? "%" + Integer.parseInt(searchQuery) + "%" : "%" + searchQuery + "%");
		    
		    return query.getResultList();
		}

	
}