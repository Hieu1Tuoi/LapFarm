<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-breadcumb.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-aside.jsp"%>

<!-- SECTION -->

<!-- STORE -->
<div id="store" class="col-md-9">
<h2>${products != null ? products.size() : 0}</h2>
	<h3>
		TẤT CẢ SẢN PHẨM:
		<fmt:formatNumber value="${totalQuantity}" type="number"
			groupingUsed="true" />
	</h3>

	<!-- store products -->
	<div class="row">
		<!-- product -->

		<c:if test="${products.size()>0}">
			<ul class="thumbnails">
				<c:forEach var="p" items="${products}" varStatus="loop">

					<div class="col-md-4 col-xs-6">
						<div class="product">
							<div class="product-img">
								<a href="chi-tiet-san-pham/${p.idProduct}"></a>
								<!-- Lấy hình ảnh đầu tiên từ danh sách -->
								<c:choose>
									<c:when test="${not empty p.images}">
										<img src="${p.images[0].imageUrl}" alt="Product Image">
									</c:when>
									<c:otherwise>
										<img src="/LapFarm/resources/img/${p.images[0].imageUrl}"
											alt="Default Image">
									</c:otherwise>
								</c:choose>

								<div class="product-label">
									<span class="sale">${p.discount*100}%</span>
								</div>
							</div>
							<div class="product-body">
								<p class="product-category">${p.category.nameCategory}</p>
								<h4 class="product-name">
									<a href="chi-tiet-san-pham/${p.idProduct}"
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
							<div class="add-to-cart">
								<button class="add-to-cart-btn">
									<i class="fa fa-shopping-cart"></i> Thêm giỏ hàng
								</button>
							</div>
						</div>
					</div>

					<c:if
						test="${(loop.index +1)%3==0 || (loop.index +1)==highLightProducts.size() }">
			</ul>
			<c:if test="${(loop.index +1)< highLightProducts.size() }">
				<ul class="thumbnails">
			</c:if>
		</c:if>
		</c:forEach>
		</c:if>

		<!-- /product -->

	</div>
	<!-- /store products -->

	<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>