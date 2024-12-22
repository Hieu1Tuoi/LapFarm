<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Quản lý hóa đơn</h1>
		<ol class="breadcrumb">
			<li class="active">Trang chủ</li>
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
						<form 
							action="${pageContext.request.contextPath}/search/admin/payment"
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
				<div class="box-body table-responsive no-padding" style="margin-top: 30px">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>ID Hóa Đơn</th>
								<th>ID Đơn Hàng</th>
								<th>Người đặt hàng</th>
								<th>Tổng tiền</th>
								<th>Hình thức</th>
								<th>Thời gian</th>
								<th>Trạng thái</th>
								<th>Tùy chọn</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${payments}">
								<tr>
									<td>${p.idPayment}</td>
									<td>${p.orderPayment.idOrder}</td>
									<td>${p.userPayment.fullName}</td>
									<td><fmt:formatNumber value="${p.pricePayment}"
											type="number" groupingUsed="true" /> VNĐ</td>
									<td><c:choose>
											<c:when test="${p.methodPayment == 0}">Tiền mặt</c:when>
											<c:when test="${p.methodPayment == 1}">Chuyển khoản</c:when>
											<c:otherwise>Không xác định</c:otherwise>
										</c:choose></td>
									<!-- Cột tùy chọn: dropdown để thay đổi trạng thái -->
									<td>${p.timePayment}</td>
									<td>${p.statePayment}</td>
									<td>
									<a
										href="${pageContext.request.contextPath}/admin/orders/detail-order/${p.orderPayment.idOrder}"
										class="btn btn-success">Xem đơn hàng</a>
									</td>
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