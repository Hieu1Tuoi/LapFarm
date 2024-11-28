package LapFarm.DAO;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import LapFarm.Entity.CategoryEntity;

@Repository
public class CategoryDAO {
    @Autowired
    private SessionFactory factory;
    
    @Transactional
    public List<CategoryEntity> getAllCategories() {
        Session session = factory.getCurrentSession();
        String hql = "FROM CategoryEntity";
        Query<CategoryEntity> query = session.createQuery(hql, CategoryEntity.class);
        return query.list();
    }
    

  
    @Transactional
    public CategoryEntity getCategoryById(int idCategory) {
        Session session = factory.getCurrentSession();

        // HQL để lấy thông tin Category theo idCategory
        String hql = "FROM CategoryEntity c WHERE c.idCategory = :idCategory";
        Query<CategoryEntity> query = session.createQuery(hql, CategoryEntity.class);
        query.setParameter("idCategory", idCategory);

        return query.uniqueResult();
    }

    @Transactional
    public void saveCategory(CategoryEntity category) {
        Session session = factory.getCurrentSession();

        // Sử dụng phương thức persist để lưu đối tượng vào database
        session.persist(category);
    }

    @Transactional
    public boolean checkCategory(String categoryName) {
        Session session = factory.getCurrentSession();

        // HQL để kiểm tra sự tồn tại của loại hàng
        String hql = "SELECT count(c) FROM CategoryEntity c WHERE c.nameCategory = :categoryName";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("categoryName", categoryName);

        // Trả về true nếu có ít nhất một bản ghi
        return query.uniqueResult() > 0;
    }


}