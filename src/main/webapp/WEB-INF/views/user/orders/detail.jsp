<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>
<<style>
/* Container chính */
.order-detail-content {
    margin: 20px auto;
    max-width: 1200px;
    padding: 20px;
    background-color: #ffffff; /* Nền trắng */
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    border: 2px solid #000; /* Viền đen */
}

/* Tiêu đề */
.order-detail-title {
    font-size: 28px;
    font-weight: bold;
    margin-bottom: 20px;
    color: #d32f2f; /* Màu đỏ đậm */
    text-align: center;
}

/* Bảng */
.order-detail-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
}

.order-detail-table th,
.order-detail-table td {
    text-align: left;
    padding: 12px;
    font-size: 16px;
    color: #000; /* Chữ đen */
}

.order-detail-table th {
    background-color: #d32f2f; /* Màu đỏ */
    color: #ffffff; /* Chữ trắng */
    font-weight: bold;
    border: 1px solid #000; /* Viền đen */
}

.order-detail-table tbody tr:nth-child(even) {
    background-color: #f9f9f9; /* Màu nền trắng nhạt */
}

.order-detail-table tbody tr:hover {
    background-color: #ffe8e8; /* Màu nền đỏ nhạt khi hover */
}

.order-detail-table tbody tr td {
    border-bottom: 1px solid #ddd;
}

/* Hình ảnh */
.order-detail-img {
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

/* Tên sản phẩm */
.order-detail-product-name {
    color: black;
    text-decoration: none;
}

/* Nút bấm */
.order-detail-btn {
    background-color: #d32f2f; /* Màu đỏ */
    color: #ffffff; /* Chữ trắng */
    border: none;
    padding: 10px 20px;
    font-size: 16px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.order-detail-btn-link {
    color: #fff;
    text-decoration: none;
}

.order-detail-btn:hover {
    background-color: #b71c1c; /* Màu đỏ đậm hơn khi hover */
}

/* Responsive */
@media (max-width: 768px) {
    .order-detail-content {
        padding: 10px;
    }

    .order-detail-table th,
    .order-detail-table td {
        font-size: 14px;
        padding: 8px;
    }

    .order-detail-btn {
        padding: 8px 16px;
        font-size: 14px;
    }
}

</style>
<body>
    <!-- Main content -->
    <div class="order-detail-content">
        <h1 class="order-detail-title">Mã đơn hàng: ${order.idOrder}</h1>
        <div class="order-detail-box">
            <table class="order-detail-table">
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
                    <c:forEach var="od" items="${orderdetail}">
                        <tr>
                            <td>
                                <a href="product-detail/${od.idProduct}">
                                    <img class="order-detail-img" width="100" 
                                         src="${od.image}" alt="" 
                                         onerror="this.src='/LapFarm/resources/img/soicodoc.jpg'">
                                </a>
                            </td>
                            <td>
                                <a href="product-detail/${od.idProduct}" class="order-detail-product-name">
                                    ${od.nameProduct}
                                </a>
                            </td>
                            <td>${od.brandName}</td>
                            <td>
                                <!-- Định dạng salePrice --> 
                                <fmt:formatNumber value="${od.salePrice}" 
                                                  type="number" groupingUsed="true" />
                                VNĐ
                            </td>
                            <td>${od.quantity}</td>
                            <td>
                                <fmt:formatNumber value="${od.salePrice * od.quantity}" 
                                                  type="number" groupingUsed="true" /> VNĐ
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <button class="order-detail-btn">
            <a href="" class="order-detail-btn-link">Quay lại trang chủ</a>
        </button>
    </div>
</body>
</html>

<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>
