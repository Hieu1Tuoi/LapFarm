package LapFarm.DAO;

import java.util.List;

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

	    // Truy vấn lấy danh sách OrderDetails và ánh xạ ProductEntity sang ProductDTO
	    String hql = "SELECT o FROM OrderDetailsEntity o LEFT JOIN FETCH o.product WHERE o.product.idProduct = :orderId";
	    Query<OrderDetailsEntity> query = session.createQuery(hql, OrderDetailsEntity.class);
	    query.setParameter("orderId", id);

	    // Lấy danh sách các OrderDetailsEntity từ cơ sở dữ liệu
	    List<OrderDetailsEntity> orderDetails = query.list();

	    // Chuyển đổi từ OrderDetailsEntity sang OrderDetailDTO
	    List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream().map(orderDetail -> {
	        // Lấy ProductEntity từ OrderDetailsEntity
	        ProductEntity product = orderDetail.getProduct();

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
	        return new OrderDetailDTO(productDTO, orderDetail.getQuantity(), orderDetail.getPrice());
	    }).toList();

	    return orderDetailDTOs;
	}


}
