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
    public int getProductCountByCategoryId(int idCategory) {
        Session session = factory.getCurrentSession();

        // HQL để đếm số lượng sản phẩm theo idCategory
        String hql = "SELECT COUNT(p) FROM ProductEntity p WHERE p.category.idCategory = :idCategory";
        Query<Integer> query = session.createQuery(hql, Integer.class);
        query.setParameter("idCategory", idCategory);

        // Trả về số lượng sản phẩm
        return query.uniqueResult();
    }
    
}