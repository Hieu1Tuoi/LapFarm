<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/WEB-INF/views/layouts/user-header.jsp" %>
<%@ include file="/WEB-INF/views/layouts/user-breadcumb.jsp" %>

<div class="container">
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
                                <img src="${image.imageUrl}" alt="${product.nameProduct}" class="img-fluid">
                            </div>
                        </c:forEach>
                    </c:if>
                    <!-- Nếu không có hình ảnh -->
                    <c:if test="${empty product.images}">
                        <div class="product-preview">
                            <img src="/LapFarm/resources/img/default-product.jpg" alt="Default Product" class="img-fluid">
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
                                <img src="${image.imageUrl}" alt="${product.nameProduct}" class="img-thumbnail">
                            </div>
                        </c:forEach>
                    </c:if>
                    <!-- Nếu không có hình ảnh -->
                    <c:if test="${empty product.images}">
                        <div class="product-thumbnail">
                            <img src="/LapFarm/resources/img/default-thumbnail.jpg" alt="Default Thumbnail" class="img-thumbnail">
                        </div>
                    </c:if>
                </div>
            </div>
            <!-- /Product thumb imgs -->

            <!-- Thông tin chi tiết sản phẩm -->
            <div class="col-md-5">
                <div class="product-details">
                    <h1>${product.nameProduct}</h1>
                    <p><strong>Danh mục:</strong> ${product.category.nameCategory}</p>
                    <p><strong>Mô tả:</strong> ${product.description}</p>
                    <p><strong>Trạng thái:</strong> ${product.state}</p>
                    <h2>
                        <strong>Giá:</strong>
                        <fmt:formatNumber value="${product.calPrice()}" type="number" groupingUsed="true" /> ₫
                    </h2>
                    <c:if test="${product.discount > 0}">
                        <del class="text-muted">
                            <fmt:formatNumber value="${product.calSalePrice()}" type="number" groupingUsed="true" /> ₫
                        </del>
                    </c:if>
                    
                </div>
                <div class="product-rating">
                					<p><strong>Đánh giá:</strong>
									<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
										class="fa fa-star"></i> <i class="fa fa-star"></i> <i
										class="fa fa-star"></i>
								</div>
                <div class="product-btns mt-4">
                    <button class="btn btn-danger btn-lg">
                        <i class="fa fa-shopping-cart"></i> Thêm vào giỏ hàng
                    </button>
                    <button class="btn btn-outline-secondary btn-lg">
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
							<li><a data-toggle="tab" href="#tab3">Reviews (3)</a></li>
						</ul>
						<!-- /product tab nav -->

						<!-- product tab content -->
						<div class="tab-content">
							<!-- tab1  -->
							<div id="tab1" class="tab-pane fade in active">
								<div class="row">
									<div class="col-md-12">
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit, sed do eiusmod tempor incididunt ut labore et dolore
											magna aliqua. Ut enim ad minim veniam, quis nostrud
											exercitation ullamco laboris nisi ut aliquip ex ea commodo
											consequat. Duis aute irure dolor in reprehenderit in
											voluptate velit esse cillum dolore eu fugiat nulla pariatur.
											Excepteur sint occaecat cupidatat non proident, sunt in culpa
											qui officia deserunt mollit anim id est laborum.</p>
									</div>
								</div>
							</div>
							<!-- /tab1  -->

							<!-- tab2  -->
							<div id="tab2" class="tab-pane fade in">
								<div class="row">
									<div class="col-md-12">
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit, sed do eiusmod tempor incididunt ut labore et dolore
											magna aliqua. Ut enim ad minim veniam, quis nostrud
											exercitation ullamco laboris nisi ut aliquip ex ea commodo
											consequat. Duis aute irure dolor in reprehenderit in
											voluptate velit esse cillum dolore eu fugiat nulla pariatur.
											Excepteur sint occaecat cupidatat non proident, sunt in culpa
											qui officia deserunt mollit anim id est laborum.</p>
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
												<span>4.5</span>
												<div class="rating-stars">
													<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
														class="fa fa-star"></i> <i class="fa fa-star"></i> <i
														class="fa fa-star-o"></i>
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
														<div style="width: 80%;"></div>
													</div> <span class="sum">3</span>
												</li>
												<li>
													<div class="rating-stars">
														<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
															class="fa fa-star"></i> <i class="fa fa-star"></i> <i
															class="fa fa-star-o"></i>
													</div>
													<div class="rating-progress">
														<div style="width: 60%;"></div>
													</div> <span class="sum">2</span>
												</li>
												<li>
													<div class="rating-stars">
														<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
															class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i
															class="fa fa-star-o"></i>
													</div>
													<div class="rating-progress">
														<div></div>
													</div> <span class="sum">0</span>
												</li>
												<li>
													<div class="rating-stars">
														<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
															class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i
															class="fa fa-star-o"></i>
													</div>
													<div class="rating-progress">
														<div></div>
													</div> <span class="sum">0</span>
												</li>
												<li>
													<div class="rating-stars">
														<i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i
															class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i
															class="fa fa-star-o"></i>
													</div>
													<div class="rating-progress">
														<div></div>
													</div> <span class="sum">0</span>
												</li>
											</ul>
										</div>
									</div>
									<!-- /Rating -->

									<!-- Reviews -->
									<div class="col-md-6">
										<div id="reviews">
											<ul class="reviews">
												<li>
													<div class="review-heading">
														<h5 class="name">John</h5>
														<p class="date">27 DEC 2018, 8:0 PM</p>
														<div class="review-rating">
															<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
																class="fa fa-star"></i> <i class="fa fa-star"></i> <i
																class="fa fa-star-o empty"></i>
														</div>
													</div>
													<div class="review-body">
														<p>Lorem ipsum dolor sit amet, consectetur adipisicing
															elit, sed do eiusmod tempor incididunt ut labore et
															dolore magna aliqua</p>
													</div>
												</li>
												<li>
													<div class="review-heading">
														<h5 class="name">John</h5>
														<p class="date">27 DEC 2018, 8:0 PM</p>
														<div class="review-rating">
															<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
																class="fa fa-star"></i> <i class="fa fa-star"></i> <i
																class="fa fa-star-o empty"></i>
														</div>
													</div>
													<div class="review-body">
														<p>Lorem ipsum dolor sit amet, consectetur adipisicing
															elit, sed do eiusmod tempor incididunt ut labore et
															dolore magna aliqua</p>
													</div>
												</li>
												<li>
													<div class="review-heading">
														<h5 class="name">John</h5>
														<p class="date">27 DEC 2018, 8:0 PM</p>
														<div class="review-rating">
															<i class="fa fa-star"></i> <i class="fa fa-star"></i> <i
																class="fa fa-star"></i> <i class="fa fa-star"></i> <i
																class="fa fa-star-o empty"></i>
														</div>
													</div>
													<div class="review-body">
														<p>Lorem ipsum dolor sit amet, consectetur adipisicing
															elit, sed do eiusmod tempor incididunt ut labore et
															dolore magna aliqua</p>
													</div>
												</li>
											</ul>
											<ul class="reviews-pagination">
												<li class="active">1</li>
												<li><a href="#">2</a></li>
												<li><a href="#">3</a></li>
												<li><a href="#">4</a></li>
												<li><a href="#"><i class="fa fa-angle-right"></i></a></li>
											</ul>
										</div>
									</div>
									<!-- /Reviews -->

									<!-- Review Form -->
									<div class="col-md-3">
										<div id="review-form">
											<form class="review-form">
												<input class="input" type="text" placeholder="Your Name">
												<input class="input" type="email" placeholder="Your Email">
												<textarea class="input" placeholder="Your Review"></textarea>
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
                            <img src="${product.image}" alt="${product.nameProduct}">
                        </div>
                        <div class="product-body">
                            <p class="product-category">${product.categoryName}</p>
                            <h3 class="product-name">
                                <a href="product-detail/${product.idProduct}">${product.nameProduct}</a>
                            </h3>
                            <h4 class="product-price">
                                <fmt:formatNumber value="${product.salePrice}" type="number" groupingUsed="true"/> ₫
                                <del class="product-old-price">
                                    <fmt:formatNumber value="${product.originalPrice}" type="number" groupingUsed="true"/> ₫
                                </del>
                            </h4>
                        </div>
                    </div>
                </div>
            </c:forEach>
             <!-- Nút "Xem tất cả sản phẩm liên quan" -->
            <div class="col-md-12 text-center">
                <a href="related-products/${product.brand.idBrand}" class="btn btn-danger">Xem tất cả</a>
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
							<input class="input" type="email" placeholder="Enter Your Email">
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

<%@ include file="/WEB-INF/views/layouts/user-footer.jsp" %>
<script src="<c:url value='/resources/js/jquery.min.js' />"></script>
	<script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/resources/js/slick.min.js' />"></script>
	<script src="<c:url value='/resources/js/nouislider.min.js' />"></script>
	<script src="<c:url value='/resources/js/jquery.zoom.min.js' />"></script>
	<script src="<c:url value='/resources/js/main.js' />"></script>
