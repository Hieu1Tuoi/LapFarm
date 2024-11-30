<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

	<div class="content-wrapper">

		<!-- /.box-header -->
		<div class="box-body table-responsive no-padding">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>ID Đơn Hàng</th>
						<th>Người Đặt Hàng</th>
						<th>Thời Gian</th>
						<th>Trạng Thái</th>
						<th>Tổng Giá</th>
						<th>Phương thức thanh toán</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="order" items="${orders}">
						<tr>
							<td>${order.orderId}</td>
							<td>${order.userFullname}</td>
							<td>${order.time}</td>
							<td>${order.state}</td>
							<td><fmt:formatNumber value="${order.totalPrice}"
									type="number" groupingUsed="true" /> VNĐ</td>
							<td><c:choose>
									<c:when test="${order.paymentMethod == 0}">
            									Tiền mặt
        									</c:when>
									<c:when test="${order.paymentMethod == 1}">
            									Chuyển khoản VNPAY
        									</c:when>
									<c:otherwise>
            									Không xác định
        									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<!-- /.box-body -->
	</div>
	<!-- /.box -->
	</div>
	<!-- /.box -->

	</section>
	<!-- /.content -->
	</div>
</body>
</html>