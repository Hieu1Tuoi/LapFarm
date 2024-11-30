<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Báo Lỗi - Yêu cầu cần xác thực người dùng</title>
<base href="${pageContext.servletContext.contextPath}/">
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f8f9fa;
	color: #343a40;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
	padding: 20px;
	box-sizing: border-box;
}

.error-container {
	text-align: center;
	background-color: #ffffff;
	border: 1px solid #dee2e6;
	border-radius: 8px;
	padding: 30px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	max-width: 400px;
	width: 100%;
}

.error-container h1 {
	font-size: 48px;
	margin: 0;
	color: #dc3545;
}

.error-container p {
	font-size: 18px;
	margin: 10px 0;
}

.back-button {
	display: inline-block;
	margin-top: 20px;
	padding: 10px 20px;
	background-color: #007bff;
	color: white;
	text-decoration: none;
	border-radius: 4px;
	transition: background-color 0.3s;
}

.back-button:hover {
	background-color: #0056b3;
}
</style>
</head>
<body>
	<div class="error-container">
		<h1>401</h1>
		<p>Yêu cầu cần xác thực người dùng.</p>
		<a href="home" class="back-button">Quay lại trang chủ</a>
	</div>
</body>
</html>