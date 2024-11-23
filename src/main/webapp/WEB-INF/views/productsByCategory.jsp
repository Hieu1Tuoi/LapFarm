<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="${pageContext.servletContext.contextPath}/">
<title>Products By Category</title>

<style>
.product-name {
	display: block; /* Đặt phần tử theo dạng khối */
	white-space: nowrap; /* Ngăn không cho xuống dòng */
	overflow: hidden; /* Ẩn phần nội dung bị tràn */
	text-overflow: ellipsis; /* Hiển thị dấu "..." nếu nội dung bị cắt */
	max-width: 300px;
}

.product-name>a:hover {
	
}
</style>
<!-- Google font -->
<link
	href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700"
	rel="stylesheet">

<!-- Bootstrap -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css" />">

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

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->

</head>
<body>

</head>
<body>

    <!-- Include header and navigation -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <%@ include file="/WEB-INF/views/include/navigation.jsp" %>

    <!-- Breadcrumb -->
    <div id="breadcrumb" class="section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <ul class="breadcrumb-tree">
                        <li><a href="#">Home</a></li>
                        <li><a href="#">Categories</a></li>
                        <li class="active">${categoryName}</li> <!-- Category name dynamically passed from controller -->
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- SECTION -->
	<div class="section">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<!-- ASIDE -->
				<div id="aside" class="col-md-3">
					<!-- aside Widget -->
					<div class="aside">
						<h2>${productsByCategory != null ? productsByCategory.size() : 0}</h2>
						<h3 class="aside-title">DANH MỤC</h3>

						<div class="category-filter">
							<c:forEach var="cate" items="${categories}">
								<div class="category-item">
									<!-- Thẻ <a> chứa liên kết động -->
									<a href="products?idCategory=${cate.idCategory}">
										${cate.nameCategory} <small>(${productCounts[cate.idCategory]})</small>
									</a>
								</div>
								<br>
							</c:forEach>
						</div>

					</div>
					<!-- /aside Widget -->

					<!-- aside Widget -->
						<div class="aside">
						<h3 class="aside-title">GIÁ</h3>
						<div class="price-filter">
							<div id="price-slider"></div>
							<div class="input-number price-min">
								<input id="price-min" type="number"> <span
									class="qty-up">+</span> <span class="qty-down">-</span>
							</div>
							<span>-</span>
							<div class="input-number price-max">
								<input id="price-max" type="number"> <span
									class="qty-up">+</span> <span class="qty-down">-</span>
							</div>
						</div>
					</div>
					<!-- /aside Widget -->

					<!-- aside Widget -->
					<div class="aside">
						<h3 class="aside-title">THƯƠNG HIỆU</h3>

						<div class="brand-filter">
							<c:forEach var="brand" items="${brands}">
								<div class="brand-item">
									<!-- Thẻ <a> thay thế checkbox -->
									<a href="/thuong-hieu" class="brand-link"
										data-brand-id="${brand.idBrand}"> ${brand.nameBrand} <small>(${productCountsByBrand[brand.idBrand]})</small>
									</a>

								</div>
								<br>
							</c:forEach>
						</div>
					</div>
					<!-- /aside Widget -->

					<!-- aside Widget -->
					<div class="aside">

						<h3 class="aside-title">BÁN CHẠY NHẤT</h3>

						<c:forEach var="product" items="${products_top_sell}">
							<div class="product-widget">
								<div class="product-img">
									<!-- Sử dụng thuộc tính 'image' từ ProductDTO -->
									<c:choose>
										<c:when test="${not empty product.image}">
											<img src="${product.image}" alt="Product Image">
										</c:when>
										<c:otherwise>
											<img src="/LapFarm/resources/img/${p.images[0].imageUrl}"
												alt="Default Image">
										</c:otherwise>
									</c:choose>

								</div>
								<div class="product-body">
									<!-- Sử dụng thuộc tính 'category' từ ProductDTO -->
									<p class="product-category">${product.categoryName}</p>
									<h3 class="product-name">
										<a href="chi-tiet-san-pham/${product.idProduct}">${product.nameProduct}</a>
									</h3>
									<h4 class="product-price">
										<!-- Sử dụng phương thức 'getFormattedCalPrice' từ ProductDTO -->
										${product.getFormattedDiscountedPrice()}
										<del class="product-old-price">
											<!-- Sử dụng phương thức 'getFormattedSalePrice' từ ProductDTO -->
											${product.getFormattedSalePrice()}
										</del>
									</h4>
								</div>
							</div>
						</c:forEach>



					</div>
					<!-- /aside Widget -->
				</div>
				<!-- /ASIDE -->

				<!-- STORE -->
				<div id="store" class="col-md-9">
					<h3>
						SẢN PHẨM THEO "${category.nameCategory}": 
						<fmt:formatNumber value="${productCounts[category.idCategory]}" type="number"
							groupingUsed="true" />
					</h3>
					<!-- store products -->
					<div class="row">
						<!-- product -->

						<c:if test="${productsByCategory.size()>0}">
							<ul class="thumbnails">
								<c:forEach var="p" items="${productsByCategory}" varStatus="loop">

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
													${p.getFormattedCalPrice()}
													<del class="product-old-price">
														${p.getFormattedSalePrice()}</del>
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

					<!-- store bottom filter -->
					<div class="store-filter clearfix">

						<ul class="store-pagination">
							<li class="active">1</li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#"><i class="fa fa-angle-right"></i></a></li>
						</ul>
					</div>
					<!-- /store bottom filter -->
				</div>
				<!-- /STORE -->
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /SECTION -->
	

    <!-- Include footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>

    <!-- JavaScript links (same as in store.jsp) -->

</body>
</html>
