<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>

<div class="content-wrapper">
    <!-- Content Header -->
    <section class="content-header">
        <h1>Sửa loại hàng</h1>
        <ol class="breadcrumb">
            <li><a href="admin/home"><i class="fa fa-dashboard"></i> Trang chủ</a></li>
            <li><a href="admin/categories">Loại hàng</a></li>
            <li class="active"> Sửa loại hàng</li>
        </ol>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">
                <h3 class="box-title">Thông tin loại hàng</h3>
            </div>
            <form
                action="${pageContext.request.contextPath}/admin/categories/edit-category/${category.idCategory}"
                method="post">
                <div class="box-body">
                    <c:if test="${not empty message}">
                        <h2 style="color: #800923; text-align: center;">${message}</h2>
                    </c:if>

                    <!-- Loại hàng -->
                    <div class="form-group">
                        <label for="categoryName">Sửa loại hàng</label>
                        <input type="text" class="form-control" id="categoryName" name="categoryName"
                               value="${category.nameCategory}" placeholder="Nhập tên loại hàng mới" required />
                    </div>

                    <!-- Submit Button -->
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">Cập nhật loại hàng</button>
                    </div>
                </div>
            </form>
        </div>
    </section>
</div>

<%@ include file="/WEB-INF/views/admin/layout/adminFooter.jsp"%>
