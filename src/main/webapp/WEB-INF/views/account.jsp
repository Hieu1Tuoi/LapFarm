<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title>LapFarm</title>
<!-- Google font -->
<link
	href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700"
	rel="stylesheet">

<!-- Bootstrap -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css" />">

<!-- Slick -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/slick.css" />">
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/slick-theme.css" />">

<!-- nouislider -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/nouislider.min.css" />">

<!-- Font Awesome Icon -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/font-awesome.min.css" />">

<!-- Custom stlylesheet -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->


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
/* Phần thông tin User Profile */

/* Style cho các danh sách */
ul {
	padding-left: 20px;
}

ul li {
	line-height: 1.8;
}
/* Nút "Save changes" hoặc các nút */
button, .btn {
	background-color: #d10000;
	color: #fff;
	border: none;
	padding: 10px 15px;
	border-radius: 4px;
	cursor: pointer;
	font-weight: bold;
	transition: background-color 0.3s;
	margin-left: 12.9%;
}

button:hover, .btn:hover {
	background-color: #a00000;
}

//profile
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

input[type="text"], input[type="date"], select {
	width: 40%; /* Độ rộng của input */
	padding: 8px 10px;
	font-size: 14px;
	border: 1px solid #ccc;
	border-radius: 4px;
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
</head>
<body>
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<h1>Account Overview</h1>
	<ul class="tab-links">
		<li><a href="#profile" onclick="showTab('profile');">Profile</a></li>
		<li><a href="#orders-history"
			onclick="showTab('orders-history');">Order History</a></li>
		<li><a href="#viewed" onclick="showTab('viewed');">Viewed
				Items</a></li>
	</ul>

	<div id="profile" class="tab">
		<div class="container">
			<h1>Thông tin tài khoản</h1>
			<form action="${pageContext.servletContext.contextPath}/profile/update" method="post">
				<div class="form-group">
					<label for="fullname">Họ Tên</label> <input type="text"
						id="fullname" name="fullname" value="${userInfo.fullName}">
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
						id="phone" name="phone" value="${userInfo.phone}">
				</div>
				<div class="form-group">
					<label for="address">Địa chỉ</label> <input type="text"
						id="address" name="address" value="${userInfo.address}">
				</div>
				<div class="form-group">
					<label for="email">Email</label> <input type="text" id="email"
						name="email" value="${ userProfile.email}" readonly> <a
						href="#" class="link">Thay đổi</a>
				</div>
				<div class="form-group">
					<label for="dob">Ngày sinh</label> <input type="date" id="dob"
						name="dob" value="${userInfo.dob}"/>
				</div>
				<button type="submit" class="button">LƯU THAY ĐỔI</button>
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
		<h2>Viewed Items</h2>
		<ul>
			<c:forEach var="item" items="${viewedItems}">
				<li>Item ID: ${item.itemId}, Name: ${item.name}</li>
			</c:forEach>
		</ul>
	</div>
	<!-- FOOTER -->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<!-- /FOOTER -->

	<!-- jQuery Plugins -->
	<script src="<c:url value='/resources/js/jquery.min.js' />"></script>
	<script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/resources/js/slick.min.js' />"></script>
	<script src="<c:url value='/resources/js/nouislider.min.js' />"></script>
	<script src="<c:url value='/resources/js/jquery.zoom.min.js' />"></script>
	<script src="<c:url value='/resources/js/main.js' />"></script>

</body>
</html>
