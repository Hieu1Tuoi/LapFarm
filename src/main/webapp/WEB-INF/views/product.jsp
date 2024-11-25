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
                <div class="product-btns mt-4">
                    <button class="btn btn-primary btn-lg">
                        <i class="fa fa-shopping-cart"></i> Thêm vào giỏ hàng
                    </button>
                    <button class="btn btn-outline-secondary btn-lg">
                        <i class="fa fa-heart-o"></i> Thêm yêu thích
                    </button>
                </div>
            </div>
        </div>
    </c:if>
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
                <a href="/related-products/${product.brand.idBrand}" class="btn btn-primary">Xem tất cả</a>
        </div>
    </div>
</div>

   
</div>

<%@ include file="/WEB-INF/views/layouts/user-footer.jsp" %>
<script src="<c:url value='/resources/js/jquery.min.js' />"></script>
	<script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/resources/js/slick.min.js' />"></script>
	<script src="<c:url value='/resources/js/nouislider.min.js' />"></script>
	<script src="<c:url value='/resources/js/jquery.zoom.min.js' />"></script>
	<script src="<c:url value='/resources/js/main.js' />"></script>
