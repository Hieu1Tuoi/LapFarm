<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="section">
	<!-- container -->
	<div class="container">
		<!-- row -->
		<div class="row">
			<!-- ASIDE -->
			<div id="aside" class="col-md-3">
				<!-- aside Widget -->
				<div class="aside">

					<h3 class="aside-title">DANH MỤC</h3>

					<div class="category-filter">
						<c:forEach var="cate" items="${categories}">
							<div class="category-item">
								<!-- Thẻ <a> chứa liên kết động -->
								<a href="products-category?idCategory=${cate.idCategory}">
									${cate.nameCategory} <small>(${productCounts[cate.idCategory]})</small>
								</a>
							</div>
							<br>
						</c:forEach>
					</div>

				</div>
				<!-- /aside Widget -->

				<!-- aside Widget -->
				<div class="sidebar">
					<div class="filter-container">
						<h3>GIÁ</h3>
						<div class="slider-container">
							<input type="range" min="${priceMin - 1}" max="${priceMax + 1}" value="${priceMin - 1}"
								class="price-slider" id="minPriceSlider"> <input
								type="range" min="${priceMin - 1}" max="${priceMax + 1}"
								value="${priceMax + 1}" class="price-slider" id="maxPriceSlider">
						</div>
						<div class="price-container">
							<span id="minPriceLabel">0 ₫</span> <span id="maxPriceLabel">50,000,000
								₫</span>
						</div>
						<div class="button-container">
							<button class="apply-button" onclick="applyFilter()">Áp
								Dụng</button>
							<button class="cancel-button" onclick="resetFilter()">Hủy</button>
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
								<a href="products-brand?idBrand=${brand.idBrand}"
									class="brand-link" data-brand-id="${brand.idBrand}">
									${brand.nameBrand} <small>(${productCountsByBrand[brand.idBrand]})</small>
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
										<img src="${product.image}" alt="Product Image" onerror="this.src='/LapFarm/resources/img/soicodoc.jpg'">
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
									<a href="product-detail/${product.idProduct}">${product.nameProduct}</a>
								</h3>
								<h4 class="product-price">
									<td><fmt:formatNumber value="${product.calPrice()}"
											type="number" groupingUsed="true" /> ₫</td>
									<del class="product-old-price">
										<td><fmt:formatNumber value="${product.calSalePrice()}"
												type="number" groupingUsed="true" /> ₫</td>
									</del>
								</h4>
							</div>
						</div>
					</c:forEach>



				</div>
				<!-- /aside Widget -->
			</div>
			<!-- /ASIDE -->