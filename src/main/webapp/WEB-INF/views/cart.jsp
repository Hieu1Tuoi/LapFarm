<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>

<style>
/* Tổng quan */
body {
	font-family: 'Roboto', sans-serif;
	background-color: #fff;
	color: #333;
	margin: 0;
	padding: 0;
}

.well {
	background-color: #ffffff;
	border: 1px solid #ddd;
	border-radius: 5px;
	padding: 20px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
	max-width: 1200px;
	margin: 0 auto;
}

h1 {
	font-size: 28px;
	font-weight: bold;
	color: #e63946; /* Đỏ */
}

.breadcrumb {
	background-color: transparent;
	padding: 8px 15px;
	margin-bottom: 20px;
	list-style: none;
	border-radius: 5px;
}

.breadcrumb>li {
	display: inline-block;
	color: #e63946;
}

.breadcrumb>li>a {
	text-decoration: none;
	color: #e63946;
	font-weight: bold;
}

.breadcrumb>li.active {
	color: #6c757d;
}

/* Bảng giỏ hàng */
.table {
	width: 100%;
	margin: 0 auto 20px;
	border-collapse: collapse;
	border-spacing: 0;
}

.table th, .table td {
	text-align: center;
	vertical-align: middle;
	padding: 10px;
	border: 1px solid #ddd;
	white-space: nowrap; /* Tránh tràn chữ */
}

.table th {
	background-color: #212121; /* Đen */
	color: #fff; /* Trắng */
	font-weight: bold;
}

.table td {
	color: #333;
}

.table img {
	border-radius: 5px;
	max-width: 80px;
	max-height: 80px;
	transition: transform 0.3s ease-in-out;
}

.table img:hover {
	transform: scale(1.1);
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

/* Nút chỉnh sửa và xóa */
.btn-danger {
	background-color: #e63946;
	border-color: #e63946;
	color: #fff;
	border-radius: 3px;
	padding: 5px 10px;
	transition: background-color 0.3s ease;
}

.btn-danger:hover {
	background-color: #b31b27;
}

/* Nút màu sắc */
.shopBtn {
	border-radius: 5px;
	padding: 5px 15px;
	color: #fff;
	background-color: #e63946;
	border: none;
	cursor: pointer;
	text-transform: uppercase;
	font-size: 12px;
	transition: background-color 0.3s ease;
}

.shopBtn:hover {
	background-color: #b31b27;
}

/* Nút số lượng */
input[type="number"] {
	border: 1px solid #ddd;
	border-radius: 4px;
	padding: 5px;
	width: 60px;
	text-align: center;
}

input[type="number"]:focus {
	outline: none;
	border-color: #e63946;
	box-shadow: 0 0 3px rgba(230, 57, 70, 0.5);
}

/* Nút điều hướng */
.shopBtn.pull-right {
	float: right;
	margin-left: 10px;
}

.shopBtn.pull-right:hover {
	background-color: #b31b27;
}

.icon-arrow-left, .icon-arrow-right, .icon-ok, .icon-remove, .icon-edit
	{
	margin-right: 5px;
}

/* Thanh toán và tiếp tục mua */
a.shopBtn {
	display: inline-block;
	padding: 10px 20px;
	text-decoration: none;
	color: #fff;
}

a.shopBtn:hover {
	text-decoration: none;
}

/* Chỉnh sửa tổng thể để tránh tràn */
.row {
	max-width: 1200px;
	margin: 0 auto;
}

.table-container {
	overflow-x: auto; /* Thêm thanh cuộn nếu bảng quá lớn */
}
</style>

<body>
	<!-- 
Body Section 
-->
	<div class="row">
		<div class="span12">
			<ul class="breadcrumb">
				<li><a href="home">Home</a> <span class="divider"></span></li>
				<li class="active">Check Out</li>
			</ul>

			<div class="well well-small">
			
				<h1>Giỏ hàng<small class="pull-right"> ${TotalQuantyCart} sản phẩm </small></h1>
				<hr class="soften" />

				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th>Hình ảnh</th>
							<th>Tên sản phẩm</th>
							<th>Thương hiệu</th>
							<th>Giá bán</th>
							<th>Số lượng</th>
							<th>Chỉnh sửa</th>
							<th>Xóa</th>
							<th>Tổng tiền</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="item" items="${Cart}">
							<tr>
								<td><a href="product-detail/${item.value.product.idProduct}"/><img width="100"
									src="<c:url value="${ item.value.product.image }"/>" alt=""></td>
								<td> <a href="product-detail/${item.value.product.idProduct}"/>${ item.value.product.nameProduct }</td>
								<td><a href="products-brand?nameBrand=${item.value.product.brandName}"/>${ item.value.product.brandName }</td>
								<td><fmt:formatNumber type="number" groupingUsed="true"
										value="${ item.value.product.calPrice() }" /> ₫</td>
								<td><input type="number" min="1" max="100" class="span1"
									style="max-width: 60px" placeholder="1"
									id="quanty-cart-${ item.key }" size="16" type="text"
									value="${ item.value.quantity }"></td>
								<td><a data-id="${item.key}"
									class="btn btn-mini btn-danger edit-cart" type="button"> <i
										class="fa fa-edit"></i>
								</a></td>
								<td><a href="<c:url value='/DeleteCart/${item.key}'/>"
									class="btn btn-mini btn-danger" type="button"> <i
										class="fa fa-trash"></i>
								</a></td>
								<td><fmt:formatNumber type="number" groupingUsed="true"
										value="${ item.value.totalPrice }" /> ₫</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br /> <a href="<c:url value="/"/>" class="shopBtn btn-large"><span
					class="icon-arrow-left"></span> Tiếp tục mua sắm</a> <a
					href="<c:url value="checkout"/>"
					class="shopBtn btn-large pull-right">Thanh toán <span
					class="icon-arrow-right"></span></a>

			</div>
		</div>
	</div>
	<content tag="script"> <script>
		$(".edit-cart").on("click", function() {
			var id = $(this).data("id");
			var quanty = $("#quanty-cart-" + id).val();
			window.location = "EditCart/" + id + "/" + quanty;
		});
	</script> </content>

</body>


<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>