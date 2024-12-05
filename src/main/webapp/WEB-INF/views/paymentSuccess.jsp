<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán thành công</title>
    <base href="${pageContext.servletContext.contextPath}/">
    <style>
        /* Reset CSS */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(to right, #4facfe, #00f2fe);
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }

        .success-container {
            background-color: #fff;
            padding: 30px 40px;
            border-radius: 15px;
            box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.2);
            max-width: 400px;
        }

        .success-container h1 {
            font-size: 24px;
            color: #2ecc71;
            margin-bottom: 20px;
        }

        .success-container p {
            font-size: 18px;
            color: #555;
            margin-bottom: 30px;
        }

        .success-container a {
            display: inline-block;
            text-decoration: none;
            background-color: #3498db;
            color: #fff;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }

        .success-container a:hover {
            background-color: #1d78c1;
        }

        .success-icon {
            font-size: 50px;
            color: #2ecc71;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="success-container">
        <div class="success-icon">✔</div>
        <h1>Đặt hàng thành công!</h1>
        <p>Cảm ơn bạn đã đặt hàng. Thanh toán của bạn bằng tiền mặt đã được xác nhận.</p>
        <a href="order/view">Xem đơn hàng của bạn</a>
    </div>
</body>
</html>
