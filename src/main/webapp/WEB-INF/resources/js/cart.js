/**
 * 
 */
// Hàm định dạng số tiền có dấu "." theo chuẩn Việt Nam
function formatCurrency(value) {
	return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

// Hàm tính tổng tiền
function calculateTotal() {
	let total = 0;
	const checkboxes = document.querySelectorAll(".u-checkbox");

	checkboxes.forEach(checkbox => {
		if (checkbox.checked) {
			// Lấy ID của checkbox và loại bỏ tiền tố "checkbox"
			const rowId = checkbox.id.replace("checkbox", "");

			// Lấy giá trị tiền từ ô tổng của hàng đó
			const priceElement = document.getElementById(`totalPrice${rowId}`);

			if (priceElement) {
				// Loại bỏ dấu "." và "₫" để chuyển thành số nguyên
				const priceText = priceElement.innerText.trim().replace(/\./g, '').replace('₫', '');
				const price = parseInt(priceText, 10) || 0;
				total += price;
			}
		}
	});

	// Hiển thị tổng tiền trong ô grand total
	const totalCell = document.querySelector("#grandTotal");
	if (totalCell) {
		totalCell.innerText = formatCurrency(total) + " ₫";
	}
}

// Khởi tạo sự kiện sau khi DOM đã tải hoàn toàn
document.addEventListener("DOMContentLoaded", function() {
	// Gắn sự kiện cho tất cả các checkbox
	const checkboxes = document.querySelectorAll(".u-checkbox");
	checkboxes.forEach(checkbox => {
		checkbox.addEventListener("change", calculateTotal);
	});

	// Tính tổng tiền ngay khi trang tải
	calculateTotal();

	// Xử lý nút Thanh toán
	const paymentButton = document.querySelector(".shopBtn.pull-right");
	if (paymentButton) {
		paymentButton.addEventListener("click", function(event) {
			const selectedIds = Array.from(document.querySelectorAll(".u-checkbox:checked"))
				.map(checkbox => checkbox.id.replace("checkbox", ""));

			if (selectedIds.length > 0) {
				fetch("cart/selected", {
					method: "POST",
					headers: { "Content-Type": "application/json" },
					body: JSON.stringify({ idCart: selectedIds })
				})
					.then(response => {
						if (response.ok) {
							window.location.href = "payment";
						} else {
							alert("Đã xảy ra lỗi, vui lòng thử lại.");
						}
					})
					.catch(error => {
						console.error("Lỗi:", error);
						alert("Đã xảy ra lỗi kết nối.");
					});
			} else {
				alert("Vui lòng chọn ít nhất một sản phẩm để tiếp tục thanh toán.");
				event.preventDefault();
			}
		});
	}
});