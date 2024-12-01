<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LapFarm</title>
<base href="${pageContext.servletContext.contextPath}/">
<!-- Google font -->
<link
	href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700"
	rel="stylesheet">

<!-- Bootstrap -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css" />">

<!-- Login -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/login.css" />">

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
</head>
<body>
	<!-- HEADER -->
	<header>
		<!-- TOP HEADER -->
		<div id="top-header">
			<div class="container">
				<ul class="header-links pull-left">
					<li><a href="#"><i class="fa fa-phone"></i> 0123456789</a></li>
					<li><a href="#"><i class="fa fa-envelope-o"></i>
							nhom8@gmail.com</a></li>
					<li><a href="#"><i class="fa fa-map-marker"></i> 97 Man
							Thiện</a></li>
				</ul>
			</div>
		</div>
		<!-- /TOP HEADER -->

		<!-- MAIN HEADER -->
		<div id="header">
			<!-- container -->
			<div class="container">
				<!-- row -->
				<div class="row">
					<!-- LOGO -->
					<div class="col-md-3">
						<div class="header-logo">
							<a href="<c:url value="/home" />" class="logo"> <img
								src="<c:url value="/resources/img/logo.png" />" alt="">
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- NAVIGATION -->
		<nav id="navigation">
			<!-- container -->
			<div class="container"></div>
			<!-- /container -->
		</nav>
		<!-- /NAVIGATION -->
	</header>
	<!-- /HEADER -->

	<!-- Main content -->
	<div class="content">
		<h1>Mã đơn hàng: ${order.idOrder}</h1>
		<!-- /.box-header -->
		<div class="box-body table-responsive no-padding"
			style="margin-top: 30px">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Hình Ảnh</th>
						<th>Tên Sản Phẩm</th>
						<th>Thương Hiệu</th>
						<th>Giá Bán</th>
						<th>Số Lượng</th>
						<th>Thành tiền</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="od" items="${orderdetail}">
						<h1>${od.salePrice}</h1>
						<tr>
							<td><a href="product-detail/${od.idProduct}"> <img
									width="100" src="<c:url value='${od.image}'/>" alt="">
							</a></td>
							<td><a href="product-detail/${od.idProduct}"
								style="color: black;"> ${od.nameProduct} </a></td>
							<td>${od.brandName}</td>
							<td>
								<!-- Định dạng salePrice --> <fmt:formatNumber
									value="${od.salePrice}" type="number" groupingUsed="true" />
								VNĐ
							</td>
							<td>${od.quantity}</td>
							<td><fmt:formatNumber value="${od.salePrice * od.quantity}"
									type="number" groupingUsed="true" /> VNĐ</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!-- Thêm button "Sửa Trạng Thái" -->
		</div>
		<button>
			<a href="">Quay lại trang chủ</a>
		</button>

		<!-- /.box-body -->
	</div>
	<!-- /.box -->
</body>
</html>