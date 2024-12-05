<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Sản Phẩm - Đánh Giá</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />">
</head>
<body>
    
    <div class="container">
    <header>
        <h1>Chi Tiết Sản Phẩm</h1>
        <nav>
            <a href="<c:url value='/product-detail/${product.idProduct}' />">Quay lại sản phẩm</a>
        </nav>
    </header>
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
								<img src="${image.imageUrl}" alt="Ảnh máy tính" onerror="this.src='/LapFarm/resources/img/soicodoc.jpg'"
									class="img-fluid">
							</div>
						</c:forEach>
					</c:if>
					<!-- Nếu không có hình ảnh -->
					<c:if test="${empty product.images}">
						<div class="product-preview">
							<img src="/LapFarm/resources/img/default-product.jpg"
								alt="Ảnh máy tính"  onerror="this.src='/LapFarm/resources/img/soicodoc.jpg'" class="img-fluid">
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
								<img src="${image.imageUrl}" alt="Ảnh máy tính"  onerror="this.src='/LapFarm/resources/img/soicodoc.jpg'"
									class="img-thumbnail">
							</div>
						</c:forEach>
					</c:if>
					<!-- Nếu không có hình ảnh -->
					<c:if test="${empty product.images}">
						<div class="product-thumbnail">
							<img src="/LapFarm/resources/img/default-thumbnail.jpg"
								alt="Ảnh máy tính"  onerror="this.src='/LapFarm/resources/img/soicodoc.jpg'" class="img-thumbnail">
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
						<strong>Đánh giá:
						<div class="rating-avg">
								    <span>${ratingSummary.average}</span>
								    <div class="rating-stars">
								        <c:forEach var="star" begin="1" end="5">
								            <c:choose>
								              
								                <c:when test="${star <= ratingSummary.average}">
								                    <i class="fa fa-star"></i>
								                </c:when>
								                <c:when test="${star - 0.5 <= ratingSummary.average && star > ratingSummary.average}">
								                    <i class="fa fa-star-half-o"></i>
								                </c:when>
								
								                <c:otherwise>
								                    <i class="fa fa-star-o"></i>
								                </c:otherwise>
								            </c:choose>
								        </c:forEach>
								    </div>
								</div>

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
					
				</div>

			</div>
		</div>
	</c:if>

        <!-- Reviews Section -->
        <div class="reviews-section">
            <h3>Đánh Giá Sản Phẩm</h3>

            <ul class="reviews">
                <c:forEach var="review" items="${reviews}">
                    <li>
                        <div class="review-heading">
                            <h5 class="name">${review.user.fullName}</h5>
                            <p class="date">
                                <fmt:formatDate value="${review.reviewDate}" pattern="dd MMM yyyy, hh:mm a" />
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
        </div>
        <!-- /Reviews Section -->

        <!-- Back to Product -->
        <div class="back-button">
            <a href="<c:url value='/product-detail/${product.idProduct}' />" class="btn btn-primary">Quay lại chi tiết sản phẩm</a>
        </div>
    </div>

    <footer>
        <p>&copy; 2024 LapFarm - All rights reserved</p>
    </footer>

    <script src="<c:url value='/resources/js/script.js' />"></script>
    <script src="<c:url value='/resources/js/jquery.min.js' />"></script>
	<script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/resources/js/slick.min.js' />"></script>
	<script src="<c:url value='/resources/js/nouislider.min.js' />"></script>
	<script src="<c:url value='/resources/js/jquery.zoom.min.js' />"></script>
	<script src="<c:url value='/resources/js/main.js' />"></script>
    
</body>
</html>
