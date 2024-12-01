<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-breadcumb.jsp"%>

<style>
/* Định dạng nút "Hết hàng" */
.add-to-cart-btn.disabled {
    background-color: #d3d3d3;  /* Màu xám */
    color: #808080;             /* Màu chữ xám */
    cursor: not-allowed;        /* Hiển thị con trỏ không cho phép khi hover */
}

.add-to-cart-btn.disabled i {
    color: #808080;             /* Màu xám cho biểu tượng */
}

</style>
<div class="container">
	<h1>${Cart.size() }</h1>
	<!-- Kiểm tra nếu không có sản phẩm -->
	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger text-center">
			<h2>${errorMessage}</h2>
		</div>
	</c:if>

	<!-- Hiển thị thông tin sản phẩm -->
	<c:if test="${not empty product}">
		<div class="row align-items-center">
			<!-- Product main img -->
			<div class="col-md-6 col-md-push-1">
				<div id="product-main-img">
					<c:if test="${not empty product.images}">
						<!-- Hiển thị ảnh chính -->
						<c:forEach var="image" items="${product.images}">
							<div class="product-preview">
								<img src="${image.imageUrl}" alt="/LapFarm/resources/img/soicodoc.jpg"
									class="img-fluid">
							</div>
						</c:forEach>
					</c:if>
					<!-- Nếu không có hình ảnh -->
					<c:if test="${empty product.images}">
						<div class="product-preview">
							<img src="/LapFarm/resources/img/default-product.jpg"
								alt="/LapFarm/resources/img/soicodoc.jpg" class="img-fluid">
						</div>
					</c:if>
				</div>
			</div>
			<!-- /Product main img -->

			<!-- Product thumb imgs -->
			<div class="col-md-1 col-md-pull-6">
				<div id="product-imgs">
					<c:if test="${not empty product.images}">
						<!-- Hiển thị thumbnails -->
						<c:forEach var="image" items="${product.images}">
							<div class="product-thumbnail">
								<img src="${image.imageUrl}" alt="/LapFarm/resources/img/soicodoc.jpg"
									class="img-thumbnail">
							</div>
						</c:forEach>
					</c:if>
					<!-- Nếu không có hình ảnh -->
					<c:if test="${empty product.images}">
						<div class="product-thumbnail">
							<img src="/LapFarm/resources/img/default-thumbnail.jpg"
								alt="/LapFarm/resources/img/soicodoc.jpg" class="img-thumbnail">
						</div>
					</c:if>
				</div>
			</div>
			<!-- /Product thumb imgs -->

			<!-- Thông tin chi tiết sản phẩm -->
			<div class="col-md-5">
				<div class="product-details">
					<h1>${product.nameProduct}</h1>
					<p>
						<strong>Danh mục:</strong> ${product.category.nameCategory}
					</p>
					<p>
						<strong>Mô tả:</strong> ${product.description}
					</p>
					<p>
						<strong>Trạng thái:</strong> ${product.state}
					</p>
					<h2>
						<strong>Giá:</strong>
						<fmt:formatNumber value="${product.calPrice()}" type="number"
							groupingUsed="true" />
						₫
					</h2>
					<c:if test="${product.discount > 0}">
						<del class="text-muted">
							<fmt:formatNumber value="${product.calSalePrice()}" type="number"
								groupingUsed="true" />
							₫
						</del>
					</c:if>

				</div>
				<div class="product-rating">
					<p>
						<strong>Đánh giá:</strong> <i class="fa fa-star"></i> <i
							class="fa fa-star"></i> <i class="fa fa-star"></i> <i
							class="fa fa-star"></i> <i class="fa fa-star"></i>
				</div>
				<div class="product-btns mt-4">
					<form action="addCart/${product.idProduct}" method="GET"
						style="display: inline-block;">
						<c:choose>
							<c:when test="${product.quantity > 0}">
								<form action="addCart/${product.idProduct}" method="GET">
									<button class="btn btn-danger btn-lg">
										<i class="fa fa-shopping-cart"></i> Thêm vào giỏ hàng
									</button>
								</form>
							</c:when>
							<c:otherwise>
								<button type="button"
									class="add-to-cart-btn btn btn-danger btn-lg disabled" disabled>
									<i class="fa fa-ban"></i> Hết hàng
								</button>
							</c:otherwise>
						</c:choose>


					</form>
					<button class="btn btn-outline-secondary btn-lg"
						style="display: inline-block;">
						<i class="fa fa-heart-o"></i> Thêm yêu thích
					</button>
				</div>

			</div>
		</div>
	</c:if>
	<!-- Product tab -->
	<div class="col-md-12">
		<div id="product-tab">
			<!-- product tab nav -->
			<ul class="tab-nav">
				<li class="active"><a data-toggle="tab" href="#tab1">Description</a></li>
				<li><a data-toggle="tab" href="#tab2">Details</a></li>
				<li><a data-toggle="tab" href="#tab3"> Reviews <span
						class="badge">${totalReviews}</span>
				</a></li>
			</ul>
			<!-- /product tab nav -->

			<!-- product tab content -->
			<div class="tab-content">
				<!-- tab1  -->
				<div id="tab1" class="tab-pane fade in active">
					<div class="row">
						<div class="col-md-12">
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
								sed do eiusmod tempor incididunt ut labore et dolore magna
								aliqua. Ut enim ad minim veniam, quis nostrud exercitation
								ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
								aute irure dolor in reprehenderit in voluptate velit esse cillum
								dolore eu fugiat nulla pariatur. Excepteur sint occaecat
								cupidatat non proident, sunt in culpa qui officia deserunt
								mollit anim id est laborum.</p>
						</div>
					</div>
				</div>
				<!-- /tab1  -->

				<!-- tab2  -->
				<div id="tab2" class="tab-pane fade in">
					<div class="row">
						<div class="col-md-12">
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
								sed do eiusmod tempor incididunt ut labore et dolore magna
								aliqua. Ut enim ad minim veniam, quis nostrud exercitation
								ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
								aute irure dolor in reprehenderit in voluptate velit esse cillum
								dolore eu fugiat nulla pariatur. Excepteur sint occaecat
								cupidatat non proident, sunt in culpa qui officia deserunt
								mollit anim id est laborum.</p>
						</div>
					</div>
				</div>
				<!-- /tab2  -->

				<!-- tab3  -->
				<div id="tab3" class="tab-pane fade in">
					<div class="row">
						<!-- Rating -->
						<div class="col-md-3">
							<div id="rating">
								<div class="rating-avg">
									<span>${ratingSummary.average}</span>
									<div class="rating-stars">
										<c:forEach var="star" begin="1" end="5">
											<c:choose>
												<c:when test="${star <= ratingSummary.average}">
													<i class="fa fa-star"></i>
												</c:when>
												<c:otherwise>
													<i class="fa fa-star-o"></i>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</div>
								</div>
								<ul class="rating">
									<li>
										<div class="rating-stars">
											<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
												class="fa fa-star"></i> <i class="fa fa-star"></i> <i
												class="fa fa-star"></i>
										</div>
										<div class="rating-progress">
											<div
												style="width: ${(ratingSummary.fiveStar / totalReviews) * 100}%;"></div>
										</div> <span class="sum">${ratingSummary.fiveStar}</span>
									</li>
									<li>
										<div class="rating-stars">
											<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
												class="fa fa-star"></i> <i class="fa fa-star"></i> <i
												class="fa fa-star-o"></i>
										</div>
										<div class="rating-progress">
											<div
												style="width: ${(ratingSummary.fourStar / totalReviews) * 100}%;"></div>
										</div> <span class="sum">${ratingSummary.fourStar}</span>
									</li>
									<li>
										<div class="rating-stars">
											<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
												class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i
												class="fa fa-star-o"></i>
										</div>
										<div class="rating-progress">
											<div
												style="width: ${(ratingSummary.threeStar / totalReviews) * 100}%;"></div>
										</div> <span class="sum">${ratingSummary.threeStar}</span>
									</li>
									<li>
										<div class="rating-stars">
											<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
												class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i
												class="fa fa-star-o"></i>
										</div>
										<div class="rating-progress">
											<div
												style="width: ${(ratingSummary.twoStar / totalReviews) * 100}%;"></div>
										</div> <span class="sum">${ratingSummary.twoStar}</span>
									</li>
									<li>
										<div class="rating-stars">
											<i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i
												class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i
												class="fa fa-star-o"></i>
										</div>
										<div class="rating-progress">
											<div
												style="width: ${(ratingSummary.oneStar / totalReviews) * 100}%;"></div>
										</div> <span class="sum">${ratingSummary.oneStar}</span>
									</li>
								</ul>
							</div>
						</div>
						<!-- /Rating -->



						<!-- Reviews Section -->
						<div class="col-md-6">
							<div id="reviews">
								<ul class="reviews">
									<c:forEach var="review" items="${reviews}">
										<li>
											<div class="review-heading">
												<h5 class="name">${review.user.fullName}</h5>
												<p class="date">
													<fmt:formatDate value="${review.reviewDate}"
														pattern="dd MMM yyyy, hh:mm a" />
												</p>
												<div class="review-rating">
													<c:forEach var="star" begin="1" end="5">
														<c:choose>
															<c:when test="${star <= review.rating}">
																<i class="fa fa-star"></i>
															</c:when>
															<c:otherwise>
																<i class="fa fa-star-o empty"></i>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</div>
											</div>
											<div class="review-body">
												<p>${review.comment}</p>
											</div>
										</li>
									</c:forEach>
									<c:if test="${empty reviews}">
										<li><p>Chưa có đánh giá nào cho sản phẩm này.</p></li>
									</c:if>
								</ul>

								<!-- Pagination -->
								<ul class="reviews-pagination">
									<c:forEach begin="1" end="${totalPages}" var="page">
										<li class="${page == currentPage ? 'active' : ''}"><a
											href="?page=${page}&pageSize=${pageSize}">${page}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<!-- /Reviews Section -->




