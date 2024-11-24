<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Left side column. contains the sidebar -->
	<aside class="main-sidebar">
		<!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar">
			<!-- Sidebar user panel -->
			<div class="user-panel">
				<div class="pull-left image">
					<img src="resources/admin/images/user2-160x160.jpg"
						class="img-circle" alt="User Image">
				</div>
				<div class="pull-left info">
					<p>Alexander Pierce</p>
					<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
				</div>
			</div>
			<!-- search form -->
			<form action="#" method="get" class="sidebar-form">
				<div class="input-group">
					<input type="text" name="q" class="form-control"
						placeholder="Search..."> <span class="input-group-btn">
						<button type="submit" name="search" id="search-btn"
							class="btn btn-flat">
							<i class="fa fa-search"></i>
						</button>
					</span>
				</div>
			</form>
			<!-- /.search form -->
			<!-- sidebar menu: : style can be found in sidebar.less -->

			<ul class="sidebar-menu" data-widget="tree">
				<li><a href="javascript:void(0);" onclick="linkToOrders()">
						<i class="fa fa-th"></i> <span>Quản lý đơn hàng</span> <span
						class="pull-right-container"> <small
							class="label pull-right bg-green">FE</small>
					</span>
				</a></li>
				<li class="treeview"><a href="#"> <i
						class="fa fa-dashboard"></i> <span>Quản lý sản phẩm</span> <span
						class="pull-right-container"> <i
							class="fa fa-angle-left pull-right"></i>
					</span>
				</a>
					<ul class="treeview-menu">
						<!-- Sử dụng JSTL để lặp qua danh sách categories -->
						<c:forEach var="category" items="${categories}">
							<li><a href="admin/product?category=${category.idCategory }">
									<i class="fa fa-circle-o"></i> ${category.nameCategory}
							</a></li>
						</c:forEach>
					</ul></li>


				<script>
					function linkToOrders() {
						var form = document.createElement('form');
						form.method = 'GET'; // Hoặc POST nếu bạn cần
						form.action = '${pageContext.request.contextPath}/admin/orders'; // Thay action của form

						document.body.appendChild(form);
						form.submit(); // Gửi form
					}

				</script>


				<li><a href=""> <i class="fa fa-th"></i> <span>Widgets</span>
						<span class="pull-right-container"> <small
							class="label pull-right bg-green">Hot</small>
					</span>
				</a></li>

			</ul>
		</section>
		<!-- /.sidebar -->
	</aside>