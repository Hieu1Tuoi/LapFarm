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
			<li class="active">Đơn hàng</li>
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
				<div class="box-body table-responsive no-padding">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>ID Đơn Hàng</th>
								<th>Người Đặt Hàng</th>
								<th>Thời Gian</th>
								<th>Trạng Thái</th>
								<th>Tổng Giá</th>
								<th>Hình thức</th>
								<th>Tùy Chọn</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<tr>
									<td>${order.orderId}</td>
									<td>${order.userFullname}</td>
									<td>${order.time}</td>
									<td>${order.state}</td>
									<td><fmt:formatNumber value="${order.totalPrice}"
											type="number" groupingUsed="true" /> VNĐ</td>
									<td><c:choose>
											<c:when test="${order.paymentMethod == 0}">
            									Tiền mặt
        									</c:when>
											<c:when test="${order.paymentMethod == 1}">
            									Chuyển khoản
        									</c:when>
											<c:otherwise>
            									Không xác định
        									</c:otherwise>
										</c:choose></td>
									<td><a
										href="${pageContext.request.contextPath}/admin/view-order/${order.orderId}"
										class="btn btn-success">Xem</a> <a
										href="${pageContext.request.contextPath}/admin/edit-order/${order.orderId}"
										class="btn btn-success">Sửa</a> <a
										href="${pageContext.request.contextPath}/admin/delete-order/${order.orderId}"
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
	<!-- /.content -->
</div>
<%@ include file="/WEB-INF/views//admin/layout/adminFooter.jsp"%>