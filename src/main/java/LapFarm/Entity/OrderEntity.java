package LapFarm.Entity;

import java.security.Timestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdOrder")
	private Integer IdOrder;

	@Column(name = "UserOrder", nullable = false)
	private Integer UserOrder;

	@Column(name = "Time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp Time;

	@Column(name = "State", nullable = false, length = 20)
	private String State;

	@Column(name = "TotalPrice", nullable = false)
	private Integer TotalPrice;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdOrder", referencedColumnName = "OrderDetail")

	public Integer getIdOrder() {
		return IdOrder;
	}

	public void setIdOrder(Integer idOrder) {
		IdOrder = idOrder;
	}

	public Integer getUserOrder() {
		return UserOrder;
	}

	public void setUserOrder(Integer userOrder) {
		UserOrder = userOrder;
	}

	public Timestamp getTime() {
		return Time;
	}

	public void setTime(Timestamp time) {
		Time = time;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public Integer getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		TotalPrice = totalPrice;
	}

	@Entity
	@Table(name = "orderdetail")
	public static class OrderDetail {
		@Column(name = "OrderDetail")
		private int OrderDetail;
		@Column(name = "ProductOrder", nullable = false)
		private Integer ProductOrder;
		@Column(name = "Quantity", nullable = false)
		private Integer Quantity;
		@Column(name = "Price", nullable = false)
		private Integer Price;

		public int getOrderDetail() {
			return OrderDetail;
		}

		public void setOrderDetail(int orderDetail) {
			OrderDetail = orderDetail;
		}

		public Integer getProductOrder() {
			return ProductOrder;
		}

		public void setProductOrder(Integer productOrder) {
			ProductOrder = productOrder;
		}

		public Integer getQuantity() {
			return Quantity;
		}

		public void setQuantity(Integer quantity) {
			Quantity = quantity;
		}

		public Integer getPrice() {
			return Price;
		}

		public void setPrice(Integer price) {
			Price = price;
		}

	}

}
