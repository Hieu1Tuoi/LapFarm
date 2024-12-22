<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<base href="${pageContext.servletContext.contextPath}/">
<title>LapFarm</title>
<style>
.product-name {
	display: block; /* Đặt phần tử theo dạng khối */
	white-space: nowrap; /* Ngăn không cho xuống dòng */
	overflow: hidden; /* Ẩn phần nội dung bị tràn */
	text-overflow: ellipsis; /* Hiển thị dấu "..." nếu nội dung bị cắt */
	max-width: 300px;
}

.product-name>a:hover {
	
}
.product-img img {
    max-width: 100%; /* Đảm bảo ảnh không vượt quá container */
    max-height: 100%; /* Giữ chiều cao tối đa theo container */
    object-fit: cover;
    aspect-ratio: 1 / 1; /* Tạo khung vuông cho ảnh */
}
</style>
<!-- Google font -->


<!-- Bootstrap -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css" />">

<!-- Slick -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/slick.css" />">
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/slick-theme.css" />">

<!-- nouislider -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/nouislider.min.css" />">

<!-- Font Awesome Icon -->
<link rel="stylesheet"
	href="<c:url value="/resources/css/font-awesome.min.css" />">

<!-- Custom stlylesheet -->
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />">

<link rel="stylesheet"
	href="<c:url value="/resources/css/filter.css" />">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->

 <link rel="icon" href="<c:url value='/resources/img/icons8-laptop-32.png' />" type="image/png">
</head>

<body>

	<%@ include file="/WEB-INF/views/include/header.jsp"%>


	<!-- NAVIGATION -->
	<%@ include file="/WEB-INF/views/include/navigation.jsp"%>
	<!-- /NAVIGATION -->