<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>


<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Quản lý người dùng
		</h1>
		<ol class="breadcrumb">
			<li><a href="admin/home"><i class="fa fa-dashboard"></i> Home</a></li>
			<li class="active">Nguời dùng</li>
		</ol>
	</section>
	<section class="content">

		<!-- Default box -->
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">

					<div class="box-tools">
						<form 
							action="${pageContext.request.contextPath}/search/admin/user"
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
				<div class="box-body table-responsive no-padding"
					style="margin-top: 30px;">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>ID</th>
								<th>Họ và tên</th>
								<th>Giới tính</th>
								<th>Ngày sinh</th>
								<th>Số điện thoại</th>
								<th>Email</th>
								<th>Số đơn hàng</th>
								<th>Trạng thái</th>
								<th>Tùy chọn</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="u" items="${userInfoDTO}">
								<tr>
									<td>${u.userId}</td>
									<td>${u.fullName}</td>
									<td>${u.sex}</td>
									<td>${u.dob}</td>
									<td>${u.phone}</td>
									<td>${u.email}</td>
									<td>${u.numberOfOrders}</td>
									<td>${u.state}</td>
									<td><a
										href="${pageContext.request.contextPath}/admin/manage-user/history/${u.userId}"
										class="btn btn-success">Xem</a> <!-- Form POST để khóa/mở khóa tài khoản -->
										<form method="post"
											action="${pageContext.request.contextPath}/admin/manage-user/change-state"
											style="display: inline;">
											<input type="hidden" name="userId" value="${u.userId}" /> <input
												type="hidden" name="currentState" value="${u.state}" />
											<button type="submit"
												class="btn ${u.state == 'Hoạt động' ? 'btn-danger' : 'btn-primary'}">
												${u.state == 'Hoạt động' ? 'Khóa TK' : 'Mở Khóa'}</button>
										</form></td>
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