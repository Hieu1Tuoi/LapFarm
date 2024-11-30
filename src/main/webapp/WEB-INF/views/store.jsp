<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-breadcumb.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-aside.jsp"%>

<!-- SECTION -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/outOfStock.css" />">

<!-- STORE -->
<div id="store" class="col-md-9">
<h1>${user.email}</h1>
	<h2>${ProductsPaginate != null ? ProductsPaginate.size() : 0}</h2>
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
								<a href="product-detail/${p.idProduct}"></a>
								<!-- Lấy hình ảnh đầu tiên từ danh sách -->
								<c:choose>
									<c:when test="${not empty p.image}">
										<img src="${p.image}" alt="Product Image">
									</c:when>
									<c:otherwise>
										<img src="/LapFarm/resources/img/${p.image}"
											alt="Default Image">
									</c:otherwise>
								</c:choose>

								<div class="product-label">
									<span class="sale">${p.discount*100}%</span>
								</div>
							</div>
							<div class="product-body">
								<p class="product-category">${p.categoryName}</p>
								<h4 class="product-name">
									<a href="product-detail/${p.idProduct}"
										title="${p.nameProduct}">${p.nameProduct}</a>
								</h4>
								<h6 class="product-price">
									<td><fmt:formatNumber value="${p.calPrice()}"
											type="number" groupingUsed="true" /> ₫</td>
									<del class="product-old-price">
										<td><fmt:formatNumber value="${p.calSalePrice()}"
												type="number" groupingUsed="true" /> ₫</td>
									</del>
								</h6>
								<div class="product-rating">
									<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
										class="fa fa-star"></i> <i class="fa fa-star"></i> <i
										class="fa fa-star"></i>
								</div>
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
                                        <button type="button" class="add-to-cart-btn disabled" disabled>
                                            <i class="fa fa-ban"></i> Hết hàng
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
						</div>
					</div>

					<c:if
						test="${(loop.index +1)%3==0 || (loop.index +1)==ProductsPaginate.size() }">
			</ul>
			<c:if test="${(loop.index +1)< ProductsPaginate.size() }">
				<ul class="thumbnails">
			</c:if>
		</c:if>
		</c:forEach>
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
