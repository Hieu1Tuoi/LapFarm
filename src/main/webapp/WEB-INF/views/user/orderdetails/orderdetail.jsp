<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>
<style>
/* Đặt CSS cho toàn bộ container */
/* Đặt CSS cho toàn bộ container */
body {
	font-family: 'Montserrat', sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f8f9fa;
}

/* Thông tin tiêu đề */
h1 {
	font-size: 24px;
	font-weight: bold;
	color: #d10024;
	margin: 20px;
	margin-left: 70px;
}

/* Tab navigation */
.tab-links {
	list-style: none;
	margin: 0;
	padding: 0;
	display: flex;
	border-bottom: 1px solid #ddd;
	background-color: #f8f9fa;
}

.tab-links li {
	margin: 0;
	margin-left: 50px;
}

.tab-links a {
	display: block;
	padding: 15px 20px;
	text-decoration: none;
	color: #333;
	font-weight: 500;
	transition: background-color 0.3s, color 0.3s;
}

.tab-links a:hover, .tab-links .active a {
	background-color: #d10024;
	color: #fff;
}

/* Nội dung tab */
.tab {
	display: none;
	padding: 20px;
	background-color: #fff;
	border: 1px solid #ddd;
	border-radius: 4px;
	margin: 20px;
}

.tab.active {
	display: block;
}

/* Thiết kế bảng lịch sử đơn hàng */
.order-history-table {
	width: 100%;
	border-collapse: collapse;
	margin: 20px 0;
	font-size: 16px;
	text-align: left;
	background-color: #fff;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
	overflow: hidden;
}

.order-history-table thead {
	background-color: #d10024;
	color: #fff;
	text-transform: uppercase;
	font-weight: bold;
}

.order-history-table th, .order-history-table td {
	padding: 12px 15px;
	border-bottom: 1px solid #ddd;
}

.order-history-table th {
	text-align: center;
}

.order-history-table tbody tr:hover {
	background-color: #f9f9f9;
}

.order-history-table td {
	text-align: center;
}

.order-history-table a.btn-detail {
	display: inline-block;
	background-color: #d10024;
	color: #fff;
	padding: 8px 12px;
	border-radius: 4px;
	text-decoration: none;
	font-weight: bold;
	transition: background-color 0.3s;
}

.order-history-table a.btn-detail:hover {
	background-color: #a00000;
}

/* Cải tiến độ rộng và các yếu tố trong phần thông tin giỏ hàng */
.profile-container {
	max-width: 800px;
	margin: 50px auto;
	padding: 20px;
	background: white;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.form-group {
	display: flex;
	align-items: center;
	margin-bottom: 15px;
}

label {
	width: 30%;
	margin-right: 20px;
	text-align: right;
}

input[type="radio"] {
	margin-right: 5px;
}

.link {
	color: #007bff;
	text-decoration: none;
	margin-left: 10px;
	font-size: 14px;
}

.link:hover {
	text-decoration: underline;
}

/* Điều chỉnh cho responsive (hiển thị tốt trên các thiết bị nhỏ) */
@media ( max-width : 768px) {
	.tab-links {
		flex-direction: column;
	}
	.tab-links li {
		margin-left: 0;
	}
	.profile-container {
		width: 100%;
		padding: 15px;
	}
	.order-history-table th, .order-history-table td {
		font-size: 14px;
	}
	.order-history-table img {
		max-width: 100px;
	}
}

/* Khi duyệt qua các phần tử, đổi màu nền cho các mục trong giỏ hàng */
#viewed .container div {
	flex: 0 0 calc(50% - 20px);
	padding: 10px;
	border-radius: 4px;
	background-color: #fff;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

#viewed img {
	max-width: 100%;
	height: auto;
}

/* Các tab và chi tiết đơn hàng */
#viewed .container {
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
	justify-content: flex-start;
	padding: 20px;
}

