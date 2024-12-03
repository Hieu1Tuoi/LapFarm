<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>

<!-- Thêm link tới Font Awesome để sử dụng biểu tượng chuông -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">

<style>
    /* Cấu hình cho thẻ div bao ngoài */
    body > div {
        max-width: 1000px; /* Mở rộng thêm chiều rộng */
        margin: 30px auto;
        padding: 20px;
        font-family: Arial, sans-serif;
    }

    /* Cấu hình cho khung chứa thông báo */
    .notification-container-new {
        background-color: #f4f4f9;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 20px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        max-width: 900px; /* Mở rộng thêm phần thông báo */
        margin: 0 auto;
    }

    /* Tiêu đề thông báo với màu chữ đỏ */
    .notification-container-new h3 {
        font-size: 24px;
        color: #e74c3c; /* Màu đỏ cho tiêu đề */
        margin-bottom: 15px;
        font-weight: bold;
    }

    /* Các đoạn văn bản trong thông báo */
    .notification-container-new p {
        font-size: 16px;
        color: #555;
        line-height: 1.6;
        margin-bottom: 10px;
    }

    .notification-container-new p strong {
        color: #2c3e50; /* Màu tối cho các từ in đậm */
    }

    /* Thêm biểu tượng chuông trước tiêu đề thông báo */
    .notification-container-new h3::before {
        content: "\f0f3"; /* Unicode cho biểu tượng chuông */
        font-family: "Font Awesome 5 Free"; /* Font family của Font Awesome */
        font-weight: 900;
        margin-right: 10px;
        color: #f39c12; /* Màu chuông vàng */
    }

    /* Nút Quay lại (đỏ) */
    .btn-back {
        background-color: #e74c3c; /* Màu đỏ */
        color: white;
        padding: 10px 20px;
        text-decoration: none;
        border-radius: 5px;
        font-size: 16px;
        margin-top: 10px;
        display: inline-block;
        transition: background-color 0.3s ease;
        margin-right: 10px; /* Tạo khoảng cách giữa các nút */
    }

    /* Nút Chi tiết đơn hàng (xanh lam) */
    .btn {
        background-color: #3498db; /* Màu xanh lam */
        color: white;
        padding: 10px 20px;
        text-decoration: none;
        border-radius: 5px;
        font-size: 16px;
        margin-top: 10px;
        display: inline-block;
        transition: background-color 0.3s ease;
    }

    /* Hiệu ứng hover cho các nút */
    .btn:hover, .btn-back:hover {
        opacity: 0.8;
    }

    .btn-back:hover {
        background-color: #c0392b; /* Màu đỏ đậm hơn khi hover */
    }

    .btn:hover {
        background-color: #2980b9; /* Màu xanh lam đậm hơn khi hover */
    }
</style>

<html>
<body>
<div>
    <h2>Thông báo</h2>
    
    <c:if test="${not empty notification}">
        <div class="notification-container-new">
            <h3>Chi tiết thông báo</h3>
            <p><strong>ID:</strong> ${notification.notiId}</p>
            <p><strong>Nội dung:</strong> ${notification.content}</p>
            <p><strong>Ngày tạo:</strong> ${notification.time}</p>
            
            <!-- Nút Quay lại (đỏ) -->
            <a href="home" class="btn btn-back">Quay lại</a>
            <!-- Nút Chi tiết đơn hàng (xanh lam) -->
            <a href="order-detail?orderId=${notification.orderId}" class="btn">Chi tiết đơn hàng</a>
        </div>
    </c:if>
</div>
</body>
</html>

<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>
