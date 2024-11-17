<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #000; /* Màu nền đen */
            color: #fff; /* Màu chữ trắng */
            margin: 0;
            padding: 0;
        }
        .cart-container {
            width: 80%;
            margin: 20px auto;
            padding: 20px;
            background-color: #222; /* Màu nền của container */
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(255, 0, 0, 0.7);
        }
        .cart-header {
            font-size: 24px;
            margin-bottom: 20px;
            text-align: center;
            color: #ff0000; /* Màu đỏ */
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #555;
        }
        th {
            color: #ff0000; /* Màu chữ đỏ */
        }
        .total {
            font-size: 20px;
            text-align: right;
            margin-top: 20px;
            color: #fff;
        }
        .btn {
            background-color: #ff0000; /* Màu nền nút đỏ */
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
            text-decoration: none;
        }
        .btn:hover {
            background-color: #cc0000; /* Màu nút khi hover */
        }
    </style>
</head>
<body>
    <div class="cart-container">
        <div class="cart-header">Giỏ hàng của bạn</div>
        <c:choose>
            <c:when test="${not empty cartItems}">
                <table>
                    <thead>
                        <tr>
                            <th>Tên sản phẩm</th>
                            <th>Số lượng</th>
                            <th>Giá</th>
                            <th>Tổng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${cartItems}">
                            <tr>
                                <td>${item.name}</td>
                                <td>${item.quantity}</td>
                                <td>${item.price}</td>
                                <td>${item.total}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="total">Tổng tiền: ${totalAmount}</div>
            </c:when>
            <c:otherwise>
                <p>Giỏ hàng của bạn hiện đang trống.</p>
            </c:otherwise>
        </c:choose>
        <a href="store" class="btn">Quay lại cửa hàng</a>
    </div>
</body>
</html>
