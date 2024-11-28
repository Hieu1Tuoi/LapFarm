<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>


<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Quản lý sản phẩm
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>Trang chủ</a></li>
			<li class="active">Sản phẩm</li>
		</ol>
	</section>
	<section class="content">

		<!-- Default box -->
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<a href="admin/product/add-category" class="btn btn-success">+ Thêm loại hàng</a>
					<a href="admin/product/add-brand" class="btn btn-success">+ Thêm hãng</a>
					<a href="admin/product/add-product" class="btn btn-success">+ Thêm sản phẩm</a>
					<div class="box-tools">
						<div class="input-group input-group-sm" style="width: 150px;">
							<input type="text" name="table_search"
								class="form-control pull-right" placeholder="Search">

							<div class="input-group-btn">
								<button type="submit" class="btn btn-default">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body table-responsive no-padding">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>ID</th>
								<th>Hãng</th>
								<th>Tên sản phẩm</th>
								<th>Số lượng</th>
								<th>Giá mua</th>
								<th>Giá niêm yết</th>
								<th>Giá bán</th>
								<th>Trạng thái</th>
								<th>Tùy chọn</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${products}">
								<tr>
									<td>${p.idProduct}</td>
									<td>${p.brandName}</td>
									<td>${p.nameProduct}</td>
									<td>${p.quantity}</td>
									<td><fmt:formatNumber value="${p.calOriginalPrice()}"
											type="number" groupingUsed="true" /> VNĐ</td>
									<td><fmt:formatNumber value="${p.calSalePrice()}"
											type="number" groupingUsed="true" /> VNĐ</td>
									<td><fmt:formatNumber value="${p.calPrice()}"
											type="number" groupingUsed="true" /> VNĐ</td>
									<td>${p.state}</td>
									<td><a
										href="${pageContext.request.contextPath}/admin/view-order/${p.idProduct}"
										class="btn btn-success">Xem</a> <a
										href="${pageContext.request.contextPath}/admin/edit-order/${p.idProduct}"
										class="btn btn-success">Sửa</a> <a
										href="${pageContext.request.contextPath}/admin/delete-order/${p.idProduct}"
										class="btn btn-danger">Xóa</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.box -->

	</section>
</div>

<%@ include file="/WEB-INF/views//admin/layout/adminFooter.jsp"%>