<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-breadcumb.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-aside.jsp"%>


<style>
.product-img img {
	width: 250px;
	height: 250px;
	object-fit: cover; /* Cắt ảnh để vừa khung */
}

.alert-warning {
    background-color: #fff3cd; /* Nền vàng nhạt cho phần cảnh báo */
    color: #856404; /* Màu chữ vàng đậm */
    border: 1px solid #ffeeba; /* Đường viền vàng nhạt */
    border-radius: 5px; /* Bo góc */
    padding: 15px;
    font-size: 16px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 20px 0;
    font-family: 'Arial', sans-serif; /* Chọn kiểu phông chữ phù hợp */
    font-weight: bold; /* Đậm chữ */
}

.alert-warning .warning-text {
    font-family: 'Courier New', monospace; /* Phông chữ dễ đọc cho thông báo */
    font-size: 18px; /* Kích thước phông chữ lớn hơn */
    color: #856404; /* Màu chữ vàng đậm */
    text-shadow: 1px 1px 2px #ffcc00; /* Hiệu ứng đổ bóng nhẹ cho chữ */
    letter-spacing: 0.5px; /* Khoảng cách giữa các chữ */
}

.alert-warning a {
    background-color: #dc3545; /* Màu nền nút đỏ */
    color: white; /* Chữ màu trắng */
    text-decoration: none; /* Xóa gạch dưới */
    padding: 10px 20px; /* Khoảng cách xung quanh chữ */
    border-radius: 4px; /* Bo góc nút */
    font-weight: bold;
    transition: background-color 0.3s ease, transform 0.2s ease; /* Hiệu ứng chuyển màu và hiệu ứng nhấn */
    display: inline-block;
}

.alert-warning a:hover {
    background-color: #c82333; /* Màu nền khi hover (đỏ đậm hơn) */
    transform: scale(1.05); /* Hiệu ứng phóng to nhẹ khi hover */
}

.alert-warning a:active {
    background-color: #bd2130; /* Màu nền khi nhấn (đỏ tối hơn) */
    transform: scale(0.98); /* Hiệu ứng thu nhỏ khi nhấn */
}

</style>
<!-- SECTION -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/outOfStock.css" />">

<!-- STORE -->
<div id="store" class="col-md-9">
	<c:if test="${not empty passwordWarning}">
		<div class="alert alert-warning">
			${passwordWarning} <a href="change-password">Đổi mật khẩu ngay!</a>
		</div>
	</c:if>
	<h3>
		TẤT CẢ SẢN PHẨM:
		<fmt:formatNumber value="${products.size()}" type="number"
			groupingUsed="true" />
	</h3>

	<!-- store products -->
	<div class="row">
		<!-- product -->

		<c:if test="${ProductsPaginate.size()>0}">
			<ul class="thumbnails">
				<c:forEach var="p" items="${ProductsPaginate}" varStatus="loop">
					<div class="col-md-4 col-xs-6">
						<div class="product">
							<div class="product-img">
								<a href="product-detail/${p.encryptedId}"></a>
								<!-- Lấy hình ảnh đầu tiên từ danh sách -->
								<c:choose>
									<c:when test="${not empty p.image}">
										<img src="${p.image}" alt="Product Image"
											onerror="this.src='/LapFarm/resources/img/soicodoc.jpg'">
									</c:when>
									<c:otherwise>
										<img src="/LapFarm/resources/img/${p.image}"
											alt="Default Image"
											onerror="this.src='/LapFarm/resources/img/soicodoc.jpg'">
									</c:otherwise>
								</c:choose>
								<c:if test="${p.discount > 0}">
									<div class="product-label">
										<span class="sale"> <fmt:formatNumber
												value="${p.discount * 100}" type="number"
												maxFractionDigits="0" />%
										</span>
									</div>
								</c:if>

							</div>
							<div class="product-body">
								<p class="product-category">${p.categoryName}</p>
								<h4 class="product-name">
									<a href="product-detail/${p.encryptedId}"
										title="${p.nameProduct}">${p.nameProduct}</a>
								</h4>
								<h6 class="product-price">
									<td><fmt:formatNumber value="${p.calPrice()}"
											type="number" groupingUsed="true" /> ₫</td>
									<c:if test="${p.discount > 0}">
										<del class="product-old-price">
											<fmt:formatNumber value="${p.calSalePrice()}" type="number"
												groupingUsed="true" />
											₫
										</del>
									</c:if>
								</h6>
								<style>
