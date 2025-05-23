package LapFarm.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ImageEntity;
import LapFarm.Entity.OrdersEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Utils.SecureUrlUtil;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class ProductDAO {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory factory;
	
    @Autowired
    @Qualifier("sessionFactoryVisitor")
    private SessionFactory factoryVisitor;

	@Transactional("transactionManagerVisitor")
	// Hàm lấy danh sách tất cả sản phẩm
	public List<ProductEntity> getAllProducts() {
		// Lấy session từ factory
		Session session = factoryVisitor.getCurrentSession();

		// Viết câu truy vấn HQL để lấy tất cả sản phẩm
		String hql = "SELECT p FROM ProductEntity p JOIN FETCH p.images"; // Truy vấn tất cả sản phẩm từ bảng
																			// ProductEntity
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		// Trả về danh sách sản phẩm
		return query.list();
	}

	@Transactional("transactionManagerVisitor")
	public List<ProductDTO> getAllProductsDTO() {
		Session session = factoryVisitor.getCurrentSession();

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
					product.getBrand() != null ? product.getBrand().getIdBrand() : null,
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).toList();
	}

	@Transactional("transactionManagerVisitor")
	public Map<Integer, Long> getProductCountByAllCategories(List<CategoryEntity> categories) {
		Session session = factoryVisitor.getCurrentSession();

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

	@Transactional("transactionManagerVisitor")
	public Map<Integer, Long> getProductCountByAllBrands(List<BrandEntity> brands) {
		Session session = factoryVisitor.getCurrentSession();

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
	@Transactional("transactionManagerVisitor")
	public List<ProductDTO> getTop5ProductsByLowestQuantity() {
		Session session = factoryVisitor.getCurrentSession();

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
					product.getBrand() != null ? product.getBrand().getIdBrand() : null,
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).toList();

		return productDTOs;
	}


	
	@Transactional("transactionManagerVisitor")
	public List<ProductDTO> getProductsByCategory(int idCategory) {
		Session session = factoryVisitor.getCurrentSession();

		String hql = "SELECT p FROM ProductEntity p WHERE p.category.idCategory = :idCategory";
			Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("idCategory", idCategory);

		List<ProductEntity> products = query.list();

		// Chuyển đổi từ ProductEntity sang ProductDTO
		return products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl()
					: null;

			ProductDTO productDTO = new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getIdBrand() : null,
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
			String encryptedProductId = "";
			try {
				encryptedProductId = SecureUrlUtil.encrypt(String.valueOf(product.getIdProduct()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			productDTO.setEncryptedId(encryptedProductId);
			return productDTO;
		}).toList();
	}

	@Transactional("transactionManagerVisitor")
	public List<ProductEntity> getProductsByCategoryEn(Integer categoryId) {
		// Lấy session từ factory
		Session session = factoryVisitor.getCurrentSession();

		// Viết câu truy vấn HQL để lấy sản phẩm theo categoryId
		String hql = "SELECT p FROM ProductEntity p JOIN FETCH p.images WHERE p.category.idCategory = :categoryId";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("categoryId", categoryId); // Set parameter cho categoryId
		query.setMaxResults(9); // Giới hạn số lượng sản phẩm trả về, có thể điều chỉnh theo nhu cầu

		// Trả về danh sách sản phẩm
		return query.list();
	}

	@Transactional("transactionManagerVisitor")
	public Long getTotalProductQuantity() {
		// Lấy session từ SessionFactory
		Session session = factoryVisitor.getCurrentSession();

		// Truy vấn HQL để tính tổng số lượng sản phẩm
		String hql = "SELECT SUM(p.quantity) FROM ProductEntity p";
		Long totalQuantity = session.createQuery(hql, Long.class).uniqueResult();

		// Nếu không có sản phẩm nào, trả về 0
		return totalQuantity != null ? totalQuantity : 0L;
	}

	@Transactional("transactionManagerVisitor")
	public List<ProductEntity> getProductsByBrandId(Integer brandId) {
		// Lấy session từ factory
		Session session = factoryVisitor.getCurrentSession();

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

	@Transactional("transactionManagerVisitor")
	public List<ProductDTO> getProductsByBrand(int idBrand) {
		Session session = factoryVisitor.getCurrentSession();

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
					product.getBrand() != null ? product.getBrand().getIdBrand() : null,
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).toList();
	}

	// lấy product theo tên bran
	@Transactional("transactionManagerVisitor")
	public List<ProductDTO> getProductsByBrandName(String nameBrand) {
		Session session = factoryVisitor.getCurrentSession();

		// HQL query để lấy danh sách sản phẩm theo nameBrand
		String hql = "SELECT p FROM ProductEntity p WHERE p.brand.nameBrand = :nameBrand";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("nameBrand", nameBrand);

		// Lấy danh sách sản phẩm từ kết quả query
		List<ProductEntity> products = query.list();

		// Chuyển đổi từ ProductEntity sang ProductDTO
		return products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl()
					: null;

			return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getIdBrand() : null,
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory() != null ? product.getCategory().getIdCategory() : 0, // Lưu ý kiểm tra
																								// category null
					product.getDescription(), product.getQuantity(), product.getDiscount(), product.getOriginalPrice(),
					product.getSalePrice(), product.getState(), image);
		}).toList();
	}

	@Transactional("transactionManagerVisitor")
	public List<ProductDTO> getDataProductPaginates(int start, int end, String searchText, Integer idCategory,
			String priceRange, Integer idBrand) {
		Session session = factoryVisitor.getCurrentSession();

		// Xử lý giá trị priceMin và priceMax từ priceRange
		double priceMin = Double.MIN_VALUE;
		double priceMax = Double.MAX_VALUE;
		if (priceRange != null && !priceRange.isEmpty()) {
			String[] parts = priceRange.split("-");
			if (parts.length == 2) {
				try {
					priceMin = Double.parseDouble(parts[0].trim()) - 100;
					priceMax = Double.parseDouble(parts[1].trim()) + 100;
				} catch (NumberFormatException e) {
					// Nếu parse không thành công, giữ lại giá trị mặc định
				}
			}
		}

		// Tạo phần đầu của câu truy vấn
		StringBuilder hql = new StringBuilder(
				"SELECT p FROM ProductEntity p LEFT JOIN FETCH p.brand b LEFT JOIN FETCH p.category c");

		// Danh sách điều kiện WHERE
		List<String> whereConditions = new ArrayList<>();

		// Lọc theo searchText nếu có
		if (searchText != null && !searchText.trim().isEmpty()) {
			whereConditions.add("LOWER(p.nameProduct) LIKE :searchText");
		}

		// Lọc theo category nếu có
		if (idCategory != null && idCategory != 0) {
			whereConditions.add("p.category.idCategory = :category");
		}

		// Lọc theo priceRange nếu có
		if (priceRange != null && !priceRange.isEmpty()) {
			whereConditions.add("(p.salePrice * (1 - p.discount)) BETWEEN :priceMin AND :priceMax");
		}

		// Lọc theo brand nếu có
		if (idBrand != null && idBrand != 0) {
			whereConditions.add("p.brand.idBrand = :brand");
		}

		// Thêm điều kiện WHERE vào câu truy vấn nếu có
		if (!whereConditions.isEmpty()) {
			hql.append(" WHERE ").append(String.join(" AND ", whereConditions));
		}

		// Tạo truy vấn
		Query<ProductEntity> query = session.createQuery(hql.toString(), ProductEntity.class);

		// Thiết lập tham số cho searchText nếu có
		if (searchText != null && !searchText.trim().isEmpty()) {
			query.setParameter("searchText", "%" + searchText.toLowerCase() + "%");
		}

		// Thiết lập tham số cho category nếu có
		if (idCategory != null && idCategory != 0) {
			query.setParameter("category", idCategory);
		}

		// Thiết lập tham số cho priceMin và priceMax
		if (priceRange != null && !priceRange.isEmpty()) {
			query.setParameter("priceMin", priceMin);
			query.setParameter("priceMax", priceMax);
		}

		// Thiết lập tham số cho brand nếu có
		if (idBrand != null && idBrand != 0) {
			query.setParameter("brand", idBrand);
		}

		// Thiết lập phân trang
		query.setFirstResult(start - 1); // Vị trí bắt đầu (Hibernate tính từ 0)
		query.setMaxResults(end - start + 1); // Số lượng kết quả cần lấy

		// Lấy danh sách ProductEntity
		List<ProductEntity> products = query.list();

		// Chuyển đổi sang ProductDTO
		return products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl()
					: null;

			return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getIdBrand() : null,
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory() != null ? product.getCategory().getIdCategory() : null,
					product.getDescription(), product.getQuantity(), product.getDiscount(), product.getOriginalPrice(),
					product.getSalePrice(), product.getState(), image);
		}).collect(Collectors.toList());
	}

	@Transactional("transactionManagerVisitor")
	public ProductEntity getProductById(int idProduct) {
		Session session = factoryVisitor.getCurrentSession();
		String hql = "SELECT p FROM ProductEntity p JOIN FETCH p.images WHERE p.idProduct = :idProduct";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("idProduct", idProduct);

		return query.uniqueResult(); // Trả về sản phẩm hoặc null nếu không tìm thấy
	}
	
	@Transactional("transactionManagerVisitor")
	public ProductDTO getProductById2(int productId) {
	    // Lấy session từ factory
	    Session session = factoryVisitor.getCurrentSession();

	    // Truy vấn lấy sản phẩm theo ID
	    String hql = "SELECT p FROM ProductEntity p LEFT JOIN FETCH p.images WHERE p.idProduct = :productId";
	    Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
	    query.setParameter("productId", productId);

	    // Lấy sản phẩm từ cơ sở dữ liệu
	    ProductEntity product = query.uniqueResult();

	    if (product != null) {
	        // Chuyển đổi từ ProductEntity sang ProductDTO
	        String image = product.getImages() != null && !product.getImages().isEmpty()
	                ? product.getImages().get(0).getImageUrl()
	                : null;

	        return new ProductDTO(
	            product.getIdProduct(),
	            product.getNameProduct(),
	            product.getBrand() != null ? product.getBrand().getIdBrand() : null,
	            product.getBrand() != null ? product.getBrand().getNameBrand() : null,
	            product.getCategory() != null ? product.getCategory().getNameCategory() : null,
	            product.getCategory() != null ? product.getCategory().getIdCategory() : 0,
	            product.getDescription(),
	            product.getQuantity(),
	            product.getDiscount(),
	            product.getOriginalPrice(),
	            product.getSalePrice(),
	            product.getState(),
	            image
	        );
	    }

	    // Nếu không tìm thấy sản phẩm, trả về null hoặc có thể throw exception tùy vào yêu cầu
	    return null;
	}

	

	@Transactional("transactionManagerVisitor")
	public Map<String, Double> getMinMaxPrices() {
		Session session = factoryVisitor.getCurrentSession();

		// HQL để tính giá trị nhỏ nhất và lớn nhất của SalePrice sau khi áp dụng
		// discount
		String hql = "SELECT MIN(p.salePrice * (1 - p.discount)), MAX(p.salePrice * (1 - p.discount)) "
				+ "FROM ProductEntity p";

		Query<Object[]> query = session.createQuery(hql, Object[].class);
		Object[] result = query.uniqueResult();

		// Tạo Map để trả về kết quả
		Map<String, Double> minMaxPrices = new HashMap<>();
		if (result != null) {
			// Làm tròn kết quả đến 2 chữ số thập phân
			double minPrice = result[0] != null ? Math.round((Double) result[0] * 100.0) / 100.0 : 0.0;
			double maxPrice = result[1] != null ? Math.round((Double) result[1] * 100.0) / 100.0 : 0.0;

			minMaxPrices.put("min", minPrice);
			minMaxPrices.put("max", maxPrice);
		} else {
			// Trường hợp không có sản phẩm
			minMaxPrices.put("min", 0.0);
			minMaxPrices.put("max", 0.0);
		}

		return minMaxPrices;
	}

	@Transactional("transactionManagerVisitor")
	public List<ProductDTO> getRelatedProductsByBrand(int brandId, int excludeProductId, int limit) {
		Session session = factoryVisitor.getCurrentSession();

		// HQL query để lấy sản phẩm cùng brand nhưng không bao gồm sản phẩm hiện tại
		String hql = "SELECT p FROM ProductEntity p "
				+ "WHERE p.brand.idBrand = :brandId AND p.idProduct != :excludeProductId";

		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("brandId", brandId);
		query.setParameter("excludeProductId", excludeProductId);
		query.setMaxResults(limit); // Giới hạn số lượng sản phẩm liên quan

		// Lấy danh sách sản phẩm từ query
		List<ProductEntity> products = query.list();

		// Chuyển đổi từ ProductEntity sang ProductDTO
		return products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl()
					: null;

			return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getIdBrand() : null,
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).toList();
	}

	// Đếm ản phẩm theo idBrand( THANH NHẬT)
	@Transactional("transactionManagerVisitor")
	public Long countProductsByBrand(int brandId) {
		Session session = factoryVisitor.getCurrentSession();

		// HQL query để đếm số lượng sản phẩm theo brand
		String hql = "SELECT COUNT(p) FROM ProductEntity p WHERE p.brand.idBrand = :brandId";

		Query<Long> query = session.createQuery(hql, Long.class);
		query.setParameter("brandId", brandId);

		return query.uniqueResult();
	}

	// Lấy tất cả Products theo idBrand(Thanh Nhật)
	@Transactional("transactionManagerVisitor")
	public List<ProductDTO> getAllProductsByBrand(int idBrand) {
		Session session = factoryVisitor.getCurrentSession();

		// HQL query để lấy tất cả sản phẩm thuộc thương hiệu
		String hql = "SELECT p FROM ProductEntity p WHERE p.brand.idBrand = :idBrand";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("idBrand", idBrand);

		// Lấy danh sách sản phẩm từ query
		List<ProductEntity> products = query.list();

		// Chuyển đổi từ ProductEntity sang ProductDTO
		return products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl()
					: null;

			return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getIdBrand() : null,
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).toList();
	}

	// Tìm sản phẩm theo idProduct để thêm giỏ hàng (Thanh Nhật)
	@Transactional("transactionManagerVisitor")
	public ProductDTO findProductDTOById(int idProduct) {
		// Lấy session từ factory
		Session session = factoryVisitor.getCurrentSession();

		// HQL để truy vấn sản phẩm theo idProduct
		String hql = "SELECT p FROM ProductEntity p LEFT JOIN FETCH p.images LEFT JOIN FETCH p.brand LEFT JOIN FETCH p.category WHERE p.idProduct = :idProduct";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("idProduct", idProduct);

		// Lấy sản phẩm từ cơ sở dữ liệu
		ProductEntity product = query.uniqueResult();

		// Kiểm tra nếu sản phẩm không tồn tại, trả về null
		if (product == null) {
			return null;
		}

		// Lấy ảnh đầu tiên nếu có
		String image = product.getImages() != null && !product.getImages().isEmpty()
				? product.getImages().get(0).getImageUrl()
				: null;

		// Chuyển đổi từ ProductEntity sang ProductDTO
		return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
				product.getBrand() != null ? product.getBrand().getIdBrand() : null,
				product.getBrand() != null ? product.getBrand().getNameBrand() : null,
				product.getCategory() != null ? product.getCategory().getNameCategory() : null,
				product.getCategory() != null ? product.getCategory().getIdCategory() : null, product.getDescription(),
				product.getQuantity(), product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(),
				product.getState(), image);
	}

	// Phương thức chuyển đổi từ ProductEntity sang ProductDTO
	public ProductDTO mapToProductDTO(ProductEntity product) {
		// Kiểm tra và lấy ảnh đầu tiên (nếu có)
		String image = product.getImages() != null && !product.getImages().isEmpty()
				? product.getImages().get(0).getImageUrl()
				: null;

		// Trả về một đối tượng ProductDTO đã được tạo từ ProductEntity
		return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
				product.getBrand() != null ? product.getBrand().getIdBrand() : null,
				product.getBrand() != null ? product.getBrand().getNameBrand() : null,
				product.getCategory() != null ? product.getCategory().getNameCategory() : null,
				product.getCategory() != null ? product.getCategory().getIdCategory() : null, product.getDescription(),
				product.getQuantity(), product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(),
				product.getState(), image);
	}

	@Transactional("transactionManagerVisitor")
	public Map<String, Object> getRatingSummary(int productId) {
		Session session = factoryVisitor.getCurrentSession();

		// Tính điểm trung bình và số lượng đánh giá cho từng mức rating
		String hql = "SELECT AVG(r.rating), " + "SUM(CASE WHEN r.rating = 5 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN r.rating = 4 THEN 1 ELSE 0 END), " + "SUM(CASE WHEN r.rating = 3 THEN 1 ELSE 0 END), "
				+ "SUM(CASE WHEN r.rating = 2 THEN 1 ELSE 0 END), " + "SUM(CASE WHEN r.rating = 1 THEN 1 ELSE 0 END) "
				+ "FROM ReviewEntity r WHERE r.product.idProduct = :productId";

		Object[] result = session.createQuery(hql, Object[].class).setParameter("productId", productId).uniqueResult();

		// Kết quả
		Map<String, Object> ratingSummary = new HashMap<>();
		ratingSummary.put("average", result[0] != null ? Math.round((Double) result[0] * 10.0) / 10.0 : 0);
		ratingSummary.put("fiveStar", result[1] != null ? ((Long) result[1]) : 0L);
		ratingSummary.put("fourStar", result[2] != null ? ((Long) result[2]) : 0L);
		ratingSummary.put("threeStar", result[3] != null ? ((Long) result[3]) : 0L);
		ratingSummary.put("twoStar", result[4] != null ? ((Long) result[4]) : 0L);
		ratingSummary.put("oneStar", result[5] != null ? ((Long) result[5]) : 0L);

		return ratingSummary;
	}

	@Transactional("transactionManagerVisitor")
	public List<Map<String, Object>> getAllRatingSummaries(List<Integer> productIds) {
		List<Map<String, Object>> ratingSummaries = new ArrayList<>();

		for (Integer productId : productIds) {
			Map<String, Object> ratingSummary = getRatingSummary(productId);
			ratingSummary.put("productId", productId); // Thêm productId vào mỗi rating summary
			ratingSummaries.add(ratingSummary);
		}

		return ratingSummaries;
	}

	@Transactional("transactionManager")
	public boolean updateProduct(ProductEntity product) {
		// Sử dụng try-with-resources để tự động đóng session khi hoàn thành
		try (Session session = factory.openSession()) {
			// Bắt đầu transaction
			Transaction transaction = session.beginTransaction();
			try {
				// Cập nhật sản phẩm
				session.update(product);
				// Commit transaction nếu không có lỗi
				transaction.commit();
				return true;
			} catch (Exception e) {
				// Nếu có lỗi xảy ra, rollback transaction
				if (transaction != null) {
					transaction.rollback();
				}
				// Ghi lỗi chi tiết vào log
				logError("Error updating product. Rolling back transaction.", e);
				return false;
			}
		} catch (Exception e) {
			// Ghi lỗi nếu không thể tạo session
			logError("Error opening session for updating product.", e);
			return false;
		}
	}

	private void logError(String message, Exception e) {
		// In lỗi ra log hoặc console, có thể thay thế bằng thư viện logging chuyên
		// nghiệp (SLF4J, Log4j, v.v.)
		System.err.println(message);
		e.printStackTrace();
	}
	
	@Transactional("transactionManagerVisitor")
	public ProductEntity findProductById(int productId) {
		Session session = factoryVisitor.getCurrentSession();
		return session.get(ProductEntity.class, productId);
	}

	@Transactional("transactionManager")
	public void addProduct(ProductEntity product) {
		Session session = factory.getCurrentSession();
		try {
			// Lưu đối tượng sản phẩm vào bảng "product"
			session.persist(product); // Sử dụng saveOrUpdate để vừa có thể thêm mới, vừa có thể cập nhật

			// Nếu có ảnh liên quan, lưu các ảnh vào bảng "image"
			for (ImageEntity image : product.getImages()) {
				session.persist(image); // Lưu hoặc cập nhật ảnh liên kết với sản phẩm
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional("transactionManagerVisitor")
	public List<ProductEntity> getProductsByName(String nameProduct) {
		Session session = factoryVisitor.getCurrentSession();

		// HQL để truy vấn sản phẩm theo tên
		String hql = "FROM ProductEntity p WHERE p.nameProduct = :nameProduct";
		Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
		query.setParameter("nameProduct", nameProduct);

		// Trả về danh sách sản phẩm
		return query.list();
	}

	@Transactional("transactionManagerVisitor")
	public boolean checkProductByName(String nameProduct) {
		Session session = factoryVisitor.getCurrentSession();

		// HQL để kiểm tra sự tồn tại của sản phẩm theo tên
		String hql = "SELECT COUNT(p) FROM ProductEntity p WHERE p.nameProduct = :nameProduct";
		Query<Long> query = session.createQuery(hql, Long.class);
		query.setParameter("nameProduct", nameProduct);

		// Kiểm tra nếu số lượng sản phẩm lớn hơn 0 thì có tồn tại, ngược lại là không
		long count = query.uniqueResult();

		return count > 0;
	}
	
	@Transactional("transactionManagerVisitor")
	public List<ProductDTO> searchProducts(String searchQuery) {
	    // Kiểm tra nếu người dùng tìm kiếm theo số
	    boolean isNumeric = searchQuery.matches("^[0-9]+$");

	    // Lấy phiên làm việc hiện tại
	    Session session = factoryVisitor.getCurrentSession();
	    String hql;

	    if (isNumeric) {
	        // Tìm kiếm theo ID (sử dụng LIKE để tìm theo chuỗi số)
	        hql = "SELECT p FROM ProductEntity p WHERE CAST(p.idProduct AS string) LIKE :searchQuery";
	    } else {
	        // Tìm kiếm theo fullName (sử dụng LIKE để tìm kiếm tên chứa chuỗi tìm kiếm)
	        hql = "SELECT p FROM ProductEntity p WHERE p.nameProduct LIKE :searchQuery OR p.brand.nameBrand LIKE :searchQuery";
	    }

	    // Tạo truy vấn
	    Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);

	    // Thiết lập tham số tìm kiếm, thêm dấu % nếu tìm kiếm theo fullName
	    query.setParameter("searchQuery", isNumeric ? "%" + Integer.parseInt(searchQuery) + "%" : "%" + searchQuery + "%");

	    List<ProductEntity> products = query.list();

		// Chuyển đổi từ ProductEntity sang ProductDTO
		return products.stream().map(product -> {
			String image = product.getImages() != null && !product.getImages().isEmpty()
					? product.getImages().get(0).getImageUrl()
					: null;

			return new ProductDTO(product.getIdProduct(), product.getNameProduct(),
					product.getBrand() != null ? product.getBrand().getIdBrand() : null,
					product.getBrand() != null ? product.getBrand().getNameBrand() : null,
					product.getCategory() != null ? product.getCategory().getNameCategory() : null,
					product.getCategory().getIdCategory(), product.getDescription(), product.getQuantity(),
					product.getDiscount(), product.getOriginalPrice(), product.getSalePrice(), product.getState(),
					image);
		}).toList();
	}

}