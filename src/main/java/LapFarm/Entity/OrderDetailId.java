package LapFarm.Entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderDetailId implements Serializable {

    private int order;
    private int product;

    // Getters and Setters
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public int getProduct() { return product; }
    public void setProduct(int product) { this.product = product; }
    
    

    public OrderDetailId() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailId that = (OrderDetailId) o;
        return order == that.order && product == that.product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}