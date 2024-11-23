package LapFarm.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ProductEntity;
import jakarta.transaction.Transactional;

@Repository
public class ProductDAO {

    @Autowired
    private SessionFactory factory;

    // Lấy danh sách tất cả sản phẩm
    @Transactional
    public List<ProductEntity> getAllProducts() {
        Session session = factory.getCurrentSession();
        String hql = "SELECT p FROM ProductEntity p JOIN FETCH p.images"; 
        Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
        query.setMaxResults(9); // Giới hạn kết quả nếu cần
        return query.list();
    }

    // Lấy sản phẩm theo ID
    @Transactional
    public ProductEntity getProductById(Integer id) {
        Session session = factory.getCurrentSession();
        return session.get(ProductEntity.class, id); // Trả về đối tượng sản phẩm theo ID
    }

    // Đếm số lượng sản phẩm theo danh mục
    @Transactional
    public Map<Integer, Long> getProductCountByAllCategories(List<CategoryEntity> categories) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT p.category.idCategory, COUNT(p) FROM ProductEntity p GROUP BY p.category.idCategory";
        List<Object[]> results = session.createQuery(hql).list();

        Map<Integer, Long> countMap = new HashMap<>();
        for (CategoryEntity category : categories) {
            countMap.put(category.getIdCategory(), 0L);
        }

        for (Object[] row : results) {
            Integer idCategory = (Integer) row[0];
            Long count = (Long) row[1];
            countMap.put(idCategory, count);
        }
        return countMap;
    }

    // Đếm số lượng sản phẩm theo thương hiệu
    @Transactional
    public Map<Integer, Long> getProductCountByAllBrands(List<BrandEntity> brands) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT p.brand.idBrand, COUNT(p) FROM ProductEntity p GROUP BY p.brand.idBrand";
        List<Object[]> results = session.createQuery(hql).list();

        Map<Integer, Long> countMap = new HashMap<>();
        for (BrandEntity brand : brands) {
            countMap.put(brand.getIdBrand(), 0L);
        }

        for (Object[] row : results) {
            Integer idBrand = (Integer) row[0];
            Long count = (Long) row[1];
            countMap.put(idBrand, count);
        }
        return countMap;
    }

    // Lấy 5 sản phẩm bán chạy nhất (theo số lượng thấp nhất)
    @Transactional
    public List<ProductDTO> getTop5ProductsByLowestQuantity() {
        Session session = factory.getCurrentSession();
        String hql = "SELECT p FROM ProductEntity p LEFT JOIN FETCH p.images ORDER BY p.quantity ASC";
        Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class).setMaxResults(5);

        List<ProductEntity> products = query.list();

        return products.stream().map(product -> {
            String image = (product.getImages() != null && !product.getImages().isEmpty())
                    ? product.getImages().get(0).getImageUrl() : null;

            return new ProductDTO(
                product.getIdProduct(),
                product.getNameProduct(),
                product.getBrand() != null ? product.getBrand().getNameBrand() : null,
                product.getCategory() != null ? product.getCategory().getNameCategory() : null,
                product.getDescription(),
                product.getQuantity(),
                product.getDiscount(),
                product.getSalePrice(),
                image
            );
        }).toList();
    }

    // Lấy danh sách sản phẩm theo danh mục
    @Transactional
    public List<ProductDTO> getProductsByCategory(Integer idCategory) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT p FROM ProductEntity p WHERE p.category.idCategory = :idCategory";
        Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
        query.setParameter("idCategory", idCategory);

        List<ProductEntity> products = query.list();

        return products.stream().map(product -> {
            String image = (product.getImages() != null && !product.getImages().isEmpty())
                    ? product.getImages().get(0).getImageUrl() : null;

            return new ProductDTO(
                product.getIdProduct(),
                product.getNameProduct(),
                product.getBrand() != null ? product.getBrand().getNameBrand() : null,
                product.getCategory() != null ? product.getCategory().getNameCategory() : null,
                product.getDescription(),
                product.getQuantity(),
                product.getDiscount(),
                product.getSalePrice(),
                image
            );
        }).toList();
    }

    // Lấy danh sách sản phẩm theo thương hiệu
    @Transactional
    public List<ProductEntity> getProductsByBrandId(Integer brandId) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT p FROM ProductEntity p JOIN FETCH p.images WHERE p.brand.idBrand = :brandId";
        Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
        query.setParameter("brandId", brandId).setMaxResults(9);
        return query.list();
    }

    // Lấy sản phẩm theo danh mục (Entity)
    @Transactional
    public List<ProductEntity> getProductsByCategoryEn(Integer categoryId) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT p FROM ProductEntity p JOIN FETCH p.images WHERE p.category.idCategory = :categoryId";
        Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
        query.setParameter("categoryId", categoryId).setMaxResults(9);
        return query.list();
    }

    // Tính tổng số lượng sản phẩm
    @Transactional
    public Long getTotalProductQuantity() {
        Session session = factory.getCurrentSession();
        String hql = "SELECT SUM(p.quantity) FROM ProductEntity p";
        Long totalQuantity = session.createQuery(hql, Long.class).uniqueResult();
        return totalQuantity != null ? totalQuantity : 0L;
    }
}
