package LapFarm.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orderdetail")
public class OrderDetailEntity {

    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne
    @JoinColumn(name = "OrderDetail", referencedColumnName = "IdOrder", insertable = false, updatable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "ProductOrder", referencedColumnName = "IdProduct", insertable = false, updatable = false)
    private ProductEntity product;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "Price", nullable = false)
    private int price;

    // Getters and Setters
    public OrderDetailId getId() { return id; }
    public void setId(OrderDetailId id) { this.id = id; }

    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }

    public ProductEntity getProduct() { return product; }
    public void setProduct(ProductEntity product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    @Override
    public String toString() {
        return "OrderDetailEntity{" + "order=" + order + ", product=" + product + ", quantity=" + quantity + ", price=" + price + '}';
    }
}