#order-detail {
	max-width: 1475px; /* Tăng chiều rộng tối đa lên 15% */
	margin: 0 auto; /* Căn giữa thẻ */
	padding: 20px; /* Thêm khoảng cách bên trong */
	background-color: #fff; /* Màu nền trắng */
	border-radius: 8px; /* Viền bo tròn */
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Thêm bóng đổ cho thẻ */
}
</style>
<script>
function showTab(tabId) {
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => {
        tab.classList.remove('active');
    });

    const links = document.querySelectorAll('.tab-links a');
    links.forEach(link => {
        link.parentNode.classList.remove('active');
    });

    const activeTab = document.getElementById(tabId);
    activeTab.classList.add('active');

    const activeLink = document.querySelector(`a[href="#${tabId}"]`);
    activeLink.parentNode.classList.add('active');

    // Cập nhật URL hash
    window.location.hash = tabId;

    // Ẩn các phần tử khác nếu không phải tab "viewed" hoặc tab mới
    const viewedTab = document.getElementById('viewed');
    const orderDetailTab = document.getElementById('order-detail');
    if (tabId !== 'viewed' && tabId !== 'order-detail') {
        viewedTab.style.display = 'none';
        orderDetailTab.style.display = 'none';
    } else {
        if (tabId === 'viewed') {
            viewedTab.style.display = 'block';
        } else {
            orderDetailTab.style.display = 'block';
        }
    }
}

// Mở tab mặc định
document.addEventListener('DOMContentLoaded', function () {
    const currentHash = window.location.hash.substring(1) || 'profile'; // Loại bỏ dấu "#" nếu có
    showTab(currentHash);
});

document.addEventListener('DOMContentLoaded', function () {
    const orderDetails = document.querySelectorAll('.orderdetail');

    // Duyệt qua từng thẻ
    orderDetails.forEach(order => {
      // Gắn sự kiện click cho từng thẻ
      order.addEventListener('click', function () {
        // Kiểm tra nếu thẻ có id
        const id = this.id || null;
        if (id) {
          // Chuyển hướng đến URL "product-detail/" + id
          window.location.href = `product-detail/` + id;
        } else {
          console.error('Thẻ không có id:', this);
        }
      });
    });
});

/ Hàm hiển thị thông báo "Thêm thành công"
function showSuccessMessage(event) {
    event.preventDefault(); // Ngừng gửi form để không tải lại trang
    // Hiển thị thông báo
    var successMessage = document.getElementById('success-message');
    successMessage.style.display = 'block';
    
    // Tự động ẩn thông báo sau 3 giây
    setTimeout(function() {
        successMessage.style.display = 'none';
    }, 3000);

    // Gửi form sau khi hiển thị thông báo
    event.target.submit();
}
</script>
<body>
	<div id="order-detail">
		<h1>Chi tiết đơn hàng</h1>
		<!-- Thêm nội dung chi tiết đơn hàng tại đây -->
		<c:if test="${not empty orderDetail}">
			<table class="order-history-table">
				<thead>
					<tr>
						<th>Hình ảnh</th>
						<th>Tên sản phẩm</th>
						<th>Danh mục</th>
						<th>Thương hiệu</th>
						<th>Số lượng</th>
						<th>Đơn giá</th>
						<th>Thành tiền</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${orderDetail}">
						<tr id="${item.product.idProduct}" class="orderdetail">
							<td><a href="product-detail/${item.product.idProduct}"><img
									src="${item.product.image} " " style="width: 40%" /></td>
							<td><a href="product-detail/${item.product.idProduct}">${item.product.nameProduct}</td>
							<td>${item.product.categoryName}</td>
							<td>${item.product.brandName}</td>
							<td>${item.quantity}</td>
							<td><fmt:formatNumber value="${item.price}" type="currency"
									currencySymbol="₫" /></td>
							<td><fmt:formatNumber value="${item.price * item.quantity}"
									type="currency" currencySymbol="₫" /></td>
							<td>
								<form action="addCart/${item.product.idProduct}" method="GET"
									onsubmit="showSuccessMessage(event)">
									<button type="submit" class="add-to-cart-btn">
										<i class="fa fa-shopping-cart"></i> Thêm lại giỏ hàng
									</button>
								</form>
							</td>

						</tr>
					</c:forEach>
					<tr>
						<td colspan="6" style="text-align: right; font-weight: bold;">Tổng
							giá:</td>
						<td><strong><fmt:formatNumber value="${totalPrice}"
									type="currency" currencySymbol="₫" /> </strong></td>
					</tr>
					<div id="success-message"
						style="display: none; padding: 10px; margin-top: 20px; background-color: #28a745; color: white; text-align: center; border-radius: 5px;">
						Thêm sản phẩm vào giỏ hàng thành công!</div>

				</tbody>
			</table>
		</c:if>
		<c:if test="${empty orderDetail}">
			<p>Không có thông tin chi tiết cho đơn hàng này.</p>
		</c:if>
	</div>


</body>
</html>



<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>