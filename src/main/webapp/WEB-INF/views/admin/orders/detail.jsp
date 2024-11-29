<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Quản lý đơn hàng</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>Trang chủ</a></li>
			<li><a>Đơn hàng</a></li>
			<li class="active">Chi tiết đơn hàng</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">

		<!-- Default box -->
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<div></div>
					<!-- <a href="add-menu.html" class="btn btn-success">+ Tạo đơn hàng</a> -->
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
				<div class="box-body table-responsive no-padding"
					style="margin-top: 30px ">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Hình Ảnh</th>
								<th>Tên Sản Phẩm</th>
								<th>Thương Hiệu</th>
								<th>Giá Bán</th>
								<th>Số Lượng</th>
								<th>Thành tiền</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="d" items="${detail}">
								<tr>
									<td><a href="product-detail/${d.product.idProduct}"><img
											width="100" src="<c:url value='${ d.product.image }'/>"
											alt=""></a></td>
									<td><a href="product-detail/${d.product.idProduct}"
										style="color: black;">${d.product.nameProduct}</a></td>
									<td>${d.product.brandName}</td>
									<td><fmt:formatNumber value="${d.product.salePrice}"
											type="number" groupingUsed="true" /> VNĐ</td>
									<td>${d.quantity}</td>
									<td><fmt:formatNumber
											value="${d.product.salePrice * d.quantity}" type="number"
											groupingUsed="true" /> VNĐ</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<!-- Thêm button "Sửa Trạng Thái" -->
					<a
						href="${pageContext.request.contextPath}/admin/orders/change-status/${order.orderId}"
						class="btn btn-primary" style="margin: 20px 20px;"> Sửa Trạng
						Thái </a>
				</div>


				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.box -->

	</section>
	<!-- /.content -->
</div>
<%@ include file="/WEB-INF/views//admin/layout/adminFooter.jsp"%>