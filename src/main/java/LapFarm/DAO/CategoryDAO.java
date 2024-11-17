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
}