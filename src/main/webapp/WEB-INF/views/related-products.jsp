<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-breadcumb.jsp"%>
<%@ include file="/WEB-INF/views/layouts/user-aside.jsp"%>

<!-- SECTION -->
<div id="store" class="col-md-9">
    <h3>
        Sản phẩm liên quan: <fmt:formatNumber value="${relatedProducts.size()}" type="number" groupingUsed="true" />
    </h3>

    <!-- store products -->
    <div class="row">
        <!-- product -->
        <c:if test="${relatedProducts.size() > 0}">
            <ul class="thumbnails">
                <c:forEach var="p" items="${relatedProducts}" varStatus="loop">
                    <div class="col-md-4 col-xs-6">
                        <div class="product">
                            <div class="product-img">
                                <a href="product-detail/${p.idProduct}">
                                    <!-- Hiển thị hình ảnh -->
                                    <c:choose>
                                        <c:when test="${not empty p.image}">
                                            <img src="${p.image}" alt="Product Image">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="/LapFarm/resources/img/default-product.png" alt="Default Image">
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </div>
                            <div class="product-body">
                                <p class="product-category">${p.categoryName}</p>
                                <h4 class="product-name">
                                    <a href="product-detail/${p.idProduct}" title="${p.nameProduct}">
                                        ${p.nameProduct}
                                    </a>
                                </h4>
                                <h6 class="product-price">
                                    <fmt:formatNumber value="${p.salePrice}" type="number" groupingUsed="true" /> ₫
                                    <del class="product-old-price">
                                        <fmt:formatNumber value="${p.originalPrice}" type="number" groupingUsed="true" /> ₫
                                    </del>
                                </h6>
                            </div>
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

                    <!-- Tạo hàng mới sau mỗi 3 sản phẩm -->
                    <c:if test="${(loop.index + 1) % 3 == 0 && (loop.index + 1) < relatedProducts.size()}">
                        </ul>
                        <ul class="thumbnails">
                    </c:if>
                </c:forEach>
            </ul>
        </c:if>

        <!-- Hiển thị thông báo nếu không có sản phẩm -->
        <c:if test="${relatedProducts.size() == 0}">
            <div class="col-md-12 text-center">
                <p>Không có sản phẩm liên quan nào.</p>
            </div>
        </c:if>
    </div>
    <!-- /store products -->
</div>
<!-- /SECTION -->

<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>
