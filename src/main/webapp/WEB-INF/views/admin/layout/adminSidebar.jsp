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
					<p>ADMIN</p>
					<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
				</div>
			</div>	
			<!-- sidebar menu: : style can be found in sidebar.less -->

			<ul class="sidebar-menu" data-widget="tree">
				
				<li><a href="admin/home"> <i class="fa fa-level-up"></i> <span>Doanh thu</span>
						<span class="pull-right-container"> <small
							class="label pull-right bg-green"></small>
					</span>
				</a></li>
					
				<li class="treeview"><a href="#"> <i
						class="fa fa-dashboard"></i> <span>Quản lý sản phẩm</span> <span
						class="pull-right-container"><i
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
				<li><a href="admin/product/add-product"> <i class="fa fa-plus"></i> <span>Thêm sản phẩm</span>
						<span class="pull-right-container"> <small
							class="label pull-right bg-green">New</small>
					</span>
				</a></li>
					
				<li><a href="admin/categories"> <i class="fa fa-server"></i> <span>Quản lý loại hàng</span>
						<span class="pull-right-container"> <small
							class="label pull-right bg-green"></small>
					</span>
				</a></li>
				
				<li><a href="admin/brands"> <i class="fa fa-building"></i> <span>Quản lý nhãn hàng</span>
						<span class="pull-right-container"> <small
							class="label pull-right bg-green"></small>
					</span>
				</a></li>

				<li><a href="javascript:void(0);" onclick="linkToOrders()">
						<i class="fa fa-th"></i> <span>Quản lý đơn hàng</span> <span
						class="pull-right-container"> <small
							class="label pull-right bg-green"></small>
					</span>
				</a></li>
				<script>
					function linkToOrders() {
						var form = document.createElement('form');
						form.method = 'GET'; // Hoặc POST nếu bạn cần
						form.action = '${pageContext.request.contextPath}/admin/orders'; // Thay action của form

						document.body.appendChild(form);
						form.submit(); // Gửi form
					}

				</script>
				
				<li><a href="admin/payments"> <i class="fa fa-credit-card-alt"></i> <span>Quản lý hóa đơn</span>
						<span class="pull-right-container"> <small
							class="label pull-right bg-green"></small>
					</span>
				</a></li>
				
				<li><a href="admin/manage-user"> <i class="fa fa-user"></i> <span>Quản lý người dùng</span>
						<span class="pull-right-container"> <small
							class="label pull-right bg-green"></small>
					</span>
				</a></li>

			</ul>
		</section>
		<!-- /.sidebar -->
	</aside>