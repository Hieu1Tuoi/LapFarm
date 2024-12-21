<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>


<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Quản lý loại hàng
		</h1>
		<ol class="breadcrumb">
			<li><a href="admin/home"><i class="fa fa-dashboard"></i>Trang chủ</a></li>
			<li class="active">Loại hàng</li>
		</ol>
	</section>
	<section class="content">

		<!-- Default box -->
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<a href="admin/categories/add-category" class="btn btn-success">+ Thêm loại hàng</a>
					<div class="box-tools">
						<form 
							action="${pageContext.request.contextPath}/search/admin/category"
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
				<div class="box-body table-responsive no-padding">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>ID</th>
								<th>Loại hàng</th>
								<th>Tùy chọn</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="c" items="${categories}">
								<tr>
									<td>${c.idCategory}</td>
									<td>${c.nameCategory}</td>
									<td><a
										href="${pageContext.request.contextPath}/admin/categories/edit-category/${c.idCategory}"
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