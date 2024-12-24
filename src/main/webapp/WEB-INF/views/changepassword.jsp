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

<!-- sigup -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/sigup.css" />">

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
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
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
							nhom8@email.com</a></li>
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

	<!-- sigup -->
	<div id="sigup" class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="sigup-form">
						<h2 class="text-center">Đổi mật khẩu</h2>
						<form action="<c:url value='/change-password' />" method="post">
							<div class="form-group">
								<label for="old-password">Mật khẩu cũ:</label> <input
									type="password" class="form-control" id="old-password"
									value="${opw}" name="oldPassword" minlength="8" maxlength="50"
									placeholder="Nhập mật khẩu cũ của bạn" required>
							</div>
							<div class="form-group">
								<label for="new-password">Mật khẩu:</label> <input
									type="password" class="form-control" id="new-password"
									value="${pw}" name="password" minlength="8" maxlength="50"
									placeholder="Nhập mật khẩu mới của bạn" required>
							</div>
							<div class="form-group">
								<label for="confirm-password">Xác nhận Mật khẩu mới:</label> <input
									type="password" class="form-control" id="confirm-password"
									value="${cfpw}" name="confirmPassword" maxlength="50"
									placeholder="Xác nhận mật khẩu của bạn" required>
							</div>


							<!--  Captcha -->
							<div class="form-group text-center">
								<div class="g-recaptcha"
									data-sitekey="6LcMHp8qAAAAADwRM1JbSj3IJLwDuyTqsFx2uEGo"></div>
							</div>

							<div class="form-group">
								<label for="verification-code">Mã xác nhận:</label>
								<div class="input-group">
									<input type="text" class="form-control" id="verification-code"
										name="verificationCode" maxlength="6" placeholder="Nhập mã xác nhận"
										required>
									<button type="button" id="send-code-btn">Gửi mã</button>
								</div>
							</div>

							<div class="form-group">
								<strong style="color: red;">${warning}</strong> <strong
									style="color: green;">${message}</strong>
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-primary btn-block">Đổi
									mật khẩu</button>
							</div>
							<div class="form-group">
                            <a href="<c:url value='/home' />" class="btn btn-secondary btn-block">
                                Trang chủ
                            </a>
                        </div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /Signup Form -->

	<!-- NAVIGATION -->
	<nav id="navigation">
		<!-- container -->
		<div class="container"></div>
		<!-- /container -->
	</nav>
	<!-- /NAVIGATION -->
	<!-- FOOTER -->
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<!-- /FOOTER -->
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
    $(document).ready(function() {
        // Kiểm tra nếu có message thành công
        var message = "${message}";
        
        if (message) {
            // Nếu có message thành công, chuyển hướng đến trang đăng nhập
            setTimeout(function() {
                window.location.href = "<c:url value='/login' />";
            }, 3000); // Đợi 3 giây trước khi chuyển hướng
        }
        

        $('#send-code-btn').click(function() {
        	// Gửi yêu cầu AJAX trực tiếp mà không cần email
            $.ajax({
                url: 'change-password/send-code',
                type: 'POST',
                success: function(response) {
                    $('#code-message')
                        .text("Mã xác nhận đã được gửi đến email của bạn!")
                        .removeClass('text-danger')
                        .addClass('text-success')
                        .show();
                    alert("Mã xác minh đã được gửi đến email của bạn!");
                },
                error: function(xhr, status, error) {
                    $('#code-message')
                        .text("Có lỗi xảy ra khi gửi mã xác minh. Vui lòng thử lại sau.")
                        .removeClass('text-success')
                        .addClass('text-danger')
                        .show();
                }
            });
        });
    });
</script>
</html>