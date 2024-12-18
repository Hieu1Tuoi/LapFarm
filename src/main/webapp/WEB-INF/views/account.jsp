<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="/WEB-INF/views/layouts/user-header.jsp"%>

<style>
/* Đặt CSS cho toàn bộ container */
body {
	font-family: 'Montserrat', sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f8f9fa;
}

h1 {
	font-size: 24px;
	font-weight: bold;
	color: #d10024;
	margin: 20px;
	margin-left: 70px;
}

/* Tab navigation */
.tab-links {
	list-style: none;
	margin: 0;
	padding: 0;
	display: flex;
	border-bottom: 1px solid #ddd;
	background-color: #f8f9fa;
}

.tab-links li {
	margin: 0;
	margin-left: 50px;
}

.tab-links a {
	display: block;
	padding: 15px 20px;
	text-decoration: none;
	color: #333;
	font-weight: 500;
	transition: background-color 0.3s, color 0.3s;
}

.tab-links a:hover, .tab-links .active a {
	background-color: #d10024;
	color: #fff;
}

/* Nội dung tab */
.tab {
	display: none; /* Ẩn toàn bộ tab ban đầu */
	padding: 20px;
	background-color: #fff;
	border: 1px solid #ddd;
	border-radius: 4px;
	margin: 20px;
}

.tab.active {
	display: block; /* Chỉ hiện tab đang chọn */
}

profile
.container {
	max-width: 600px;
	margin: 50px auto;
	padding: 20px;
	background: white;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.form-group {
	display: flex;
	align-items: center;
	margin-bottom: 15px;
}

label {
	width: 10%; /* Chiều rộng cố định của label */
	margin-right: 30px; /* Khoảng cách giữa label và input */
	text-align: right; /* Căn lề phải */
}

input[type="radio"] {
	margin-right: 5px;
}

.link {
	color: #007bff;
	text-decoration: none;
	margin-left: 10px;
	font-size: 14px;
}

.link:hover {
	text-decoration: underline;
}

#viewed .container {
	display: flex; /* Sử dụng Flexbox để tạo bố cục linh hoạt */
	flex-wrap: wrap;
	/* Cho phép các phần tử tự động xuống dòng khi không đủ chỗ */
	gap: 10px; /* Khoảng cách giữa các phần tử */
	justify-content: flex-start; /* Các phần tử sẽ xếp từ trái sang phải */
	padding: 20px;
}

#viewed .container div {
	flex: 0 0 calc(25% - 20px);
	/* Mỗi sản phẩm chiếm 25% chiều rộng của container, trừ đi khoảng cách */
	text-align: center; /* Căn giữa nội dung trong mỗi sản phẩm */
	padding: 10px;
	border-radius: 4px;
	background-color: #fff;
}

#viewed img {
	max-width: 100%; /* Giữ kích thước hình ảnh linh hoạt trong phần tử */
	height: auto; /* Giữ tỷ lệ khung hình cho hình ảnh */
}

#viewed .container div p:nth-child(2) {
	font-size: 18px; /* Tăng kích thước chữ */
	font-weight: bold; /* In đậm chữ */
	margin-top: 10px; /* Tạo khoảng cách phía trên */
	color: #333; /* Màu chữ */
}

#viewed .container div p:nth-child(3) {
	font-size: 16px; /* Tăng kích thước chữ */
	font-weight: bold; /* In đậm chữ */
	color: #d10000; /* Đặt màu chữ nổi bật, có thể thay đổi theo ý thích */
	margin-top: 5px; /* Tạo khoảng cách phía trên */
}
/* CSS cho bảng lịch sử đơn hàng */
.order-history-table {
	width: 100%;
	border-collapse: collapse; /* Gộp các đường viền */
	margin: 20px 0;
	font-size: 16px;
	text-align: left;
	background-color: #fff;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
	overflow: hidden; /* Để các góc bo tròn không bị ảnh hưởng */
}

.order-history-table thead {
	background-color: #d10024;
	color: #fff;
	text-transform: uppercase;
	font-weight: bold;
}

.order-history-table th, .order-history-table td {
	padding: 12px 15px;
	border-bottom: 1px solid #ddd; /* Đường viền dưới của mỗi ô */
}

