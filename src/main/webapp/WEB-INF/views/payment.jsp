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
<base href="${pageContext.servletContext.contextPath}/">
<style>
.error-message {
	color: red;
	font-size: 12px;
	margin-top: 5px;
	display: block;
}
</style>
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
							Nhom8@email.com</a></li>
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

	<!-- BREADCRUMB -->
	<div id="breadcrumb" class="section">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<div class="col-md-12">
					<h3 class="breadcrumb-header">Thanh toán</h3>
					<ul class="breadcrumb-tree">
						<li><a href="home">Trang chủ</a></li>
						<li class="active">Thanh toán</li>
					</ul>
				</div>
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /BREADCRUMB -->

	<form action="" method="GET">
		<!-- SECTION -->
		<div class="section">
			<!-- container -->
			<div class="container">
				<!-- row -->
				<div class="row">

					<div class="col-md-7">
						<!-- Billing Details -->
						<div class="billing-details">
							<div class="section-title">
								<h3 class="title">Địa chỉ thanh toán</h3>
							</div>

							<!-- Họ và tên -->
							<div class="form-group">
								<input class="input" type="text" name="fullName"
									placeholder="Họ và tên" value="${userInfo.fullName}"><span
									class="error-message" id="error-fullname">${errorFullName}</span>
							</div>

							<!-- Email -->
							<div class="form-group">
								<input class="input" type="email" placeholder="Email"
									value="${userInfo.account.email}" disabled> <span
									class="error-message" id="error-email"></span>
							</div>

							<!-- Địa chỉ -->
							<div class="form-group">
								<input class="input" type="text" name="address"
									placeholder="Địa chỉ" value="${userInfo.address}"> <span
									class="error-message" id="error-address">${errorAddress}</span>
							</div>

							<!-- Số điện thoại -->
							<div class="form-group">
								<input class="input" type="tel" name="tel"
									placeholder="Số điện thoại" value="${userInfo.phone}">
								<span class="error-message" id="error-tel">${errorTel}</span>
							</div>

							<div class="form-group">
								<div class="input-checkbox">
									<input type="checkbox" id="create-account"> <label
										for="create-account"> <span></span> Create Account?
									</label>
									<div class="caption">
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit, sed do eiusmod tempor incididunt.</p>
										<input class="input" type="password" name="password"
											placeholder="Enter Your Password">
									</div>
								</div>
							</div>
						</div>
						<!-- /Billing Details -->

						<!-- Shiping Details -->
						<div class="shiping-details">
							<div class="section-title">
								<h3 class="title">Shiping address</h3>
							</div>
							<div class="input-checkbox">
								<input type="checkbox" id="shiping-address"> <label
									for="shiping-address"> <span></span> Ship to a diffrent
									address?
								</label>
								<div class="caption">
									<div class="form-group">
										<input class="input" type="text" name="first-name"
											placeholder="First Name">
									</div>
									<div class="form-group">
										<input class="input" type="text" name="last-name"
											placeholder="Last Name">z
									</div>
									<div class="form-group">
										<input class="input" type="email" name="email"
											placeholder="Email">
									</div>
									<div class="form-group">
										<input class="input" type="text" name="address"
											placeholder="Address">
									</div>
									<div class="form-group">
										<input class="input" type="text" name="city"
											placeholder="City">
									</div>
									<div class="form-group">
										<input class="input" type="text" name="country"
											placeholder="Country">
									</div>
									<div class="form-group">
										<input class="input" type="text" name="zip-code"
											placeholder="ZIP Code">
									</div>
									<div class="form-group">
										<input class="input" type="tel" name="tel"
											placeholder="Telephone">
									</div>
								</div>
							</div>
						</div>
						<!-- /Shiping Details -->

						<!-- Order notes -->
						<div class="order-notes">
							<textarea name="note" class="input" placeholder="Order Notes"></textarea>
							<span class="error-message">${errorNote}</span>
						</div>
						<!-- /Order notes -->
					</div>

					<!-- Order Details -->
					<div class="col-md-5 order-details">
						<div class="section-title text-center">
							<h3 class="title">Đơn hàng của bạn</h3>
						</div>
						<div class="order-summary">
							<div class="order-col">
								<div>
									<strong>Sản phẩm</strong>
								</div>
								<div>
									<strong>Tổng tiền</strong>
								</div>
							</div>
							<div class="order-products">
								<c:forEach var="product" items="${cartProducts}"
									varStatus="status">
									<div class="order-col">
										<div>
											<strong>${product.quantity}x</strong> <span>${product.productName}</span>
										</div>
										<div>
											<span>${product.formattedPrice}</span> VND
										</div>
									</div>
								</c:forEach>
							</div>
							<div class="order-col">
								<div>Phí giao hàng</div>
								<div>
									<strong>FREE</strong>
								</div>
							</div>
							<div class="order-col">
								<div>
									<strong>THÀNH TIỀN</strong>
								</div>
								<div>
									<strong class="order-total">${totalAmount} VND</strong>
								</div>
							</div>
						</div>
						<!-- Phương thức thanh toán -->
					 <div class="payment-method">
							<div class="input-radio">
								<input type="radio" name="payment" value="0" id="payment-1"
									value="bank-transfer"> <label for="payment-1"><span></span>
									Thanh toán tiền mặt</label>
							</div>
							<div class="input-radio">
								<input type="radio" name="payment" value="1" id="payment-2"
									value="cash"> <label for="payment-2"><span></span>
									Thanh toán qua VNPAY</label>
							</div>
							<span class="error-message" id="error-payment"></span>
						</div> 

						


						<!-- Điều khoản -->
						<div class="input-checkbox">
							<input type="checkbox" id="terms"> <label for="terms">
								<span></span> Tôi đã đọc và chấp nhận các <a href="#">điều
									khoản và điều kiện</a>
							</label> <span class="error-message" id="error-terms"></span>
						</div>
						<input type="hidden" name="totalAmount"
							value="${totalAmountDefaut}">
						<button style="width: 425px;" type="submit"
							class="primary-btn order-submit">Thanh toán</button>
					</div>
					<!-- /Order Details -->
				</div>
				<!-- /row -->
			</div>
			<!-- /container -->
		</div>
		<!-- /SECTION -->
	</form>

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

	<script>
	function validateForm(event) {
	    // Lấy giá trị của các input
	    const fullName = document.querySelector("input[name='fullName']").value.trim();
	    const email = document.querySelector("input[name='email']").value.trim();
	    const address = document.querySelector("input[name='address']").value.trim();
	    const tel = document.querySelector("input[name='tel']").value.trim();
	    const paymentMethod = document.querySelector("input[name='payment']:checked");
	    const termsAccepted = document.querySelector("#terms").checked;

	    // Đặt lại thông báo lỗi
	    document.querySelectorAll(".error-message").forEach(span => span.innerText = "");

	    // Kiểm tra và hiển thị lỗi
	    let isValid = true;

	    if (!fullName) {
	        document.getElementById("error-fullname").innerText = "Tên không được để trống.";
	        isValid = false;
	    }

	    if (!address) {
	        document.getElementById("error-address").innerText = "Địa chỉ không được để trống.";
	        isValid = false;
	    }

	    if (!tel) {
	        document.getElementById("error-tel").innerText = "Số điện thoại không được để trống.";
	        isValid = false;
	    } else if (!/^\d{10}$/.test(tel)) {
	        document.getElementById("error-tel").innerText = "Số điện thoại phải đúng 10 chữ số.";
	        isValid = false;
	    }

	    if (!paymentMethod) {
	        document.getElementById("error-payment").innerText = "Vui lòng chọn phương thức thanh toán.";
	        isValid = false;
	    }

	    if (!termsAccepted) {
	        document.getElementById("error-terms").innerText = "Bạn phải chấp nhận các điều khoản.";
	        isValid = false;
	    }

	     // Thay đổi action của form dựa trên phương thức thanh toán
	    if (paymentMethod) {
	        const form = document.querySelector("form");
	        if (paymentMethod.value === "0") {
	            form.action = "payment/success"; // Thanh toán tiền mặt
	        } else if (paymentMethod.value === "1") {
	            form.action = "payment/vnpay"; // Thanh toán qua VNPAY
	        }
	    } 
	


	    // Ngăn form submit nếu có lỗi
	    if (!isValid) {
	        event.preventDefault();
	    }
	}

	// Gắn sự kiện click vào nút thanh toán
	document.addEventListener("DOMContentLoaded", function () {
	    const submitButton = document.querySelector(".order-submit");
	    submitButton.addEventListener("click", validateForm);
	});
</script>

</body>
</html>
