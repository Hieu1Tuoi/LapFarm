<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>

<div class="content-wrapper">
    <!-- Content Header -->
    <section class="content-header">
        <h1>Sửa hãng</h1>
        <ol class="breadcrumb">
            <li><a href="admin/home"><i class="fa fa-dashboard"></i> Trang chủ</a></li>
            <li><a href="admin/brands">Hãng</a></li>
            <li class="active"> Sửa hãng</li>
        </ol>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <h3 class="box-title">Thông tin hãng</h3>
            </div>
            <form
                action="${pageContext.request.contextPath}/admin/brands/edit-brand/${brand.idBrand}"
                method="post">
                <div class="box-body">
                    <c:if test="${not empty message}">
                        <h2 style="color: #800923; text-align: center;">${message}</h2>
                    </c:if>

                    <!-- Loại hàng -->
                    <div class="form-group">
                        <label for="brandName">Sửa hãng</label>
                        <input type="text" class="form-control" id="brandName" name="brandName"
                               value="${brand.nameBrand}" placeholder="Nhập tên hãng mới" required />
                    </div>

                    <!-- Submit Button -->
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">Cập nhật hãng</button>
                    </div>
                </div>
            </form>
        </div>
    </section>
</div>

<%@ include file="/WEB-INF/views/admin/layout/adminFooter.jsp"%>