<!-- Review Form -->
<div class="col-md-3">
    <div id="review-form">
        <!-- Kiểm tra nếu người dùng đã đăng nhập -->
        <c:if test="${not empty sessionScope.user}">
            <form class="review-form" id="reviewForm" method="post">
                <input type="hidden" name="productId" value="${product.idProduct}" />
                <textarea class="input" name="review" placeholder="Your Review" required></textarea>
                <div class="input-rating">
                    <span>Your Rating: </span>
                    <div class="stars">
                        <input id="star5" name="rating" value="5" type="radio" required><label for="star5"></label>
                        <input id="star4" name="rating" value="4" type="radio"><label for="star4"></label>
                        <input id="star3" name="rating" value="3" type="radio"><label for="star3"></label>
                        <input id="star2" name="rating" value="2" type="radio"><label for="star2"></label>
                        <input id="star1" name="rating" value="1" type="radio"><label for="star1"></label>
                    </div>
                </div>
                <button class="primary-btn" type="submit">Submit</button>
            </form>
            <!-- Thông báo thành công hoặc lỗi -->
            <div id="successMessage" style="display:none;" class="alert alert-success"></div>
            <div id="errorMessage" style="display:none;" class="alert alert-danger"></div>
        </c:if>

        <!-- Nếu người dùng chưa đăng nhập -->
        <c:if test="${empty sessionScope.user}">
            <p>Bạn cần <a href="<c:url value='/login' />">đăng nhập</a> để gửi đánh giá.</p>
        </c:if>
    </div>
