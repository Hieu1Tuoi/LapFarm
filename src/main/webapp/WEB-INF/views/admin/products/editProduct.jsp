<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>

<div class="content-wrapper">
    <!-- Content Header -->
    <section class="content-header">
        <h1>Sửa thông tin sản phẩm</h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i>Trang chủ</a></li>
            <li><a href="">Sản phẩm</a></li>
            <li class="active">Sửa thông tin</li>
        </ol>
    </section>

    <section class="content">
    <!-- Hiển thị thông báo nếu có -->
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                <strong>${message}</strong> 
            </div>
        </c:if>
        <div class="box">
            <div class="box-header with-border">
                <h3 class="box-title">Thông tin sản phẩm</h3>
            </div>
            <!-- Form upload -->
            <form method="POST" action="${pageContext.request.contextPath}/admin/product/edit-product/${product.idProduct}" enctype="multipart/form-data">
                <div class="box-body">
                    <!-- Tên sản phẩm -->
                    <div class="form-group">
                        <label for="nameProduct">Tên sản phẩm</label>
                        <input type="text" class="form-control" id="nameProduct" name="nameProduct" value="${product.idProduct }" placeholder="Vui lòng nhập tên sản phẩm" required>
                    </div>

                    <!-- Loại hàng -->
                    <div class="form-group">
                        <label for="categoryName">Tên loại hàng</label>
                        <select class="form-control" id="categoryName" name="categoryName" required>
                            <c:forEach var="c" items="${categories}">
                                <option value="${c.nameCategory}">${c.getNameCategory(product.idCategory)}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Nhãn hàng -->
                    <div class="form-group">
                        <label for="brand">Nhãn hàng</label>
                        <select class="form-control" id="brand" name="brand" required>
                            <c:forEach var="b" items="${brands}">
                                <option value="${b.nameBrand}">${b.getNameBrand(product.nameBrand)}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Mô tả -->
                    <div class="form-group">
                        <label for="description">Mô tả</label>
                        <textarea class="form-control" id="description" name="description" value="${product.description }" placeholder="Vui lòng mô tả sản phẩm" rows="3" required></textarea>
                    </div>

					<!-- Thông số kỹ thuật-->
                    <div class="form-group">
                        <label for="moreinfo">Thông số kỹ thuật</label>
                        <textarea class="form-control" id="moreinfo" name="moreinfo" value="${productDetail.moreInfo }" placeholder="Vui lòng thông số kỹ thuật" rows="3" required></textarea>
                    </div>
                    
                    <!-- Số lượng -->
					<div class="form-group">
						<label for="quantity">Số lượng</label> <input type="number"
							class="form-control" id="quantity" name="quantity" value="${product.quantity }" placeholder="Vui lòng nhập số lượng sản phẩm" required>
					</div>

					<!-- Hệ số giảm giá -->
					<div class="form-group">
						<label for="discountPercent">Hệ số giảm giá</label> <input
							type="number" class="form-control" id="discountPercent"
							name="discountPercent" min="0" max="1" step="0.01" value="${product.discount }" placeholder="Vui lòng hệ số giảm giá" required>
					</div>


					<!-- Giá mua -->
					<div class="form-group">
                        <label for="purchasePrice">Giá mua</label>
                        <input type="number" class="form-control" id="purchasePrice" name="purchasePrice" value="${product.originalPrice }" placeholder="Vui lòng nhập giá gốc" required>
                    </div>

                    <!-- Giá niêm yết -->
                    <div class="form-group">
                        <label for="salePrice">Giá niêm yết</label>
                        <input type="number" class="form-control" id="salePrice" name="salePrice" value="${product.salePrice }" placeholder="Vui lòng nhập giá niêm yết (chưa gồm giảm giá)" required>
                    </div>

                    <!-- Khuyến mãi -->
                    <div class="form-group">
                        <label for="promotion">Khuyến mãi liên quan</label>
                        <input type="text" class="form-control" id="promotion" name="promotion" value="${product.relatedPromotions }" placeholder="Vui lòng nhập khuyến mãi khác (nếu có)">
                    </div>

                    <!-- Trạng thái -->
                    <div class="form-group">
                        <label for="state">Trạng thái</label>
                        <select class="form-control" id="state" name="state" value="${product.state }" required>
                            <option value="Đang bán">Đang bán</option>
                            <option value="Sắp về hàng">Sắp về hàng</option>
                            <option value="Ngưng kinh doanh">Ngưng kinh doanh</option>
                            <option value="Hết hàng">Hết hàng</option>
                        </select>
                    </div>

                    <!-- Ảnh sản phẩm -->
                    <div class="form-group">
                        <label for="productImages">Ảnh sản phẩm</label>
                        <input type="file" class="form-control" id="productImages" name="productImages"  value="${product.image }"
                               accept="image/*" multiple required>
                        <small class="form-text text-muted">Chọn tối đa 10 ảnh. Mỗi ảnh có dung lượng không vượt quá 5MB.</small>
                    </div>
                </div>

                <!-- Submit Button -->
                <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                </div>
            </form>
        </div>
    </section>
</div>

<%@ include file="/WEB-INF/views/admin/layout/adminFooter.jsp"%>