.order-history-table th {
	text-align: center; /* Căn giữa tiêu đề cột */
}

.order-history-table tbody tr:hover {
	background-color: #f9f9f9; /* Đổi màu nền khi di chuột qua hàng */
}

.order-history-table tbody tr:last-child td {
	border-bottom: none; /* Loại bỏ đường viền dưới cho hàng cuối */
}

.order-history-table td {
	text-align: center; /* Căn giữa nội dung trong các ô */
}

.order-history-table a.btn-detail {
	display: inline-block;
	background-color: #d10024;
	color: #fff;
	padding: 8px 12px;
	border-radius: 4px;
	text-decoration: none;
	font-weight: bold;
	transition: background-color 0.3s;
}

.order-history-table a.btn-detail:hover {
	background-color: #a00000; /* Màu đậm hơn khi hover */
}

/* Nút Xem chi tiết */
.btn-detail {
	display: inline-block;
	padding: 10px 15px;
	background-color: #333; /* Nền đen */
	color: #fff; /* Chữ trắng */
	text-decoration: none;
	font-weight: bold;
	border-radius: 4px;
	transition: background-color 0.3s, color 0.3s;
	display: flex;
	align-items: center; /* Căn giữa biểu tượng và chữ */
}

.btn-detail:hover {
	background-color: #555; /* Khi hover, nền chuyển thành xám */
	color: #fff; /* Chữ vẫn giữ màu trắng */
}

/* Thay đổi màu cho biểu tượng */
.btn-detail i {
	margin-right: 5px; /* Khoảng cách giữa biểu tượng và chữ */
}

/* Thêm hiệu ứng hover cho biểu tượng */
.btn-detail:hover i {
	color: #fff; /* Khi hover, màu biểu tượng cũng chuyển sang trắng */
}
/* Existing styles remain the same */
/* Existing styles remain the same */
.phone-container, .email-container {
    display: flex;
    align-items: center;
    width: 40%;
}

.phone-input, .email-input {
    flex-grow: 1;
    margin-right: 10px;
}

.change-btn {
    background-color: #d10024;
    color: #fff;
    border: none;
    padding: 8px 12px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
    margin-left: 10px;
}

.change-btn:hover {
    background-color: #a00000;
}

.masked-info {
    color: #666;
}
</style>
<script>
function showTab(tabId) {
    // Hide all tabs first
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => {
        tab.style.display = 'none';
    });

    // If no tabId is provided, default to 'profile'
    if (!tabId) {
        tabId = 'profile';
    }

    // Show the selected tab
    const activeTab = document.getElementById(tabId);
    if (activeTab) {
        activeTab.style.display = 'block';
    }

    // Update active link
    const links = document.querySelectorAll('.tab-links a');
    links.forEach(link => {
        link.parentNode.classList.remove('active');
    });

    const activeLink = document.querySelector(`a[href="#${tabId}"]`);
    if (activeLink) {
        activeLink.parentNode.classList.add('active');
    }

    // Update URL hash
    window.location.hash = tabId;
}

