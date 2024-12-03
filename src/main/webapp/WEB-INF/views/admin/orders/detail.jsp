<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <div class="col-xs-12">
            <div class="box">
                <div class="box-header">
                    <div></div>
                    <div class="box-tools">
                        <div class="input-group input-group-sm" style="width: 150px;">
                            <input type="text" name="table_search" class="form-control pull-right" placeholder="Search">
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
					style="margin-top: 30px">
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
							<c:set var="totalAmount" value="0" />
							<c:forEach var="d" items="${detail}">
								<tr>
									<td><a href="product-detail/${d.product.idProduct}"><img
											width="100" src="${d.product.image}" alt=""></a></td>
									<td><a href="product-detail/${d.product.idProduct}"
										style="color: black;"> ${d.product.nameProduct}</a></td>
									<td>${d.product.brandName}</td>
									<td><fmt:formatNumber value="${d.price}" type="number"
											groupingUsed="true" /> VNĐ</td>
									<td>${d.quantity}</td>
									<td><fmt:formatNumber value="${d.price * d.quantity}"
											type="number" groupingUsed="true" /> VNĐ</td>
								</tr>
								<c:set var="totalAmount"
									value="${totalAmount + (d.price * d.quantity)}" />
							</c:forEach>
						</tbody>
					</table>
					<div style="margin: 20px 50px; text-align: right;">
						<div class="btn" style="margin-bottom: 20px;">
							Tổng tiền:
							<fmt:formatNumber value="${totalAmount}" type="number"
								groupingUsed="true" />
							VNĐ
						</div>

						<!-- Form to submit the order status -->
						<form
							action="${pageContext.request.contextPath}/admin/orders/update-status/${order.idOrder}"
							method="post">
							<input type="hidden" name="orderId" value="${order.idOrder}" /> <select
								class="btn btn-success" name="state"
								${order.state == 'Hoàn thành' ? 'disabled' : ''}>
								<c:forEach var="state" items="${statuses}">
									<option value="${state}"
										${order.state == state ? 'selected style="color: black;"' : ''}>
										Trạng thái: ${state}</option>
								</c:forEach>
							</select>
							<!-- Submit button (optional, if you don't want auto-submit on change) -->
							<button type="submit" class="btn btn-success"
								${order.state == 'Hoàn thành' ? 'disabled' : ''}>Lưu</button>
						</form>


					</div>
				</div>
			</div>
        </div>
    </section>
</div>

<%@ include file="/WEB-INF/views/admin/layout/adminFooter.jsp"%>
