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
#viewed .container {
    display: flex; /* Sử dụng Flexbox để tạo bố cục linh hoạt */
    flex-wrap: wrap; /* Cho phép các phần tử tự động xuống dòng khi không đủ chỗ */
    gap: 20px; /* Khoảng cách giữa các phần tử */
    justify-content: flex-start; /* Các phần tử sẽ xếp từ trái sang phải */
    padding: 20px;
}

#viewed .container div {
    flex: 0 0 calc(25% - 20px); /* Mỗi sản phẩm chiếm 25% chiều rộng của container, trừ đi khoảng cách */
    text-align: center; /* Căn giữa nội dung trong mỗi sản phẩm */
    padding: 10px;
    border-radius: 4px;
    background-color: #fff;
}

#viewed img {
    max-width: 100%; /* Giữ kích thước hình ảnh linh hoạt trong phần tử */
    height: auto; /* Giữ tỷ lệ khung hình cho hình ảnh */
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

    // Ẩn các phần tử khác nếu không phải tab "viewed"
    const viewedTab = document.getElementById('viewed');
    if (tabId !== 'viewed') {
        viewedTab.style.display = 'none';
    } else {
        viewedTab.style.display = 'block';
    }
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
						<input type="radio" id="male" name="sex" value="Nam"
							${userInfo.sex == 'Nam' ? 'checked' : ''}> <label
							for="male">Nam</label> <input type="radio" id="female" name="sex"
							value="Nữ" ${userInfo.sex == 'Nữ' ? 'checked' : ''}> <label
							for="fermale">Nữ</label>

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
		<h1>Order History</h1>
		<ul>
			<c:forEach var="order" items="${orders}">
				<li>Order ID: ${order.orderId}, Date: ${order.date}, Total:
					$${order.total}</li>
			</c:forEach>
		</ul>
	</div>

	<div id="viewed" class="tab">
		<h1>Sản phẩm đã xem</h1>
		<div class="container">
			<c:if test="${not empty viewedItems}">
				<c:forEach var="item" items="${viewedItems}">
					<div>
						<img src="${item.image}" alt="${item.name}" />
						<p>${item.name}</p>
						<p>Price: ${item.price}</p>
					</div>
				</c:forEach>

				</tbody>
			</c:if>
			<c:if test="${empty viewedItems}">
				<p>Bạn chưa xem sản phẩm nào.</p>
			</c:if>

		</div>
</body>
</html>



<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>