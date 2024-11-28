package LapFarm.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartId implements Serializable {
    @Column(name = "UserId")
    private int userId;

    @Column(name = "ProductId")
    private int productId;

    
    public CartId() {
		super();
	}

	public CartId(int userId, int productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}

	// Getters và Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    // Override equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartId cartId = (CartId) o;
        return userId == cartId.userId && productId == cartId.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}