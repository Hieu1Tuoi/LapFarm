package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_details")  // Tên bảng phù hợp với database
public class OrderDetailsEntity {

    @EmbeddedId
    private OrderDetailId id;

    @MapsId("order")  // Liên kết với trường trong OrderDetailId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderDetail", nullable = false)
    private OrdersEntity order;

    @MapsId("product")  // Liên kết với trường trong OrderDetailId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductOrder", nullable = false)
    private ProductEntity product;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "Price", nullable = false)
    private int price;
    
    

    public OrderDetailsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDetailsEntity(OrderDetailId id, OrdersEntity order, ProductEntity product, int quantity, int price) {
		super();
		this.id = id;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
	}
	// Getters and Setters
    public OrderDetailId getId() { return id; }
    public void setId(OrderDetailId id) { this.id = id; }

    public OrdersEntity getOrder() { return order; }
    public void setOrder(OrdersEntity order) { this.order = order; }

    public ProductEntity getProduct() { return product; }
    public void setProduct(ProductEntity product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    @Override
    public String toString() {
        return "OrderDetailEntity{" +
                "id=" + id +
                ", order=" + (order != null ? order.getIdOrder() : null) +
                ", product=" + (product != null ? product.getIdProduct() : null) +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}