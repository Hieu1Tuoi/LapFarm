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
					<li><a href="#"><i class="fa fa-phone"></i> +021-95-51-84</a></li>
					<li><a href="#"><i class="fa fa-envelope-o"></i>
							vailonluon@email.com</a></li>
					<li><a href="#"><i class="fa fa-map-marker"></i> 1734
							Stonecoal Road</a></li>
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

	<!-- Login -->
	<div id="login" class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="login-form">
						<h2 class="text-center">Đăng Nhập</h2>
						<form action="<c:url value="/login" />" method="post">
							<div class="form-group">
								<label for="email">Email:</label> <input type="email"
									class="form-control" id="email" name="email" value="${email}"
									placeholder="Nhập email của bạn" required>
							</div>
							<div class="form-group">
								<label for="password">Mật khẩu:</label> <input type="password"
									class="form-control" id="password" name="password"
									value="${pw}" placeholder="Nhập mật khẩu của bạn" required>
								<a href="<c:url value="/forgotpassword" />">Quên mật khẩu?</a>

							</div>
							<div class="form-group">
								<strong style="color: red;">${warning}</strong>
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-primary btn-block">Đăng
									Nhập</button>
							</div>
							<div class="text-center">
								Bạn chưa có tài khoản? <a href="<c:url value="/signup" />">Đăng
									ký ngay!</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /Login -->

	<!-- FOOTER -->
	<footer id="footer">
		<!-- top footer -->
		<div class="section">
			<!-- container -->
			<div class="container">
				<!-- row -->
				<div class="row">
					<div class="col-md-3 col-xs-6">
						<div class="footer">
							<h3 class="footer-title">About Us</h3>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
								sed do eiusmod tempor incididunt ut.</p>
							<ul class="footer-links">
								<li><a href="#"><i class="fa fa-map-marker"></i>1734
										Stonecoal Road</a></li>
								<li><a href="#"><i class="fa fa-phone"></i>+021-95-51-84</a></li>
								<li><a href="#"><i class="fa fa-envelope-o"></i>email@email.com</a></li>
							</ul>
						</div>
					</div>

					<div class="col-md-3 col-xs-6">
						<div class="footer">
							<h3 class="footer-title">Categories</h3>
							<ul class="footer-links">
								<li class="active"><a href="#">Home</a></li>
								<li><a href="pages">Hot Deals</a></li>
								<li><a href="pages/">Categories</a></li>
								<li><a href="pages/laptops">Laptops</a></li>
								<li><a href="pages/smartphones">Smartphones</a></li>
								<li><a href="pages/cameras">Cameras</a></li>
								<li><a href="pages/accessories">Accessories</a></li>
							</ul>
						</div>
					</div>

					<div class="clearfix visible-xs"></div>

					<div class="col-md-3 col-xs-6">
						<div class="footer">
							<h3 class="footer-title">Information</h3>
							<ul class="footer-links">
								<li><a href="#">About Us</a></li>
								<li><a href="#">Contact Us</a></li>
								<li><a href="#">Privacy Policy</a></li>
								<li><a href="#">Orders and Returns</a></li>
								<li><a href="#">Terms & Conditions</a></li>
							</ul>
						</div>
					</div>

					<div class="col-md-3 col-xs-6">
						<div class="footer">
							<h3 class="footer-title">Service</h3>
							<ul class="footer-links">
								<li><a href="<c:url value="/login" />">My Account</a></li>
								<li><a href="#">View Cart</a></li>
								<li><a href="#">Wishlist</a></li>
								<li><a href="#">Track My Order</a></li>
								<li><a href="#">Help</a></li>
							</ul>
						</div>
					</div>
				</div>
				<!-- /row -->
			</div>
			<!-- /container -->
		</div>
		<!-- /top footer -->

		<!-- bottom footer -->
		<div id="bottom-footer" class="section">
			<div class="container">
				<!-- row -->
				<div class="row">
					<div class="col-md-12 text-center">
						<ul class="footer-payments">
							<li><a href="#"><i class="fa fa-cc-visa"></i></a></li>
							<li><a href="#"><i class="fa fa-credit-card"></i></a></li>
							<li><a href="#"><i class="fa fa-cc-paypal"></i></a></li>
							<li><a href="#"><i class="fa fa-cc-mastercard"></i></a></li>
							<li><a href="#"><i class="fa fa-cc-discover"></i></a></li>
							<li><a href="#"><i class="fa fa-cc-amex"></i></a></li>
						</ul>
						<span class="copyright"> <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
							Copyright &copy;<script>
								document.write(new Date().getFullYear());
							</script> All rights reserved | This template is made with <i
							class="fa fa-heart-o" aria-hidden="true"></i> by <a
							href="https://colorlib.com" target="_blank">Colorlib</a> <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						</span>
					</div>
				</div>
				<!-- /row -->
			</div>
			<!-- /container -->
		</div>
		<!-- /bottom footer -->
	</footer>
	<!-- /FOOTER -->
</body>
</html>