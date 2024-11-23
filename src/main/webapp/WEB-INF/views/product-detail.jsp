<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.nameProduct}</title>
</head>
<body>
    <h1>Product Details</h1>

    <!-- Hiển thị tên sản phẩm -->
    <h2>${product.nameProduct}</h2>

    <!-- Hiển thị thương hiệu -->
    <p><strong>Brand:</strong> ${product.brand.nameBrand}</p>

    <!-- Hiển thị danh mục -->
    <p><strong>Category:</strong> ${product.category.nameCategory}</p>

    <!-- Mô tả sản phẩm -->
    <p><strong>Description:</strong> ${product.description}</p>

    <!-- Giá sản phẩm -->
    <p><strong>Price:</strong> ${product.salePrice}</p>

    <!-- Số lượng sản phẩm -->
    <p><strong>Quantity:</strong> ${product.quantity}</p>

    <!-- Hiển thị ảnh sản phẩm (nếu có) -->
    <h3>Images</h3>
    <%-- <c:forEach var="image" items="${product.images}">
        <img src="${image.imageUrl}" alt="${product.nameProduct}" style="width:200px;">
    </c:forEach> --%>

    <!-- Link quay lại trang danh sách sản phẩm -->
    <a href="/product">Back to Products</a>
</body>
</html>
