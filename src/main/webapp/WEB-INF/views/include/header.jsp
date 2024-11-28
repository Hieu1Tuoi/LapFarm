<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<base href="${pageContext.servletContext.contextPath}/">
<style>
.dropdown-menu {
	display: none; /* Ẩn menu theo mặc định */
	position: absolute;
	top: 100%;
	left: 0;
	z-index: 1000;
	float: left;
	min-width: 15rem; /* Tăng chiều rộng tối thiểu */
	padding: 1rem 0; /* Tăng khoảng cách trên dưới của menu */
	margin: 0;
	font-size: 16px; /* Tăng kích thước chữ */
	background-color: #fff;
	border: 1px solid rgba(0, 0, 0, 0.15);
	border-radius: 0.25rem;
	box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.1);
	/* Thêm hiệu ứng bóng mờ */
}

.dropdown-item {
	padding: 12px 20px; /* Tăng khoảng cách bên trong của các item */
	font-size: 16px; /* Kích thước chữ của item */
	color: #212529;
	text-decoration: none;
	display: block;
}

.dropdown-item:hover {
	background-color: #f1f1f1; /* Hiệu ứng hover */
	color: #000;
}
</style>

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
			<ul class="header-links pull-right">
				<li><a href="#"><i class="fa fa-dollar"></i> USD</a></li>
				<c:choose>
					<c:when test="${not empty sessionScope.user}">
						<li class="dropdown"><a href="#" class="dropdown-toggle">
								<i class="fa fa-user-o"></i> Tài khoản
						</a>
							<div class="dropdown-menu">
								<a class="dropdown-item" href="<c:url value='/account' />">Thông
									tin</a> <a class="dropdown-item"
									href="<c:url value='/account#orders-history' />">Đơn hàng</a> <a
									class="dropdown-item" href="<c:url value='/logout' />">Đăng
									xuất</a>
							</div></li>
					</c:when>
					<c:otherwise>
						<li><a href="<c:url value='/login' />"><i
								class="fa fa-user-o"></i> Tài khoản</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
	<!-- /TOP HEADER -->

	<!-- MAIN HEADER -->
	<div id="header">
		<div class="container">
			<div class="row">
				<!-- LOGO -->
				<div class="col-md-3">
					<div class="header-logo">
						<a href="<c:url value='/home' />" class="logo"> <img
							src="<c:url value='/resources/img/logo.png' />" alt="">
						</a>
					</div>
				</div>
				<!-- /LOGO -->

				<!-- SEARCH BAR -->
				<div class="col-md-6">
					<div class="header-search">
						<form action="search" method="get">
							<select class="input-select" name="category">
								<option value="0" ${searchCategory == 0 ? 'selected' : ''}>All
									Categories</option>
								<option value="1" ${searchCategory == 1 ? 'selected' : ''}>Category
									01</option>
								<option value="2" ${searchCategory == 2 ? 'selected' : ''}>Category
									02</option>
							</select> <input value="${searchText}" id="searchInput" class="input"
								name="searchtext" placeholder="Search here"> <input
								value="${priceRange}" name="priceRange" style="display: none;">
							<button type="submit" class="search-btn">Tìm Kiếm</button>
						</form>
					</div>
				</div>
				<!-- /SEARCH BAR -->

				<!-- ACCOUNT -->
				<div class="col-md-3 clearfix">
					<div class="header-ctn">
						<!-- Wishlist -->
						<div>
							<a href="#"> <i class="fa fa-heart-o"></i> <span>Wishlish</span>
								<div class="qty">2</div>
							</a>
						</div>
						<!-- /Wishlist -->

						<!-- Cart -->
						<div class="dropdown">
							<a href="<c:url value='/cart' />" class="cart-link"> <i
								class="fa fa-shopping-cart"></i> <span>Giỏ hàng</span>
								<div class="qty">${Cart.size()}</div>
							</a>
						</div>
						<!-- /Cart -->

						<!-- Menu Toggle -->
						<div class="menu-toggle">
							<a href="#"> <i class="fa fa-bars"></i> <span>Menu</span>
							</a>
						</div>
						<!-- /Menu Toggle -->
					</div>
				</div>
				<!-- /ACCOUNT -->
			</div>
		</div>
	</div>
	<!-- /MAIN HEADER -->
</header>
<!-- /HEADER -->

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap 4 JavaScript -->
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

<script>
	$(document).ready(function() {
		// Xử lý hiển thị/ẩn dropdown khi nhấp vào nút
		$('.dropdown-toggle').on('click', function(e) {
			e.preventDefault();
			$(this).next('.dropdown-menu').toggle(); // Hiện/ẩn menu
		});

		// Đóng menu khi nhấp ra ngoài
		$(document).on('click', function(e) {
			if (!$(e.target).closest('.dropdown').length) {
				$('.dropdown-menu').hide(); // Ẩn menu
			}
		});
	});
</script>
