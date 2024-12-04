<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<base href="${pageContext.servletContext.contextPath}/">
<style>
.dropdown-menu {
	display: none;
	position: absolute;
	top: 100%;
	left: 0;
	z-index: 1000;
	float: left;
	min-width: 16rem;
	padding: 1rem 0;
	margin: 0;
	font-size: 17px;
	background-color: #000; /* Đổi nền sang màu đen */
	border: 1px solid rgba(255, 255, 255, 0.15);
	/* Đường viền nhẹ màu trắng */
	border-radius: 0.25rem;
	box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.1);
}

.dropdown.active .dropdown-menu {
	display: block;
}

.dropdown-item {
	padding: 5px 12px;
	font-size: 16px;
	color: #fff; /* Chữ màu trắng */
	text-decoration: none;
	display: block;
	background-color: transparent; /* Giữ nền trong suốt cho item */
}

.dropdown-item:hover {
	background-color: #333; /* Nền màu xám khi hover */
	color: #fff;
} /* Giữ chữ màu trắng khi hover */
.notification-container {
	position: relative;
	display: inline-block;
}

.notification-box {
	display: none;
	position: absolute;
	top: 40px;
	right: 0;
	background: #fff;
	border: 1px solid #ddd;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	padding: 10px;
	width: 250px;
	z-index: 1000;
	border-radius: 5px;
}

.notification-box ul {
	list-style: none;
	padding: 0;
	margin: 0;
}

.notification-box ul li {
	padding: 5px 0;
	border-bottom: 1px solid #eee;
}

.notification-box ul li:last-child {
	border-bottom: none;
}

.notification-container:hover .notification-box {
	display: block;
}

.unread {
	color: red;
	font-weight: bold;
}

.read {
	color: #000;
	font-weight: normal;
}

.notification-box {
	display: none;
	position: absolute;
	top: 40px;
	right: 0;
	background: #f9f9f9; /* Nền sáng */
	border: 1px solid #ddd;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	padding: 10px;
	width: 300px;
	max-height: 400px; /* Giới hạn chiều cao */
	overflow-y: auto; /* Thêm thanh cuộn dọc */
	z-index: 1000;
	border-radius: 5px;
}

/* Thu nhỏ thanh cuộn */
.notification-box::-webkit-scrollbar {
	width: 6px; /* Độ rộng của thanh cuộn */
}

.notification-box::-webkit-scrollbar-thumb {
	background-color: #888; /* Màu thanh cuộn */
	border-radius: 10px; /* Làm tròn thanh cuộn */
}

.notification-box::-webkit-scrollbar-thumb:hover {
	background-color: #555; /* Màu thanh cuộn khi hover */
}

.notification-box::-webkit-scrollbar-track {
	background: #f1f1f1; /* Màu nền phía sau thanh cuộn */
}

.notification-box ul {
	list-style: none;
	padding: 0;
	margin: 0;
}

.notification-box ul li {
	padding: 5px 0;
	border-bottom: 1px solid #eee;
	display: flex; /* Để căn chỉnh icon và nội dung */
	align-items: center;
	gap: 10px;
}

.notification-box ul li:last-child {
	border-bottom: none;
}

.unread i {
	color: red; /* Icon màu đỏ cho thông báo chưa đọc */
}

.read i {
	color: green; /* Icon màu xanh cho thông báo đã đọc */
}

