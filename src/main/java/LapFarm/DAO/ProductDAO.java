package LapFarm.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	// Hàm lấy danh sách tất cả sản phẩm
	public List<ProductEntity> getAllProducts() {
		// Lấy session từ factory
		Session session = factory.getCurrentSession();

		// Viết câu truy vấn HQL để lấy tất cả sản phẩm
		String hql = "SELECT p FROM ProductEntity p JOIN FETCH p.images"; // Truy vấn tất cả sản phẩm từ bảng
																			// ProductEntity
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		// Trả về danh sách sản phẩm
		return query.list();
	}

	@Transactional
	public List<ProductDTO> getAllProductsDTO() {
		Session session = factory.getCurrentSession();

		// Truy vấn để lấy tất cả các sản phẩm
		String hql = "SELECT p FROM ProductEntity p";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);

		List<ProductEntity> products = query.list();

		// Chuyển đổi từ ProductEntity sang ProductDTO
		return products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl()
					: null;

			return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).toList();
	}

	@Transactional
	public Map<Integer, Long> getProductCountByAllCategories(List<CategoryEntity> categories) {
		Session session = factory.getCurrentSession();

		// HQL đếm số lượng sản phẩm group theo idCategory
		String hql = "SELECT p.category.idCategory, COUNT(p) FROM ProductEntity p GROUP BY p.category.idCategory";
		List<Object[]> results = session.createQuery(hql).list();

		// Khởi tạo Map với giá trị mặc định là 0 cho tất cả danh mục
		Map<Integer, Long> countMap = new HashMap<>();
		for (CategoryEntity category : categories) {
			countMap.put(category.getIdCategory(), 0L); // Mặc định là 0
		}

		// Cập nhật số lượng từ kết quả truy vấn
		for (Object[] row : results) {
			Integer idCategory = (Integer) row[0];
			Long count = (Long) row[1];
			countMap.put(idCategory, count); // Ghi đè nếu có sản phẩm
		}

		return countMap;
	}

	@Transactional
	public Map<Integer, Long> getProductCountByAllBrands(List<BrandEntity> brands) {
		Session session = factory.getCurrentSession();

		// HQL đếm số lượng sản phẩm group theo idBrand
		String hql = "SELECT p.brand.idBrand, COUNT(p) FROM ProductEntity p GROUP BY p.brand.idBrand";
		List<Object[]> results = session.createQuery(hql).list();

		// Khởi tạo Map với giá trị mặc định là 0 cho tất cả thương hiệu
		Map<Integer, Long> countMap = new HashMap<>();
		for (BrandEntity brand : brands) {
			countMap.put(brand.getIdBrand(), 0L); // Mặc định là 0
		}

		// Cập nhật số lượng từ kết quả truy vấn
		for (Object[] row : results) {
			Integer idBrand = (Integer) row[0];
			Long count = (Long) row[1];
			countMap.put(idBrand, count); // Ghi đè nếu có sản phẩm
		}

		return countMap;
	}

	// TOP SELLING
	@Transactional
	public List<ProductDTO> getTop5ProductsByLowestQuantity() {
		Session session = factory.getCurrentSession();

		// Truy vấn lấy sản phẩm cùng với ảnh (dùng JOIN FETCH nếu cần)
		String hql = "SELECT p FROM ProductEntity p LEFT JOIN FETCH p.images ORDER BY p.quantity ASC";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setMaxResults(5);

		// Lấy danh sách các sản phẩm từ cơ sở dữ liệu
		List<ProductEntity> products = query.list();

		// Chuyển đổi từ ProductEntity sang ProductDTO
		List<ProductDTO> productDTOs = products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl() // Lấy ảnh đầu tiên nếu có
					: null;

			// Chuyển đổi thông tin từ ProductEntity sang ProductDTO
			return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).toList();

		return productDTOs;
	}

	@Transactional
	public List<ProductDTO> getProductsByCategory(int idCategory) {
		Session session = factory.getCurrentSession();

		String hql = "SELECT p FROM ProductEntity p WHERE p.category.idCategory = :idCategory";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("idCategory", idCategory);

		List<ProductEntity> products = query.list();

		// Chuyển đổi từ ProductEntity sang ProductDTO
		return products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl()
					: null;

			return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).toList();
	}

	@Transactional
	public List<ProductEntity> getProductsByCategoryEn(Integer categoryId) {
		// Lấy session từ factory
		Session session = factory.getCurrentSession();

		// Viết câu truy vấn HQL để lấy sản phẩm theo categoryId
		String hql = "SELECT p FROM ProductEntity p JOIN FETCH p.images WHERE p.category.idCategory = :categoryId"; // Truy
																													// vấn
																													// sản
																													// phẩm
																													// theo
																													// danh
																													// mục
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("categoryId", categoryId); // Set parameter cho categoryId
		query.setMaxResults(9); // Giới hạn số lượng sản phẩm trả về, có thể điều chỉnh theo nhu cầu

		// Trả về danh sách sản phẩm
		return query.list();
	}

	@Transactional
	public Long getTotalProductQuantity() {
		// Lấy session từ SessionFactory
		Session session = factory.getCurrentSession();

		// Truy vấn HQL để tính tổng số lượng sản phẩm
		String hql = "SELECT SUM(p.quantity) FROM ProductEntity p";
		Long totalQuantity = session.createQuery(hql, Long.class).uniqueResult();

		// Nếu không có sản phẩm nào, trả về 0
		return totalQuantity != null ? totalQuantity : 0L;
	}

	@Transactional
	public List<ProductEntity> getProductsByBrandId(Integer brandId) {
		// Lấy session từ factory
		Session session = factory.getCurrentSession();

		// Viết câu truy vấn HQL để lấy sản phẩm theo brandId
		String hql = "SELECT p FROM ProductEntity p JOIN FETCH p.images WHERE p.brand.idBrand = :brandId";

		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);

		// Gắn giá trị tham số cho brandId
		query.setParameter("brandId", brandId);

		// Giới hạn số lượng kết quả trả về (nếu cần)
		query.setMaxResults(9);

		// Trả về danh sách sản phẩm
		return query.list();
	}

	@Transactional
	public List<ProductDTO> getProductsByBrand(int idBrand) {
		Session session = factory.getCurrentSession();

		// HQL query để lấy danh sách sản phẩm theo idBrand
		String hql = "SELECT p FROM ProductEntity p WHERE p.brand.idBrand = :idBrand";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("idBrand", idBrand);

		// Lấy danh sách sản phẩm từ kết quả query
		List<ProductEntity> products = query.list();

		// Chuyển đổi từ ProductEntity sang ProductDTO
		return products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl()
					: null;

			return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).toList();
	}

	@Transactional
	public List<ProductDTO> getDataProductPaginates(int start, int end, String searchText, int category) {
		Session session = factory.getCurrentSession();

		// HQL để lấy danh sách ProductEntity với điều kiện lọc nếu có searchText và
		// category
		String hql = "SELECT p FROM ProductEntity p LEFT JOIN FETCH p.brand b LEFT JOIN FETCH p.category c"
				+ (searchText != null && !searchText.trim().isEmpty() ? " WHERE LOWER(p.nameProduct) LIKE :searchText"
						: "")
				+ (category != 0
						? (searchText != null && !searchText.trim().isEmpty() ? " AND" : " WHERE")
								+ " p.category.idCategory = :category"
						: "");

		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);

		// Thiết lập giá trị cho tham số searchText nếu có
		if (searchText != null && !searchText.trim().isEmpty()) {
			query.setParameter("searchText", "%" + searchText.toLowerCase() + "%");
		}

		// Thiết lập giá trị cho tham số category nếu có
		if (category != 0) {
			query.setParameter("category", category);
		}

		// Thiết lập phân trang
		query.setFirstResult(start - 1); // Vị trí bắt đầu (Hibernate tính từ 0)
		query.setMaxResults(end - start + 1); // Số lượng kết quả cần lấy

		// Lấy danh sách ProductEntity
		List<ProductEntity> products = query.list();

		// Chuyển đổi sang ProductDTO
		return products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl() // Lấy ảnh đầu tiên nếu có
					: null;

			return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).collect(Collectors.toList());
	}

	@Transactional
	public ProductEntity getProductById(int idProduct) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT p FROM ProductEntity p JOIN FETCH p.images WHERE p.idProduct = :idProduct";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("idProduct", idProduct);

		return query.uniqueResult(); // Trả về sản phẩm hoặc null nếu không tìm thấy
	}

}