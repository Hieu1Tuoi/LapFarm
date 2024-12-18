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
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
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
							<a href="<c:url value='/home' />" class="logo"> <img
								src="<c:url value='/resources/img/Lapfarm.png' />" alt=""
								style="max-width: 340px;">
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
						<form action="<c:url value='/login' />" method="post">
							<div class="form-group">
								<label for="email">Email:</label> <input type="email"
									class="form-control" id="email" name="email" value="${email}"
									placeholder="Nhập email của bạn" required>
							</div>
							<div class="form-group">
								<label for="password">Mật khẩu:</label> <input type="password"
									class="form-control" id="password" name="password"
									value="${pw}" placeholder="Nhập mật khẩu của bạn" required>
								<a href="<c:url value='/forgotpassword' />">Quên mật khẩu?</a>
							</div>
							<div class="form-group">
								<strong style="color: red;">${warning}</strong>
							</div>
							<!--  Captcha -->
							<div class="form-group text-center">
								<div class="g-recaptcha" data-sitekey="6LcMHp8qAAAAADwRM1JbSj3IJLwDuyTqsFx2uEGo"></div>
							</div>

							<div class="form-group">
								<button type="submit" class="btn btn-primary btn-block">Đăng
									Nhập</button>
							</div>
							<div class="text-center">- Hay -</div>
							<br>
							<div class="text-center">
								<a href="<c:url value='${googleAuthUrl}' />"
									class="google-login"> <i class="fa fa-google"
									style="color: red;"></i> Đăng nhập với Google
								</a>
							</div>
							<br>
							<div class="text-center">
								Bạn chưa có tài khoản? <a href="<c:url value='/signup' />">Đăng
									ký ngay!</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /Login -->

	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>