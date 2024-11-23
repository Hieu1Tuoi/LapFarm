<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-breadcumb.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-aside.jsp"%>

<!-- STORE -->
<div id="store" class="col-md-9">
    <h2>Số sản phẩm: ${products != null ? products.size() : 0}</h2>
    <h3>
        TẤT CẢ SẢN PHẨM: 
        <fmt:formatNumber value="${totalQuantity}" type="number" groupingUsed="true" />
    </h3>

    <!-- Store products -->
    <div class="row">
        <c:if test="${not empty products}">
            <c:forEach var="p" items="${products}" varStatus="loop">
                <div class="col-md-4 col-xs-6">
                    <div class="product">
                        <!-- Product Image -->
                        <div class="product-img">
                            <a href="${pageContext.request.contextPath}/product/${p.idProduct}">
                                <c:choose>
                                    <c:when test="${not empty p.images}">
                                        <img src="${p.images[0].imageUrl}" alt="Product Image">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="/LapFarm/resources/img/default.jpg" alt="Default Image">
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            <div class="product-label">
                                <c:if test="${p.discount > 0}">
                                    <span class="sale">-${p.discount * 100}%</span>
                                </c:if>
                            </div>
                        </div>

                        <!-- Product Details -->
                        <div class="product-body">
                            <p class="product-category">${p.category.nameCategory}</p>
                            <h4 class="product-name">
                                <a href="${pageContext.request.contextPath}/product/${p.idProduct}" title="${p.nameProduct}">
                                    ${p.nameProduct}
                                </a>
                            </h4>
                            <h6 class="product-price">
                                <fmt:formatNumber value="${p.calPrice()}" type="number" groupingUsed="true" /> ₫
                                <c:if test="${p.calSalePrice() > 0}">
                                    <del class="product-old-price">
                                        <fmt:formatNumber value="${p.calSalePrice()}" type="number" groupingUsed="true" /> ₫
                                    </del>
                                </c:if>
                            </h6>
                            <div class="product-rating">
                                <!-- Static stars for rating -->
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                                <i class="fa fa-star"></i>
                            </div>
                            <div class="product-btns">
                                <button class="add-to-wishlist">
                                    <i class="fa fa-heart-o"></i> <span class="tooltipp">Thêm yêu thích</span>
                                </button>
                                <button class="add-to-compare">
                                    <i class="fa fa-exchange"></i> <span class="tooltipp">So sánh</span>
                                </button>
                                <button class="quick-view">
                                    <i class="fa fa-eye"></i> <span class="tooltipp">Xem nhanh</span>
                                </button>
                            </div>
                        </div>

                        <!-- Add to Cart Button -->
                        <div class="add-to-cart">
                            <button class="add-to-cart-btn">
                                <i class="fa fa-shopping-cart"></i> Thêm giỏ hàng
                            </button>
                        </div>
                    </div>
                </div>

                <!-- Break row after every 3 products -->
                <c:if test="${(loop.index + 1) % 3 == 0 && loop.index + 1 < products.size()}">
                    <div class="clearfix"></div>
                </c:if>
            </c:forEach>
        </c:if>
    </div>
    <!-- /Store products -->
</div>
<!-- /STORE -->

<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>
