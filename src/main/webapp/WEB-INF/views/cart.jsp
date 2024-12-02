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

/* Giới hạn chiều dài tên sản phẩm */
.table td a {
	display: block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	max-width: 500px; /* Giới hạn chiều rộng của tên sản phẩm */
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

				<h1>
					Giỏ hàng<small class="pull-right"> ${TotalQuantyCart} sản
						phẩm </small>
				</h1>
				<hr class="soften" />

				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th></th>
							<th>Hình ảnh</th>
							<th>Tên sản phẩm</th>
							<th>Thương hiệu</th>
							<th>Đơn giá</th>
							<th>Số lượng</th>
							<th>Chỉnh sửa</th>
							<th>Xóa</th>
							<th>Tổng</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="item" items="${Cart}">
							<tr id="${item.value.id}">
								<td><input id="checkbox${item.value.id}" type="checkbox"
									class="u-checkbox"
									<c:if test="${cartIdSelecteds != null && cartIdSelecteds.contains(item.value.id)}">checked</c:if>>
								</td>
								<td><a
									href="product-detail/${item.value.product.idProduct}" /><img
									width="100" src="${ item.value.product.image }" alt=""></td>
								<td><a
									href="product-detail/${item.value.product.idProduct}" />${ item.value.product.nameProduct }</td>
								<td><a
									href="products-brand?nameBrand=${item.value.product.brandName}" />${ item.value.product.brandName }</td>

								<td id="price-cart-${item.value.id}"><fmt:formatNumber
										type="number" groupingUsed="true"
										value="${ item.value.product.calPrice() }" /> ₫</td>
								<td><input type="number" min="1"
									max="${item.value.product.quantity}" class="span1"
									style="max-width: 60px" id="quanty-cart-${item.key}"
									value="${item.value.quantity}"
									data-original-quantity="${item.value.quantity}"
									onchange="updateQuantity(${item.key}, this.value)"></td>

								<td><a data-id="${item.key}"
									class="btn btn-mini btn-danger edit-cart" type="button"> <i
										class="fa fa-edit"></i>
								</a></td>
								<td><a href="<c:url value='/DeleteCart/${item.key}'/>"
									class="btn btn-mini btn-danger"
									onclick="return confirmDelete();"> <i class="fa fa-trash"></i>
								</a></td>


								<td id="totalPrice${item.value.id}"><fmt:formatNumber
										type="number" groupingUsed="true"
										value="${ item.value.totalPrice }" /> ₫</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="8" style="text-align: right; font-weight: bold;">Tổng
								tiền:</td>
							<td id="grandTotal">0 ₫</td>
						</tr>
					</tbody>
				</table>
				<br /> <a href="<c:url value="/"/>" class="shopBtn btn-large"><span
					class="icon-arrow-left"></span> Tiếp tục mua sắm</a> <a
					class="shopBtn btn-large pull-right">Thanh toán <span
					class="icon-arrow-right"></span></a>

			</div>
		</div>
	</div>
	<content tag="script"> <script>
	$(document).ready(function () {
	    let editedItems = {}; // Theo dõi các sản phẩm đã chỉnh sửa
	    let canCheckout = false; // Đánh dấu trạng thái cho phép thanh toán

	    // Hàm cập nhật tổng tiền của từng sản phẩm
	    function updateTotalPrice(itemId, newQuantity) {
	        const unitPrice = parseFloat($("#price-cart-" + itemId).text().replace(/[^\d]/g, ""));
	        const totalPrice = unitPrice * newQuantity;
	        $("#totalPrice" + itemId).text(new Intl.NumberFormat('vi-VN').format(totalPrice) + " ₫");
	    }

	    // Xử lý sự kiện thay đổi số lượng
	    $(".span1").on("change", function () {
	        const row = $(this).closest("tr");
	        const id = row.attr("id");
	        const newQuantity = $(this).val();

	        if (newQuantity <= 0) {
	            alert("Số lượng phải lớn hơn 0.");
	            return;
	        }

	        editedItems[id] = false; // Đánh dấu chưa chỉnh sửa
	        updateTotalPrice(id, newQuantity); // Cập nhật tổng tiền
	        $("#checkbox" + id).prop("checked", false).prop("disabled", true); // Vô hiệu hóa checkbox
	        $(".shopBtn.pull-right").prop("disabled", true); // Vô hiệu hóa nút thanh toán
	    });

	    // Xử lý sự kiện nhấn nút "Chỉnh sửa"
	    $(".edit-cart").on("click", function () {
	        const id = $(this).data("id");
	        const newQuantity = $("#quanty-cart-" + id).val();

	        if (newQuantity <= 0) {
	            alert("Số lượng phải lớn hơn 0.");
	            return;
	        }

	        // Gọi API hoặc xử lý logic để lưu số lượng mới
	        window.location = "EditCart/" + id + "/" + newQuantity;

	        // Sau khi chỉnh sửa thành công
	        editedItems[id] = true; // Đánh dấu đã chỉnh sửa
	        $("#checkbox" + id).prop("disabled", false).prop("checked", true); // Kích hoạt lại checkbox và đánh dấu đã chọn
	        checkAllEdited(); // Kiểm tra trạng thái thanh toán
	    });

	    // Hàm kiểm tra trạng thái cho phép thanh toán
	    function checkAllEdited() {
	        // Kiểm tra tất cả các checkbox và trạng thái chỉnh sửa
	        canCheckout = !Object.values(editedItems).includes(false) && $(".u-checkbox:checked").length > 0;
	        $(".shopBtn.pull-right").prop("disabled", !canCheckout);
	    }

	    // Xử lý sự kiện nhấn nút "Thanh toán"
	    $(".shopBtn.pull-right").on("click", function (event) {
	        event.preventDefault();

	        // Kiểm tra tất cả các sản phẩm xem có tất cả checkbox đã được chọn và đã chỉnh sửa hay chưa
	        let anyChecked = false;   // Kiểm tra nếu có bất kỳ sản phẩm nào được chọn
	        let allEdited = true;     // Kiểm tra xem tất cả sản phẩm đã được chỉnh sửa hay chưa

	        $(".u-checkbox").each(function () {
	            const id = $(this).closest("tr").attr("id");

	            // Kiểm tra nếu sản phẩm chưa được chỉnh sửa
	            if (!editedItems[id]) {
	                allEdited = false;
	            }

	            // Kiểm tra nếu có ít nhất một sản phẩm được chọn
	            if ($(this).prop("checked")) {
	                anyChecked = true;
	            }
	        });

	        // Nếu có ít nhất một sản phẩm được chọn và tất cả sản phẩm đều đã được chỉnh sửa, hiển thị thông báo thanh toán
	        if (anyChecked || allEdited) {
	            alert("Bạn đã sẵn sàng thanh toán!");
	            // Logic xử lý thanh toán
	        } else {
	            alert("Bạn cần chỉnh sửa tất cả các sản phẩm trước khi thanh toán.");
	        }
	    });

	    // Hàm xác nhận xóa sản phẩm
	    window.confirmDelete = function () {
	        return confirm("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng không?");
	    };
	});


</script> </content>
	<script src="<c:url value='/resources/js/cart.js' />"></script>
</body>


<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>