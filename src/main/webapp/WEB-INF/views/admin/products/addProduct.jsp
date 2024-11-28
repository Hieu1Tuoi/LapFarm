<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>

<div class="content-wrapper">
    <!-- Content Header -->
    <section class="content-header">
        <h1>Thêm sản phẩm mới</h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i>Trang chủ</a></li>
            <li class="active">Thêm sản phẩm</li>
        </ol>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <h3 class="box-title">Thông tin sản phẩm</h3>
            </div>
            <form>
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
                            <c:forEach var="category" items="${p.categoryName}">
                                <option value="${category}">${category}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Nhãn hàng -->
                    <div class="form-group">
                        <label for="brand">Nhãn hàng</label>
                        <select class="form-control" id="brand" name="brand" required>
                            <c:forEach var="brand" items="${p.brand}">
                                <option value="${brand}">${brand}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Danh sách sản phẩm -->
                    <div class="form-group">
                        <label for="relatedProducts">Sản phẩm liên quan</label>
                        <select class="form-control" id="relatedProducts" name="relatedProducts" required>
                            <c:forEach var="product" items="${p.nameProduct}">
                                <option value="${product}">${product}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Mô tả -->
                    <div class="form-group">
                        <label for="description">Mô tả</label>
                        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                    </div>

                    <!-- Số lượng -->
                    <div class="form-group">
                        <label for="quantity">Số lượng</label>
                        <input type="number" class="form-control" id="quantity" name="quantity" required>
                    </div>

                    <!-- Phần trăm giảm giá -->
                    <div class="form-group">
                        <label for="discountPercent">Phần trăm giảm giá</label>
                        <input type="number" class="form-control" id="discountPercent" name="discountPercent" min="0" max="100" required>
                    </div>

                    <!-- Giá mua -->
                    <div class="form-group">
                        <label for="purchasePrice">Giá mua</label>
                        <input type="number" class="form-control" id="purchasePrice" name="purchasePrice" required>
                    </div>

                    <!-- Giá niêm yết -->
                    <div class="form-group">
                        <label for="listPrice">Giá niêm yết</label>
                        <input type="number" class="form-control" id="listPrice" name="listPrice" required>
                    </div>

                    <!-- Khuyến mãi -->
                    <div class="form-group">
                        <label for="promotion">Khuyến mãi liên quan</label>
                        <input type="text" class="form-control" id="promotion" name="promotion" required>
                    </div>

                    <!-- Trạng thái -->
                    <div class="form-group">
                        <label for="state">Trạng thái</label>
                        <select class="form-control" id="state" name="state" required>
                            <option value="Sắp về hàng">Sắp về hàng</option>
                            <option value="Đang bán">Đang bán</option>
                            <option value="Ngưng kinh doanh">Ngưng kinh doanh</option>
                            <option value="Hết hàng">Hết hàng</option>
                        </select>
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
