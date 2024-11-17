package LapFarm.Entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderDetailId implements Serializable {

    private int order;
    private int product;

    // Getters, Setters, equals, hashcode
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public int getProduct() { return product; }
    public void setProduct(int product) { this.product = product; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailId that = (OrderDetailId) o;
        return order == that.order && product == that.product;
    }

    @Override
    public int hashCode() {
        return 31 * order + product;
    }
}