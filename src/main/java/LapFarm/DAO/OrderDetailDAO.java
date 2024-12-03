package LapFarm.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import LapFarm.DTO.OrderDetailDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.OrderDetailsEntity;
import LapFarm.Entity.ProductEntity;

@Repository
public class OrderDetailDAO {
	
	@Autowired
    private SessionFactory factory;
	
	@Transactional
	public List<OrderDetailDTO> getOrderDetailById(int id) {
	    Session session = factory.getCurrentSession();

	    // Truy vấn lấy danh sách OrderDetailsEntity theo idOrder
	    String hqlOrderDetails = "FROM OrderDetailsEntity o WHERE o.order.idOrder = :orderId";
	    Query<OrderDetailsEntity> queryOrderDetails = session.createQuery(hqlOrderDetails, OrderDetailsEntity.class);
	    queryOrderDetails.setParameter("orderId", id);

	    // Lấy danh sách các OrderDetailsEntity từ cơ sở dữ liệu
	    List<OrderDetailsEntity> orderDetails = queryOrderDetails.list();

	    // Nếu không có OrderDetailsEntity nào, trả về danh sách rỗng
	    if (orderDetails.isEmpty()) {
	        return new ArrayList<>();
	    }

	    // Lấy danh sách idProduct từ OrderDetailsEntity
	    List<Integer> productIds = orderDetails.stream()
	                                           .map(orderDetail -> orderDetail.getProduct().getIdProduct())
	                                           .distinct()
	                                           .toList();

	    // Truy vấn danh sách ProductEntity dựa trên idProduct
	    String hqlProducts = "FROM ProductEntity p WHERE p.idProduct IN (:productIds)";
	    Query<ProductEntity> queryProducts = session.createQuery(hqlProducts, ProductEntity.class);
	    queryProducts.setParameterList("productIds", productIds);

	    // Lấy danh sách ProductEntity
	    List<ProductEntity> products = queryProducts.list();

	    // Tạo một Map để ánh xạ idProduct sang ProductEntity
	    Map<Integer, ProductEntity> productMap = products.stream()
	                                                     .collect(Collectors.toMap(ProductEntity::getIdProduct, product -> product));

	    // Chuyển đổi từ OrderDetailsEntity sang OrderDetailDTO
	    List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream().map(orderDetail -> {
	        // Lấy ProductEntity từ Map dựa trên idProduct
	        ProductEntity product = productMap.get(orderDetail.getProduct().getIdProduct());

	        // Chuyển đổi ProductEntity sang ProductDTO
	        ProductDTO productDTO = new ProductDTO(
	            product.getIdProduct(), 
	            product.getNameProduct(),
	            product.getBrand() != null ? product.getBrand().getIdBrand() : null,
	            product.getBrand() != null ? product.getBrand().getNameBrand() : null,
	            product.getCategory() != null ? product.getCategory().getNameCategory() : null,
	            product.getCategory() != null ? product.getCategory().getIdCategory() : 0,  // Lấy idCategory nếu có
	            product.getDescription(), 
	            product.getQuantity(),
	            product.getDiscount(),
	            product.getOriginalPrice(),
	            product.getSalePrice(),
	            product.getState(),
	            product.getImages() != null && !product.getImages().isEmpty() ? product.getImages().get(0).getImageUrl() : null // Lấy ảnh đầu tiên nếu có
	        );

	        // Chuyển đổi thông tin từ OrderDetailsEntity sang OrderDetailDTO
	        return new OrderDetailDTO(
	            orderDetail.getOrder().getIdOrder(),
	            productDTO,
	            orderDetail.getQuantity(),
	            orderDetail.getPrice()
	        );
	    }).toList();

	    return orderDetailDTOs;
	}



}
