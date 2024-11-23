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
}
