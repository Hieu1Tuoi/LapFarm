<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>

<!-- JavaScript để kiểm tra giá trị nhập -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.querySelector("form");
        form.addEventListener("submit", function (event) {
            const fieldsToCheck = [
                "quantity",
                "discountPercent",
                "purchasePrice",
                "salePrice"
            ];

            let isValid = true;
            fieldsToCheck.forEach(fieldId => {
                const field = document.getElementById(fieldId);
                const value = parseFloat(field.value);
                if (value < 0) {
                    isValid = false;
                    alert(`Trường ${fieldId} không được nhập giá trị âm.`);
                    field.focus();
                }
            });

            if (!isValid) {
                event.preventDefault(); // Ngăn form submit
            }
        });
    });
</script>


<div class="content-wrapper">
    <!-- Content Header -->
    <section class="content-header">
        <h1>Thêm sản phẩm mới</h1>
        <ol class="breadcrumb">
            <li><a href="admin/home"><i class="fa fa-dashboard"></i>Trang chủ</a></li>
            <li><a href="admin/product?category=1">Sản phẩm</a></li>
            <li class="active">Thêm sản phẩm</li>
        </ol>
    </section>

    <section class="content">
    <!-- Hiển thị thông báo nếu có -->
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                <strong>${message}</strong> 
            </div>
        </c:if>
        <!-- Hiển thị thông báo lỗi nếu có -->
		<c:if test="${not empty error}">
			<div class="alert alert-error">
				<strong>${error}</strong>
			</div>
		</c:if>
        <div class="box">
            <div class="box-header with-border">
                <h3 class="box-title">Thông tin sản phẩm</h3>
            </div>
            <!-- Form upload -->
            <form method="POST" action="${pageContext.request.contextPath}/admin/product/add-product" enctype="multipart/form-data">
                <div class="box-body">
                    <!-- Tên sản phẩm -->
                    <div class="form-group">
                        <label for="nameProduct">Tên sản phẩm</label>
                        <input type="text" class="form-control" id="nameProduct" name="nameProduct" required>
                    </div>

                    <!-- Loại hàng -->
                    <div class="form-group">
                        <label for="categoryName">Tên loại hàng</label>
                        <select class="form-control" id="categoryName" name="categoryName" required>
                            <c:forEach var="c" items="${categories}">
                                <option value="${c.nameCategory}">${c.nameCategory}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Nhãn hàng -->
                    <div class="form-group">
                        <label for="brand">Nhãn hàng</label>
                        <select class="form-control" id="brand" name="brand" required>
                            <c:forEach var="b" items="${brands}">
                                <option value="${b.nameBrand}">${b.nameBrand}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Mô tả -->
                    <div class="form-group">
                        <label for="description">Mô tả</label>
                        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                    </div>

					<!-- Thông số kỹ thuật-->
                    <div class="form-group">
                        <label for="moreinfo">Thông số kỹ thuật</label>
                        <textarea class="form-control" id="moreinfo" name="moreinfo" rows="3" required></textarea>
                    </div>
                    
                    <!-- Số lượng -->
					<div class="form-group">
						<label for="quantity">Số lượng</label> <input type="number"
							class="form-control" id="quantity" name="quantity" min="0" max="100000" step="1" required>
					</div>

					<!-- Hệ số giảm giá -->
					<div class="form-group">
						<label for="discountPercent">Hệ số giảm giá</label> <input
							type="number" class="form-control" id="discountPercent"
							name="discountPercent" min="0" max="1" step="0.001" required>
					</div>


					<!-- Giá mua -->
					<div class="form-group">
                        <label for="purchasePrice">Giá mua</label>
                        <input type="number" class="form-control" id="purchasePrice" 
                        name="purchasePrice"  min="0" max="1000000000" step="1" required>
                    </div>

                    <!-- Giá niêm yết -->
                    <div class="form-group">
                        <label for="salePrice">Giá niêm yết</label>
                        <input type="number" class="form-control" id="salePrice" 
                        name="salePrice" min="0" max="1000000000" step="1" required>
                    </div>

                    <!-- Khuyến mãi -->
                    <div class="form-group">
                        <label for="promotion">Khuyến mãi liên quan</label>
                        <input type="text" class="form-control" id="promotion" name="promotion">
                    </div>

                    <!-- Trạng thái -->
                    <div class="form-group">
                        <label for="state">Trạng thái</label>
                        <select class="form-control" id="state" name="state" required>
                            <option value="Đang bán">Đang bán</option>
                            <option value="Sắp về hàng">Sắp về hàng</option>
                            <option value="Ngưng kinh doanh">Ngưng kinh doanh</option>
                        </select>
                    </div>

                    <!-- Ảnh sản phẩm -->
                    <div class="form-group">
                        <label for="productImages">Ảnh sản phẩm</label>
                        <input type="file" class="form-control" id="productImages" name="productImages" 
                               accept="image/*" multiple required>
                        <small class="form-text text-muted">Chọn tối đa 10 ảnh. Mỗi ảnh có dung lượng không vượt quá 5MB.</small>
                    </div>
                </div>

                <!-- Submit Button -->
                <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Thêm sản phẩm</button>
                </div>
            </form>
        </div>
    </section>
</div>

<%@ include file="/WEB-INF/views/admin/layout/adminFooter.jsp"%>
