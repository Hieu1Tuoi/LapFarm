<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>

<style>
.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td,
	.table>tbody>tr>td, .table>tfoot>tr>td {
	border-top: 1px solid #f4f4f4;
	max-width: 450px;
}
</style>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Quản lý sản phẩm</h1>
		<ol class="breadcrumb">
			<li><a href="admin/home"><i class="fa fa-dashboard"></i>Trang chủ</a></li>
			<li class="active">Sản phẩm</li>
		</ol>
	</section>
	<section class="content">

		<!-- Default box -->
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					
					<div class="box-tools">
						<form 
							action="${pageContext.request.contextPath}/search/admin/product"
							method="post">
							<div class="input-group input-group-sm" style="width: 150px;">
								<input type="text" name="table_search"
									class="form-control pull-right" placeholder="Search">
	
								<div class="input-group-btn">
									<button type="submit" class="btn btn-default">
										<i class="fa fa-search"></i>
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body table-responsive no-padding" style="margin-top: 30px;">
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
									<td><a
										href="${pageContext.request.contextPath}/product-detail/${p.idProduct}"
										style="color: black;">${p.nameProduct}</a></td>
									<td>${p.quantity}</td>
									<td><fmt:formatNumber value="${p.calOriginalPrice()}"
											type="number" groupingUsed="true" /> VNĐ</td>
									<td><fmt:formatNumber value="${p.calSalePrice()}"
											type="number" groupingUsed="true" /> VNĐ</td>
									<td><fmt:formatNumber value="${p.calPrice()}"
											type="number" groupingUsed="true" /> VNĐ</td>
									<td>${p.state}</td>
									<td><a
										href="${pageContext.request.contextPath}/product-detail/${p.idProduct}"
										class="btn btn-success">Xem</a> <a
										href="${pageContext.request.contextPath}/admin/product/edit-product/${p.idProduct}"
										class="btn btn-success">Sửa</a></td>
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