.fa-star {
	color: red;
}

.fa-star-half-o {
	color: red;
}

.fa-star-o {
	color: red;
}
</style>
								<c:if test="${not empty p.ratingSummary}">
									<div class="product-rating">
										<div class="rating-stars">
											<c:forEach var="star" begin="1" end="5">
												<c:choose>
													<c:when test="${star <= p.ratingSummary.average}">
														<i class="fa fa-star"></i>
													</c:when>
													<c:when
														test="${star - 0.5 <= p.ratingSummary.average && star > p.ratingSummary.average}">
														<i class="fa fa-star-half-o"></i>
													</c:when>
													<c:otherwise>
														<i class="fa fa-star-o"></i>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</div>
									</div>
								</c:if>


								<div class="product-btns">
									<button class="add-to-wishlist">
										<i class="fa fa-heart-o"></i><span class="tooltipp">Thêm
											Yêu Thích</span>
									</button>
									<button class="add-to-compare">
										<i class="fa fa-exchange"></i><span class="tooltipp">So
											sánh</span>
									</button>
									<button class="quick-view">
										<i class="fa fa-eye"></i><span class="tooltipp">Xem
											nhanh</span>
									</button>
								</div>
							</div>
							<!-- Nút thêm vào giỏ hàng -->
							<div class="add-to-cart">
								<c:choose>
									<c:when test="${p.quantity > 0}">
										<form action="addCart/${p.idProduct}" method="GET">
											<button type="submit" class="add-to-cart-btn">
												<i class="fa fa-shopping-cart"></i> Thêm giỏ hàng
											</button>
										</form>
									</c:when>
									<c:otherwise>
										<button type="button" class="add-to-cart-btn disabled"
											disabled>
											<i class="fa fa-ban"></i> Hết hàng
										</button>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>

					<c:if
						test="${(loop.index +1)%3==0 || (loop.index +1)==ProductsPaginate.size() }">

						<c:if test="${(loop.index +1)< ProductsPaginate.size() }">
							<ul class="thumbnails">
						</c:if>
					</c:if>
				</c:forEach>
			</ul>
		</c:if>

		<!-- /product -->

	</div>
	<!-- /store products -->
	<div class="store-filter clearfix">

		<c:forEach var="item" begin="1" end="${paginateInfo.totalPage}"
			varStatus="loop">
			<ul class="store-pagination">
				<c:if test="${(loop.index)==paginateInfo.currentPage }">

					<li class="active"><a href="home?page=${loop.index}">${loop.index}</li>
				</c:if>
				<c:if test="${(loop.index)!=paginateInfo.currentPage}">
					<li><a href="home?page=${loop.index}">${loop.index}</a></li>
				</c:if>
		</c:forEach>
		<!-- Nút điều hướng đến trang tiếp theo -->
		<c:if test="${paginateInfo.currentPage < paginateInfo.totalPage}">
			<li><a href="home?page=${paginateInfo.currentPage + 1}"> <i
					class="fa fa-angle-right"></i>
			</a></li>
		</c:if>
		</ul>
	</div>
	<!-- /store bottom filter -->

	<!-- /store bottom filter -->
</div>
<!-- /STORE -->
<script type="text/javascript">
	// Kiểm tra nếu có lỗi từ tham số URL
	<c:if test="${not empty param.error}">
	var error = "${param.error}";

	// Hiển thị thông báo tương ứng với lỗi
	if (error === 'product-unavailable') {
		alert('Sản phẩm đã hết hàng, không thể thêm vào giỏ.');
	} else if (error === 'invalid-quantity') {
		alert('Số lượng không hợp lệ, vui lòng kiểm tra lại.');
	}
	</c:if>
</script>



<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>
