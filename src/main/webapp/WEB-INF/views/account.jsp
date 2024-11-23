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
#profile img {
	border-radius: 50%;
	border: 2px solid #ddd;
	margin-top: 10px;
}

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
}

button:hover, .btn:hover {
	background-color: #a00000;
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
		<li><a href="#address" onclick="showTab('address');">Addresses</a></li>
		<li><a href="#orders-history"
			onclick="showTab('orders-history');">Order History</a></li>
		<li><a href="#viewed" onclick="showTab('viewed');">Viewed
				Items</a></li>
	</ul>

	<div id="profile" class="tab">
		<h2>User Profile</h2>
		<p>Name: ${userInfo.fullname}</p>
		<p>Email: ${userProfile.email}</p>
		<p>Date of Birth: ${userInfo.dob}</p>
		<p>Sex: ${userInfo.sex}</p>
		<p>Phone: ${userInfo.phone}</p>
		<img src="${userInfor.avatar}" alt="Avatar"
			style="width: 100px; height: 100px;">
	</div>

	<div id="addresses" class="tab">
		<h2>User Addresses</h2>
		<ul>
			<c:forEach var="address" items="${addresses}">
				<li>${address}</li>
			</c:forEach>
		</ul>
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