</div>
<!-- /Review Form -->

<!-- Thêm JavaScript ở cuối trang, trước thẻ </body> -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    // Khi người dùng submit form
    $('#reviewForm').submit(function(event) {
        event.preventDefault();  // Ngăn không cho form gửi theo cách thông thường (reload trang)

        // Lấy dữ liệu từ form
        var formData = $(this).serialize();  // Serialize tất cả các trường trong form

        // Gửi dữ liệu form bằng AJAX
        $.ajax({
            url: '/product-detail/' + $('input[name="productId"]').val(),  // URL gửi tới server
            method: 'POST',
            data: formData,  // Dữ liệu gửi lên server
            success: function(response) {
                // Kiểm tra phản hồi từ server và hiển thị thông báo thành công
                if (response.success) {
                    $('#successMessage').html(response.success).show();  // Hiển thị thông báo thành công
                    $('#errorMessage').hide();  // Ẩn thông báo lỗi
                } else {
                    $('#errorMessage').html(response.error).show();  // Hiển thị thông báo lỗi
                    $('#successMessage').hide();  // Ẩn thông báo thành công
                }
            },
            error: function(xhr, status, error) {
                // Nếu có lỗi xảy ra trong quá trình gửi dữ liệu
                $('#errorMessage').html('Đã xảy ra lỗi trong quá trình gửi đánh giá.').show();
                $('#successMessage').hide();
            }
        });
    });
});
</script>

						<!-- Review Form -->
						<div class="col-md-3">
							<div id="review-form">
								<!-- Kiểm tra nếu người dùng đã đăng nhập -->
								<c:if test="${not empty sessionScope.user}">
									<form class="review-form" action="/submit-review" method="post">

										<textarea class="input" name="review"
											placeholder="Your Review" required></textarea>
										<div class="input-rating">
											<span>Your Rating: </span>
											<div class="stars">
												<input id="star5" name="rating" value="5" type="radio"><label
													for="star5"></label> <input id="star4" name="rating"
													value="4" type="radio"><label for="star4"></label>
												<input id="star3" name="rating" value="3" type="radio"><label
													for="star3"></label> <input id="star2" name="rating"
													value="2" type="radio"><label for="star2"></label>
												<input id="star1" name="rating" value="1" type="radio"><label
													for="star1"></label>
											</div>
										</div>
										<button class="primary-btn">Submit</button>
									</form>
								</c:if>
								<!-- Nếu người dùng chưa đăng nhập -->
								<c:if test="${empty sessionScope.user}">
									<p>
										Bạn cần <a href="<c:url value='/login' />">đăng nhập</a> để
										gửi đánh giá.
									</p>
								</c:if>
							</div>
						</div>
						<!-- /Review Form -->


					</div>
				</div>
				<!-- /tab3  -->
			</div>
			<!-- /product tab content  -->
		</div>
	</div>
	<!-- /product tab -->
	<div class="section">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="section-title text-center">
						<h3 class="title">Related Products</h3>
					</div>
				</div>

				<c:forEach var="product" items="${relatedProducts}">
					<div class="col-md-3 col-xs-6">
						<div class="product">
							<div class="product-img">
								<img src="${product.image}" alt="/LapFarm/resources/img/soicodoc.jpg">
							</div>
							<div class="product-body">
								<p class="product-category">${product.categoryName}</p>
								<h3 class="product-name">
									<a href="product-detail/${product.idProduct}">${product.nameProduct}</a>
								</h3>
								<h4 class="product-price">
									<fmt:formatNumber value="${product.salePrice}" type="number"
										groupingUsed="true" />
									₫
									<del class="product-old-price">
										<fmt:formatNumber value="${product.originalPrice}"
											type="number" groupingUsed="true" />
										₫
									</del>
								</h4>
							</div>
						</div>
					</div>
				</c:forEach>
				<!-- Nút "Xem tất cả sản phẩm liên quan" -->
				<div class="col-md-12 text-center">
					<a href="related-products/${product.brand.idBrand}"
						class="btn btn-danger">Xem tất cả</a>
				</div>

			</div>
			<!-- NEWSLETTER -->
			<div id="newsletter" class="section">
				<!-- container -->
				<div class="container">
					<!-- row -->
					<div class="row">
						<div class="col-md-12">
							<div class="newsletter">
								<p>
									Sign Up for the <strong>NEWSLETTER</strong>
								</p>
								<form>
									<input class="input" type="email"
										placeholder="Enter Your Email">
									<button class="newsletter-btn">
										<i class="fa fa-envelope"></i> Subscribe
									</button>
								</form>
								<ul class="newsletter-follow">
									<li><a href="#"><i class="fa fa-facebook"></i></a></li>
									<li><a href="#"><i class="fa fa-twitter"></i></a></li>
									<li><a href="#"><i class="fa fa-instagram"></i></a></li>
									<li><a href="#"><i class="fa fa-pinterest"></i></a></li>
								</ul>
							</div>
						</div>
					</div>
					<!-- /row -->
				</div>
				<!-- /container -->
			</div>
			<!-- /NEWSLETTER -->
		</div>


	</div>

	<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>
	<script src="<c:url value='/resources/js/jquery.min.js' />"></script>
	<script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/resources/js/slick.min.js' />"></script>
	<script src="<c:url value='/resources/js/nouislider.min.js' />"></script>
	<script src="<c:url value='/resources/js/jquery.zoom.min.js' />"></script>
	<script src="<c:url value='/resources/js/main.js' />"></script>

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