<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>
<style>
/* Đặt CSS cho toàn bộ container */
body {
	font-family: 'Montserrat', sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f8f9fa;
}

h1 {
	font-size: 24px;
	font-weight: bold;
	color: #d10000;
	margin: 20px;
	margin-left: 50px;
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
	margin-left: 22px;
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
	background-color: #d10000;
	color: #fff;
}

/* Nội dung tab */
.tab {
	display: none; /* Ẩn toàn bộ tab ban đầu */
	padding: 20px;
	background-color: #fff;
	border: 1px solid #ddd;
	border-radius: 4px;
	margin: 20px;
}

.tab.active {
	display: block; /* Chỉ hiện tab đang chọn */
}

profile
.container {
	max-width: 600px;
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
	width: 10%; /* Chiều rộng cố định của label */
	margin-right: 30px; /* Khoảng cách giữa label và input */
	text-align: right; /* Căn lề phải */
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
</style>
<script>
function showTab(tabId) {
    // Ẩn toàn bộ tab
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => {
        tab.classList.remove('active');
    });

    // Bỏ class "active" khỏi các link
    const links = document.querySelectorAll('.tab-links a');
    links.forEach(link => {
        link.parentNode.classList.remove('active');
    });

    // Hiển thị tab đang được chọn
    document.getElementById(tabId).classList.add('active');

    // Thêm class "active" vào link tương ứng
    const activeLink = document.querySelector(`a[href="#${tabId}"]`);
    activeLink.parentNode.classList.add('active');

    // Cập nhật URL hash
    window.location.hash = tabId; // Cập nhật URL
}

// Mở tab mặc định
document.addEventListener('DOMContentLoaded', function () {
    const currentHash = window.location.hash.substring(1) || 'profile'; // Loại bỏ dấu "#" nếu có
    showTab(currentHash);
});
</script>
<body>
	<h1>Thông tin tài khoản</h1>
	<ul class="tab-links">
		<li><a href="account#profile" onclick="showTab('profile');">Thông
				tin cá nhân</a></li>
		<li><a href="account#orders-history"
			onclick="showTab('orders-history');">Đơn Hàng</a></li>
		<li><a href="account#viewed" onclick="showTab('viewed');">Sản
				phẩm đã xem</a></li>
	</ul>

	<div id="profile" class="tab">
		<div class="container">
			<h1>Thông tin tài khoản</h1>
			<form
				action="${pageContext.servletContext.contextPath}/profile/update"
				method="post">
				<div class="form-group">
					<label for="fullname">Họ Tên</label> <input type="text"
						id="fullname" name="fullname"
						style="width: 40%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
						value="${userInfo.fullName}">
				</div>
				<div class="form-group">
					<label>Giới tính</label>
					<div>
						<input type="radio" id="male" name="sex" value="Nam"> <label
							for="male" style="display: inline">Nam</label> <input
							type="radio" id="female" name="sex" value="Nữ"> <label
							for="female" style="display: inline;">Nữ</label>
					</div>
				</div>
				<div class="form-group">
					<label for="phone">Số điện thoại</label> <input type="text"
						id="phone" name="phone"
						style="width: 40%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
						value="${userInfo.phone}">
				</div>
				<div class="form-group">
					<label for="address">Địa chỉ</label> <input type="text"
						id="address" name="address"
						style="width: 40%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
						value="${userInfo.address}">
				</div>
				<div class="form-group">
					<label for="email">Email</label> <input type="text" id="email"
						name="email"
						style="width: 40%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
						value="${ userProfile.email}" readonly> <a href="#"
						class="link">Thay đổi</a>
				</div>
				<div class="form-group">
					<label for="dob">Ngày sinh</label> <input type="date" id="dob"
						name="dob"
						style="width: 40%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
						value="${userInfo.dob}" />
				</div>
				<button type="submit" class="save-button profile-save-button"
					style="background-color: #a00000; background-color: #d10000; color: #fff; border: none; padding: 10px 15px; border-radius: 4px; cursor: pointer; font-weight: bold; transition: background-color 0.3s; margin-left: 12.9%;">LƯU
					THAY ĐỔI</button>
			</form>
		</div>
	</div>

	<div id="orders-history" class="tab">
		<h2>Order History</h2>
		<ul>
			<c:forEach var="order" items="${orders}">
				<li>Order ID: ${order.orderId}, Date: ${order.date}, Total:
					$${order.total}</li>
			</c:forEach>
		</ul>
	</div>

	<div id="viewed" class="tab">
		<h2>Sản phẩm đã xem</h2>
		<c:if test="${empty viewedItems }">
			<p>Bạn chưa xem sản phẩm nào</p>
		</c:if>
		<c:if test="${not empty viewedItems}">
			<div class="viewed-items">
				<c:forEach var="item" items="${viewedItems}">
					<div class="viewed-item"
						style="display: flex; align-items: center; margin-bottom: 15px;">
						<img src="${item.image}" alt="${item.name}"
							style="width: 100px; height: 100px; margin-right: 20px;">
						<div>
							<h3 style="margin: 0; font-size: 18px;">${item.name}</h3>
							<p style="margin: 0; color: #555;">Giá: ${item.price} VND</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>

</body>
</html>



<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>