// On page load, ensure profile tab is shown
document.addEventListener('DOMContentLoaded', function () {
    // Check if there's a hash, if not, show profile
    const currentHash = window.location.hash.substring(1);
    showTab(currentHash || 'profile');
});
//phone,email
document.addEventListener('DOMContentLoaded', function() {
    // Phone number masking
    const phoneContainer = document.querySelector('.phone-container');
    const phoneInput = document.getElementById('phone');
    const changePhoneBtn = document.createElement('button');
    changePhoneBtn.className = 'change-btn change-phone-btn';
    changePhoneBtn.type = 'button';

    // Email masking
    const emailContainer = document.querySelector('.email-container');
    const emailInput = document.getElementById('email');
    const changeEmailBtn = document.createElement('button');
    changeEmailBtn.className = 'change-btn change-email-btn';
    changeEmailBtn.type = 'button';

    // Function to mask phone number
    function maskPhoneNumber(phone) {
        if (phone && phone.length === 10) {
            return phone.substring(0, 3) + '****' + phone.substring(7);
        }
        return phone;
    }

    // Function to mask email
    function maskEmail(email) {
    if (!email) return '';
    
    // Split email into local part and domain
    const [local, domain] = email.split('@');
    
    // If local part is too short to mask, return as is
    if (local.length <= 3) {
        return email;  // Return the email as is if local part is less than or equal to 3 characters
    }
    
    // Show first 3 and last 3 characters of local part, mask the rest
    const maskedLocal = local.slice(0, 3) + 
                        local.slice(3, -3).replace(/./g, '*') + 
                        local.slice(-3);
    
    return maskedLocal + '@' + domain;
}


    // Initial states
    const originalPhone = phoneInput.value;
    const originalEmail = emailInput.value;
    
    // Create spans to show masked phone and email
    const phoneDisplay = document.createElement('span');
    phoneDisplay.textContent = maskPhoneNumber(originalPhone);
    phoneDisplay.className = 'masked-info';

    const emailDisplay = document.createElement('span');
    emailDisplay.textContent = maskEmail(originalEmail);
    emailDisplay.className = 'masked-info';

    // Phone number setup
    let isPhoneEditable = false;
    changePhoneBtn.textContent = 'Thay đổi';
    phoneInput.style.display = 'none';
    phoneContainer.insertBefore(phoneDisplay, phoneInput);
    phoneContainer.appendChild(changePhoneBtn);

    // Email setup
    let isEmailVisible = false;
    changeEmailBtn.textContent = 'Hiện';
    emailInput.style.display = 'none';
    emailContainer.insertBefore(emailDisplay, emailInput);
    emailContainer.appendChild(changeEmailBtn);

    // Change email button click handler
    changeEmailBtn.addEventListener('click', function() {
        if (!isEmailVisible) {
            // Show full email
            emailDisplay.textContent = originalEmail;
            changeEmailBtn.textContent = 'Ẩn';
            isEmailVisible = true;
        } else {
            // Mask email again
            emailDisplay.textContent = maskEmail(originalEmail);
            changeEmailBtn.textContent = 'Hiện';
            isEmailVisible = false;
        }
    });

    // Change phone button click handler
    changePhoneBtn.addEventListener('click', function() {
        if (!isPhoneEditable) {
            // Switch to editable mode
            phoneDisplay.style.display = 'none';
            phoneInput.style.display = 'block';
            phoneInput.value = originalPhone;
            phoneInput.focus();
            changePhoneBtn.textContent = 'Ẩn';
            isPhoneEditable = true;
        } else {
            // Switch back to masked mode
            phoneDisplay.style.display = 'block';
            phoneInput.style.display = 'none';
            changePhoneBtn.textContent = 'Thay đổi';
            isPhoneEditable = false;
        }
    });

    // Validation when manually submitting the form
    const form = document.querySelector('form[action$="/profile/update"]');
    form.addEventListener('submit', function(event) {
        const phoneInput = document.querySelector('input[name="phone"]');
        const phone = phoneInput.value.trim();
        const errorElement = document.getElementById('error-phone');
        
        // Clear previous error message
        errorElement.innerText = '';
        
        // Validation checks
        if (!phone) {
            errorElement.innerText = "Số điện thoại không được để trống.";
            event.preventDefault();
        } else if (!/^0\d{9}$/.test(phone)) {
            errorElement.innerText = "Số điện thoại phải đúng 10 chữ số.";
            event.preventDefault();
        }
    });
});
</script>
<body>
	<h1>Thông tin tài khoản</h1>
	<ul class="tab-links">
		<li><a href="account#profile" onclick="showTab('profile');">Thông
				tin cá nhân</a></li>
		<li><a href="account#orders-history"
			onclick="showTab('orders-history');">Đơn Hàng</a></li>

		<li><a href="account#viewed" onclick="showTab('viewed');">Sản
				phẩm đã xem</a></li>
	</ul>

	<div id="profile" class="tab">
		<div class="container">
			<h1>Thông tin tài khoản</h1>
			<form
				action="${pageContext.servletContext.contextPath}/profile/update"
				method="post">
				<div class="form-group">
					<label for="fullname">Họ Tên</label> <input type="text"
						id="fullname" name="fullname"
						style="width: 40%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
						value="${userInfo.fullName}">
				</div>
				<div class="form-group">
					<label>Giới tính</label>
					<div>
						<input type="radio" id="male" name="sex" value="Nam"
							${userInfo.sex == 'Nam' ? 'checked' : ''}> <label
							for="male">Nam</label> <input type="radio" id="female" name="sex"
							value="Nữ" ${userInfo.sex == 'Nữ' ? 'checked' : ''}> <label
							for="fermale">Nữ</label>
					</div>
				</div>
				<div class="form-group">
                    <label for="phone">Số điện thoại</label>
                    <div class="phone-container">
                        <input type="text" id="phone" name="phone"
                            style="width: 100%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
                            value="${userInfo.phone}">
                        <span class="error-message" id="error-phone" 
                            style="color: red; margin-left: 10px; display: block;"></span>
                    </div>
                </div>
				<div class="form-group">
					<label for="address">Địa chỉ</label> <input type="text"
						id="address" name="address"
						style="width: 40%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
						value="${userInfo.address}">
				</div>
				 <div class="form-group">
                    <label for="email">Email</label>
                    <div class="email-container">
                        <input type="text" id="email" name="email"
                            style="width: 100%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
                            value="${userProfile.email}" readonly>
                    </div>
                </div>
				<div class="form-group">
					<label for="dob">Ngày sinh</label> <input type="date" id="dob"
						name="dob"
						style="width: 40%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
						value="${userInfo.dob}" />
				</div>
				<button type="submit" class="save-button profile-save-button"
					style="background-color: #a00000; background-color: #d10024; color: #fff; border: none; padding: 10px 15px; border-radius: 4px; cursor: pointer; font-weight: bold; transition: background-color 0.3s; margin-left: 12.9%;">LƯU
					THAY ĐỔI</button>
			</form>
		</div>
	</div>
	<div id="orders-history" class="tab">
		<h1>Lịch sử đơn hàng</h1>
		<c:if test="${not empty orders}">
			<table class="order-history-table">
				<thead>
					<tr>
						<th>Mã đơn hàng</th>
						<th>Ngày đặt</th>
						<th>Tổng tiền</th>
						<th>Trạng thái</th>
						<th>Hình thức thanh toán</th>
						<th>Chi tiết</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="order" items="${orders}">
						<tr>
							<td>${order.orderId}</td>
							<td>${order.time}</td>
							<td><strong> <fmt:formatNumber
										value="${order.totalPrice}" type="currency" currencySymbol="₫" />
							</strong></td>

							<td>${order.state}</td>
							<td><c:choose>
									<c:when test="${order.paymentMethod == 0}">Tiền mặt</c:when>
									<c:when test="${order.paymentMethod == 1}">VNPAY</c:when>
									<c:otherwise>Khác</c:otherwise>
								</c:choose></td>
							<td><a href="order-detail?orderId=${order.encryptedId}"
								class="btn-detail"> <i class="fa fa-eye"></i> Xem chi tiết
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${empty orders}">
			<p>Bạn chưa có đơn hàng nào.</p>
		</c:if>
	</div>
	<div id="viewed" class="tab">
		<h1>Sản phẩm đã xem</h1>
		<div class="container">
			<c:if test="${not empty viewedItems}">
				<c:forEach var="item" items="${viewedItems}">
					<div>
<<<<<<< Updated upstream
						<a
							href="${pageContext.servletContext.contextPath}/product-detail/${item.id}">
							<img src="${item.image}" alt="${item.name}" />
							<p>${item.name}</p>
							<p>
								<fmt:formatNumber value="${item.price}" type="number"
									groupingUsed="true"></fmt:formatNumber>
								đ
							</p>
=======
						<a href="${pageContext.servletContext.contextPath}/product-detail/${item.encryptedId}">
						<img src="${item.image}" alt="${item.name}" />
						<p>${item.name} </p>
						<p> <fmt:formatNumber value="${item.price}" type="number"
									groupingUsed="true"></fmt:formatNumber>đ</p>
>>>>>>> Stashed changes
					</div>
				</c:forEach>

				</tbody>
			</c:if>
			<c:if test="${empty viewedItems}">
				<p>Bạn chưa xem sản phẩm nào.</p>
			</c:if>

		</div>
</body>
</html>
<%@ include file="/WEB-INF/views/layouts/user-footer.jsp"%>