<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="LapFarm.Service.Config"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>KẾT QUẢ THANH TOÁN</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 700px;
            margin: 50px auto;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        .header {
            text-align: center;
            padding: 10px 0;
            margin-bottom: 20px;
            border-bottom: 1px solid #ddd;
        }
        .header h3 {
            font-size: 28px;
            color: #007bff;
            margin: 0;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            font-weight: bold;
            color: #555;
            margin-bottom: 5px;
        }
        .form-group .value {
            font-weight: normal;
            color: #333;
        }
        .btn-primary {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 5px;
            transition: 0.3s;
            text-decoration: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        footer {
            text-align: center;
            margin-top: 20px;
            padding: 10px 0;
            font-size: 14px;
            color: #777;
            border-top: 1px solid #ddd;
        }
    </style>
</head>
<body>
<%
    Map fields = new HashMap();
    for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
        String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
        String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
        if ((fieldValue != null) && (fieldValue.length() > 0)) {
            fields.put(fieldName, fieldValue);
        }
    }
    String vnp_SecureHash = request.getParameter("vnp_SecureHash");
    if (fields.containsKey("vnp_SecureHashType")) {
        fields.remove("vnp_SecureHashType");
    }
    if (fields.containsKey("vnp_SecureHash")) {
        fields.remove("vnp_SecureHash");
    }
    String signValue = Config.hashAllFields(fields);
    String vnpAmount = request.getParameter("vnp_Amount");
    long amount = (vnpAmount != null && !vnpAmount.isEmpty()) ? Math.round(Long.parseLong(vnpAmount) / 100.0) : 0;
%>
<div class="container">
    <div class="header">
        <h3>KẾT QUẢ THANH TOÁN</h3>
    </div>
    <div>
        <div class="form-group">
            <label>Mã giao dịch thanh toán:</label>
            <span class="value"><%=request.getParameter("vnp_TxnRef")%></span>
        </div>
        <div class="form-group">
            <label>Số tiền:</label>
            <span class="value"><%= amount %> VND</span>
        </div>
        <div class="form-group">
            <label>Mô tả giao dịch:</label>
            <span class="value"><%=request.getParameter("vnp_OrderInfo")%></span>
        </div>
        <div class="form-group">
            <label>Mã lỗi thanh toán:</label>
            <span class="value"><%=request.getParameter("vnp_ResponseCode")%></span>
        </div>
        <div class="form-group">
            <label>Mã giao dịch tại CTT VNPAY-QR:</label>
            <span class="value"><%=request.getParameter("vnp_TransactionNo")%></span>
        </div>
        <div class="form-group">
            <label>Mã ngân hàng thanh toán:</label>
            <span class="value"><%=request.getParameter("vnp_BankCode")%></span>
        </div>
        <div class="form-group">
            <label>Thời gian thanh toán:</label>
            <span class="value"><%=request.getParameter("vnp_PayDate")%></span>
        </div>
        <div class="form-group">
            <label>Tình trạng giao dịch:</label>
            <span class="value">
                <%
                    if (signValue.equals(vnp_SecureHash)) {
                        if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                            out.print("Thành công");
                        } else {
                            out.print("Không thành công");
                        }
                    } else {
                        out.print("Invalid signature");
                    }
                %>
            </span>
        </div>
        <div class="text-center">
            <a href="/" class="btn-primary">Quay lại trang chủ</a>
        </div>
    </div>
    <footer>
        <p>&copy; VNPAY 2024</p>
    </footer>
</div>
</body>
</html>