.notification-header {
	font-weight: bold;
	font-size: 18px;
	padding: 5px 10px;
	margin-bottom: 10px;
	text-align: center;
	border-bottom: 1px solid #ddd;
	background-color: #f9f9f9;
	color: green;
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
				<!-- <li><a href="#"><i class="fa fa-dollar"></i> USD</a></li> -->
				<c:choose>
					<c:when test="${not empty sessionScope.user}">
						<li class="dropdown"><a href="#" class="dropdown-toggle">
								<i class="fa fa-user-o"></i> Tài khoản
						</a>
							<div class="dropdown-menu">
								<a class="dropdown-item" href="<c:url value='/account' />">Thông
									tin</a> <a class="dropdown-item"
									href="<c:url value='/account#orders-history' />">Đơn hàng</a> <a
									class="dropdown-item" href="<c:url value='/account#viewed' />">Đã
									xem gần đây</a> <a class="dropdown-item"
									href="<c:url value='/logout' />">Đăng xuất</a>
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
							<!-- Dropdown cho danh mục -->
							<select class="input-select" name="idCategory">
								<option value="0" ${searchCategory == 0 ? 'selected' : ''}>Tất cả danh mục</option>
								<c:forEach var="cate" items="${categories}">
									<option value="${cate.idCategory}"
										${searchCategory == cate.idCategory ? 'selected' : ''}>
										${cate.nameCategory}</option>
								</c:forEach>
							</select>

							<!-- Ô nhập từ khóa tìm kiếm -->
							<input value="${searchText}" id="searchInput" class="input"
								name="searchtext" placeholder="Search here..." />

							<!-- Ô nhập giá hoặc các tham số ẩn -->
							<input type="hidden" value="${priceRange}" name="priceRange" />

							<!-- Ô nhập ẩn cho thương hiệu nếu có -->
							<c:if test="${not empty idBrand}">
								<input id="idBrandInput" type="hidden" name="idBrand"
									value="${idBrand}" />
							</c:if>

							<!-- Nút tìm kiếm -->
							<button type="submit" class="search-btn">Tìm Kiếm</button>
						</form>
					</div>
				</div>
				<!-- /SEARCH BAR -->


				<!-- ACCOUNT -->
				<div class="col-md-3 clearfix">
					<div class="header-ctn">
						<!-- Notifications -->
						<div class="notification-container">
							<a> <i class="fa fa-bell-o"></i> <span>Thông báo</span>
								<div class="qty" 
									style="display: ${unreadNotificationsCount > 0 ? 'block' : 'none'};">
									<span>${unreadNotificationsCount}</span>
								</div>
							</a>

							<!-- Kiểm tra nếu có thông báo mới -->
							<c:if test="${not empty notifications}">
								<div class="notification-box">
									<!-- Thêm dòng "Tất cả thông báo" -->
									<div class="notification-header">Tất cả thông báo</div>

									<ul>
										<!-- Hiển thị danh sách thông báo -->
										<c:forEach var="notification" items="${notifications}">
											<li>
												<!-- Liên kết sẽ đánh dấu thông báo đã đọc và chuyển tới chi tiết thông báo -->
												<a
												href="<c:url value='/notification/${notification.notiId}?state=1' />"
												class="${notification.state == 0 ? 'unread' : 'read'}">
													<!-- Icon chú ý bật/tắt --> <i
													class="fa ${notification.state == 0 ? 'fa-exclamation-circle' : 'fa-check-circle'}"
													style="color: ${notification.state == 0 ? 'red' : 'green'};">
												</i> <span>${notification.content}</span> <br> <span>${notification.time}</span>
											</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</c:if>
						</div>


						<!-- Cart -->
						<div class="dropdown">
							<a href="<c:url value='/cart' />" class="cart-link"> <i
								class="fa fa-shopping-cart"></i> <span>Giỏ hàng</span>
								<div id="totalQuantity" class="qty">${Cart != null ? Cart.size() : 0}</div>
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

	document.addEventListener("DOMContentLoaded", function() {
		const notificationContainer = document
				.querySelector(".notification-container");
		const notificationBox = document.querySelector(".notification-box");

		// Đóng hộp thoại khi click ra ngoài
		document.addEventListener("click", function(event) {
			if (!notificationContainer.contains(event.target)) {
				notificationBox.style.display = "none";
			}
		});

		// Hiển thị hộp thoại khi di chuột
		notificationContainer.addEventListener("mouseenter", function() {
			notificationBox.style.display = "block";
		});

		// Ẩn hộp thoại khi chuột rời đi
		notificationContainer.addEventListener("mouseleave", function() {
			notificationBox.style.display = "none";
		});
	});
	
	// Hàm để đánh dấu thông báo là đã đọc khi người dùng nhấp vào
// Hàm để đánh dấu thông báo là đã đọc và mở trang chi tiết
function markAsRead(notiId) {
    fetch(`/notification/${notiId}?state=1`, {
        method: 'GET'
    })
    .then(response => {
        if (response.ok) {
            // Chuyển hướng tới trang notification.jsp với notiId
            window.location.href = `/notification/details/${notiId}`;
        } else {
            alert("Có lỗi xảy ra khi đánh dấu thông báo.");
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

document.addEventListener("DOMContentLoaded", function() {
    const notificationContainer = document.querySelector(".notification-container");
    const notificationBox = document.querySelector(".notification-box");

    // Đóng hộp thoại khi click ra ngoài
    document.addEventListener("click", function(event) {
        if (!notificationContainer.contains(event.target)) {
            notificationBox.style.display = "none";
        }
    });

    // Hiển thị hộp thoại khi di chuột
    notificationContainer.addEventListener("mouseenter", function() {
        notificationBox.style.display = "block";
    });

    // Ẩn hộp thoại khi chuột rời đi
    notificationContainer.addEventListener("mouseleave", function() {
        notificationBox.style.display = "none";
    });
});

	
</script>
