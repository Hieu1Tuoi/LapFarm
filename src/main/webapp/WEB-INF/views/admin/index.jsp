<section class="content">

      <!-- Default box -->
      <div class="box">
        <div class="box-header with-border">
          <h3 class="box-title">Title</h3>

          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"
                    title="Collapse">
              <i class="fa fa-minus"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip" title="Remove">
              <i class="fa fa-times"></i></button>
          </div>
        </div>
        <div class="box-body">
          Start creating your amazing application!
        </div>
        <!-- /.box-body -->
        <div class="box-footer">
          Footer
        </div>
        <!-- /.box-footer-->
      </div>
      <!-- /.box -->

<<<<<<< Updated upstream
    </section>
=======
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 0.0.1
    </div>
    <strong>Copyright &copy; 2018 <a href="https://youtu.be/dQw4w9WgXcQ?si=bVGCbcuqupdm9rsY">Nhóm 8</a>.</strong>
  </footer>

</div>
<!-- ./wrapper -->

<!-- jQuery 3 -->



  <!-- Left side column. contains the sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="resources/admin/images/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>Alexander Pierce</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <!-- search form -->
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
          <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
        </div>
      </form>
      <!-- /.search form -->
			<!-- sidebar menu: : style can be found in sidebar.less -->

			<ul class="sidebar-menu" data-widget="tree">
				<li><a href="javascript:void(0);" onclick="linkToOrders()"> <i
						class="fa fa-th"></i> <span>Quản lý đơn hàng</span> <span
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
							<li><a href="admin/product?category=${category.idCategory }"> <i
									class="fa fa-circle-o"></i> ${category.nameCategory}
							</a></li>
						</c:forEach>
					</ul>
				</li>
				

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

  <!-- =============================================== -->

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Blank page
        <small>it all starts here</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">Examples</a></li>
        <li class="active">Blank page</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">

      <!-- Default box -->
      <div class="box">
        <div class="box-header with-border">
          <h3 class="box-title">Title</h3>

          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"
                    title="Collapse">
              <i class="fa fa-minus"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip" title="Remove">
              <i class="fa fa-times"></i></button>
          </div>
        </div>
        <div class="box-body">
          Start creating your amazing application!
        </div>
        <!-- /.box-body -->
        <div class="box-footer">
          Footer
        </div>
        <!-- /.box-footer-->
      </div>
      <!-- /.box -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 0.0.1
    </div>
    <strong>Copyright &copy; 2018 <a href="https://adminlte.io">TTPM_BKAP</a>.</strong>
  </footer>

</div>
<!-- ./wrapper -->

<!-- jQuery 3 -->
<script src="resources/admin/js/jquery.min.js"></script>
<script src="resources/admin/js/jquery-ui.js"></script>
<script src="resources/admin/js/bootstrap.min.js"></script>
<script src="resources/admin/js/adminlte.min.js"></script>
<script src="resources/admin/js/dashboard.js"></script>
<script src="resources/admin/tinymce/tinymce.min.js"></script>
<script src="resources/admin/tinymce/config.js"></script>
<script src="resources/admin/js/function.js"></script>
</body>
</html>
>>>>>>> Stashed changes
