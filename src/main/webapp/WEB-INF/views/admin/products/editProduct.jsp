<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>

<div class="content-wrapper">
	<!-- Content Header -->
	<section class="content-header">
		<h1>Sửa thông tin sản phẩm</h1>
		<ol class="breadcrumb">
			<li><a href="admin/home"><i class="fa fa-dashboard"></i>Trang chủ</a></li>
			<li><a href="admin/product?category=1">Sản phẩm</a></li>
			<li class="active">Sửa thông tin</li>
		</ol>
	</section>

	<section class="content">
		<!-- Hiển thị thông báo thành công nếu có -->
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
			<form method="POST"
				action="${pageContext.request.contextPath}/admin/product/edit-product/${product.idProduct}"
				enctype="multipart/form-data">
				<div class="box-body">
					<!-- Tên sản phẩm -->
					<div class="form-group">
						<label for="nameProduct">Tên sản phẩm</label> <input type="text"
							class="form-control" id="nameProduct" name="nameProduct"
							value="${product.nameProduct}"
							placeholder="Vui lòng nhập tên sản phẩm" required>
					</div>

					<!-- Loại hàng -->
					<div class="form-group">
						<label for="categoryName">Tên loại hàng</label> <select
							class="form-control" id="categoryName" name="categoryName"
							required>
							<c:forEach var="c" items="${categories}">
								<option value="${c.idCategory}"
									${c.idCategory == product.category.idCategory ? 'selected' : ''}>
									${c.nameCategory}</option>
							</c:forEach>
						</select>
					</div>

					<!-- Nhãn hàng -->
					<div class="form-group">
						<label for="brand">Nhãn hàng</label> <select class="form-control"
							id="brand" name="brand" required>
							<c:forEach var="b" items="${brands}">
								<option value="${b.idBrand}"
									${b.idBrand == product.brand.idBrand ? 'selected' : ''}>
									${b.nameBrand}</option>
							</c:forEach>
						</select>
					</div>

					<!-- Mô tả -->
					<div class="form-group">
						<label for="description">Mô tả</label>
						<textarea class="form-control" id="description" name="description"
							placeholder="Vui lòng mô tả sản phẩm" rows="3" required>${product.description}</textarea>
					</div>

					<!-- Thông số kỹ thuật -->
					<div class="form-group">
						<label for="moreinfo">Thông số kỹ thuật</label>
						<textarea class="form-control" id="moreinfo" name="moreinfo"
							placeholder="Vui lòng thông số kỹ thuật" rows="3" required>${productDetail.moreInfo}</textarea>
					</div>

					<!-- Số lượng -->
					<div class="form-group">
						<label for="quantity">Số lượng</label> <input type="number"
							class="form-control" id="quantity" name="quantity"
							value="${product.quantity }"
							placeholder="Vui lòng nhập số lượng sản phẩm" required>
					</div>

					<!-- Hệ số giảm giá -->
					<div class="form-group">
						<label for="discountPercent">Hệ số giảm giá</label> <input
							type="number" class="form-control" id="discountPercent"
							name="discountPercent" min="0" max="1" step="0.001"
							value="${product.discount != null ? product.discount : ''}"
							placeholder="Vui lòng hệ số giảm giá" required>
					</div>

					<!-- Giá mua -->
					<div class="form-group">
						<label for="purchasePrice">Giá mua</label> <input type="number"
							class="form-control" id="purchasePrice" name="purchasePrice"
							min="0" max="1000000000" step="1"
							value="${product.calOriginalPrice()}"
							placeholder="Vui lòng nhập giá gốc" required>
					</div>

					<!-- Giá niêm yết -->
					<div class="form-group">
						<label for="salePrice">Giá niêm yết</label> <input type="number"
							class="form-control" id="salePrice" name="salePrice" min="0"
							max="1000000000" step="1" value="${product.calSalePrice()}"
							placeholder="Vui lòng nhập giá niêm yết (chưa gồm giảm giá)"
							required>
					</div>

					<!-- Khuyến mãi -->
					<div class="form-group">
						<label for="promotion">Khuyến mãi liên quan</label> <input
							type="text" class="form-control" id="promotion" name="promotion"
							value="${product.relatedPromotions }"
							placeholder="Vui lòng nhập khuyến mãi khác (nếu có)">
					</div>

					<!-- Trạng thái -->
					<div class="form-group">
						<label for="state">Trạng thái</label> <select class="form-control"
							id="state" name="state" required>
							<option value="Đang bán"
								${'Đang bán' == product.state ? 'selected' : ''}>Đang
								bán</option>
							<option value="Sắp về hàng"
								${'Sắp về hàng' == product.state ? 'selected' : ''}>Sắp
								về hàng</option>
							<option value="Ngưng kinh doanh"
								${'Ngưng kinh doanh' == product.state ? 'selected' : ''}>Ngưng
								kinh doanh</option>
							<option value="Hết hàng"
								${'Hết hàng' == product.state ? 'selected' : ''}>Hết
								hàng</option>
						</select>
					</div>

					<script>
                        // Kiểm tra ảnh trước khi cho phép tải lên
                        function validateImages(input) {
                            const maxFiles = 10; // Số lượng ảnh tối đa
                            const maxFileSizeMB = 5; // Dung lượng tối đa mỗi ảnh (MB)
                            const files = input.files;

                            // Kiểm tra tổng số ảnh
                            if (files.length > maxFiles) {
                                alert(`Bạn chỉ được chọn tối đa ${maxFiles} ảnh.`);
                                input.value = ""; // Reset input
                                return false;
                            }

                            // Kiểm tra dung lượng từng ảnh
                            for (let i = 0; i < files.length; i++) {
                                if (files[i].size > maxFileSizeMB * 1024 * 1024) {
                                    alert(`Ảnh "${files[i].name}" vượt quá dung lượng tối đa ${maxFileSizeMB}MB.`);
                                    input.value = ""; // Reset input
                                    return false;
                                }
                            }

                            return true;
                        }

                        // Hàm xóa ảnh khỏi giao diện và cập nhật danh sách ảnh bị xóa
                        function removeImage(button, imageId) {
                            // Tìm thẻ chứa ảnh
                            const imageWrapper = button.parentElement;

                            if (imageWrapper) {
                                // Xóa ảnh khỏi giao diện
                                imageWrapper.remove();

                                // Lấy input ẩn để lưu ID ảnh bị xóa
                                const deletedImagesInput = document.getElementById('deletedImages');
                                if (deletedImagesInput) {
                                    // Lấy giá trị hiện tại của deletedImagesInput
                                    let currentValue = deletedImagesInput.value;
                                    if (currentValue) {
                                        // Nếu đã có giá trị, thêm ID ảnh vào danh sách
                                        currentValue += imageId + ",";
                                    } else {
                                        // Nếu chưa có giá trị, khởi tạo bằng ID ảnh đầu tiên
                                        currentValue = imageId + ",";
                                    }

                                    // Cập nhật lại giá trị vào input ẩn
                                    deletedImagesInput.value = currentValue;

                                    // Kiểm tra lại giá trị của deletedImagesInput
                                    console.log(deletedImagesInput.value); // Dùng console.log để xem giá trị

                                    return true; // Return true nếu xóa thành công
                                }
                            }

                            return false; // Return false nếu không tìm thấy ảnh hoặc không xóa được
                        }
                    </script>
				</div>

				<!-- Ảnh sản phẩm -->
				<div class="form-group">
					<label for="productImages">Ảnh sản phẩm</label>

					<!-- Hiển thị danh sách ảnh hiện có -->
					<div id="current-images" class="mb-3">
						<c:forEach var="image" items="${product.images}">
							<div class="image-wrapper"
								style="display: inline-block; position: relative; margin: 5px;">
								<img src="${image.imageUrl}" alt="Product Image"
									style="width: 100px; height: 100px; object-fit: cover; border: 1px solid #ccc;">
								<button type="button" class="btn btn-danger btn-sm remove-image"
									style="position: absolute; top: 0; right: 0; border-radius: 50%;"
									onclick="removeImage(this, '${image.idImage}')">&times;</button>
							</div>
						</c:forEach>
					</div>
					<!-- Input ẩn để thêo ảnh đã xóa -->
					<input type="hidden" id="deletedImages" name="deletedImages"
						value="">
					<!-- Input để thêm ảnh mới -->
					<input type="file" class="form-control" id="productImages"
						name="productImages" accept="image/*" multiple> <small
						class="form-text text-muted">Chọn tối đa 10 ảnh. Mỗi ảnh
						có dung lượng không vượt quá 5MB.</small>
				</div>


				<div class="box-footer">
					<button type="submit" class="btn btn-primary">Lưu</button>
					<a
						href="${pageContext.request.contextPath}/admin/product/list-product"
						class="btn btn-default">Hủy bỏ</a>
				</div>
			</form>
		</div>
	</section>
</div>

<%@ include file="/WEB-INF/views/admin/layout/adminFooter.jsp"%>
