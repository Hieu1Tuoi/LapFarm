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
			const rowId = checkbox.id.replace("checkbox", "");
			const priceElement = document.getElementById(`totalPrice${rowId}`);

			if (priceElement) {
				const priceText = priceElement.innerText.trim().replace(/\./g, '').replace('₫', '');
				const price = parseInt(priceText, 10) || 0;
				total += price;
			}
		}
	});

	const totalCell = document.querySelector("#grandTotal");
	if (totalCell) {
		totalCell.innerText = formatCurrency(total) + " ₫";
	}
}

// Hàm cập nhật giá của một hàng
function updateRowTotal(rowId) {
    const quantityInput = document.getElementById(`quanty-cart-${rowId}`);
    const priceElement = document.getElementById(`price-cart-${rowId}`);
    const totalElement = document.getElementById(`totalPrice${rowId}`);

    if (quantityInput && priceElement && totalElement) {
        let quantity = parseInt(quantityInput.value, 10) || 1; // Giá trị nhập vào
        const min = parseInt(quantityInput.min, 10); // Giá trị nhỏ nhất
        const max = parseInt(quantityInput.max, 10); // Giá trị lớn nhất

        // Nếu số lượng không hợp lệ, đặt lại giá trị hợp lệ
        if (isNaN(quantity) || quantity < min) {
            quantity = min;
            alert(`Số lượng không hợp lệ! Tối thiểu là ${min}.`);
        } else if (quantity > max) {
            quantity = max;
            alert(`Số lượng không hợp lệ! Tối đa là ${max}.`);
        }

        // Cập nhật lại giá trị hợp lệ trong ô nhập liệu
        quantityInput.value = quantity;

        // Cập nhật tổng giá của hàng
        const priceText = priceElement.innerText.trim().replace(/\./g, '').replace('₫', '');
        const price = parseInt(priceText, 10) || 0;
        const total = price * quantity;

        totalElement.innerText = formatCurrency(total) + " ₫";

        // Fetch API để cập nhật số lượng
        fetch(`EditCart/${rowId}/${quantity}`, { method: "GET" })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Lỗi cập nhật giỏ hàng.");
                }
                console.log(`Cập nhật thành công: ${rowId} ${quantity}`);
            })
            .catch(error => {
                console.error("Lỗi khi cập nhật số lượng:", error);
                alert("Không thể cập nhật số lượng sản phẩm. Vui lòng thử lại.");
            });
    }

    calculateTotal(); // Cập nhật tổng tiền
}

// Gắn sự kiện thay đổi cho ô nhập số lượng
function attachQuantityChangeEvents() {
	const quantityInputs = document.querySelectorAll("input[id^='quanty-cart-']");

	quantityInputs.forEach(input => {
		input.addEventListener("input", function() {
			const rowId = this.id.replace("quanty-cart-", "");
			updateRowTotal(rowId);
		});

		input.addEventListener("change", function() {
			const rowId = this.id.replace("quanty-cart-", "");
			updateRowTotal(rowId);
		});
	});
}

// Xử lý nút Thanh toán
function attachPaymentButtonEvent() {
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
}

// Khởi tạo sự kiện sau khi DOM tải xong
document.addEventListener("DOMContentLoaded", function() {
	attachQuantityChangeEvents();

	const checkboxes = document.querySelectorAll(".u-checkbox");
	checkboxes.forEach(checkbox => {
		checkbox.addEventListener("change", calculateTotal);
	});

	calculateTotal();
	attachPaymentButtonEvent(); // Gọi hàm xử lý nút Thanh toán
});

// Hàm xác nhận và xử lý xóa sản phẩm
function attachDeleteEvent() {
	const deleteButtons = document.querySelectorAll("a[id^='delete']");
	const oldQuantity = document.querySelector('#totalQuantity').innerHTML;
	const newQuantity = oldQuantity - 1;

	deleteButtons.forEach(button => {
		button.addEventListener("click", function(event) {
			event.preventDefault(); // Ngăn chặn hành vi mặc định của thẻ <a>

			if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này?")) {
				const rowId = this.id.replace("delete", ""); // Lấy ID từ thuộc tính id của thẻ <a>

				// Thực hiện fetch API
				fetch(`DeleteCart/${rowId}`, { method: "GET" })
					.then(response => {
						if (!response.ok) {
							throw new Error("Xóa sản phẩm không thành công.");
						}

						// Xóa hàng có cùng ID khỏi DOM
						const rowElement = document.getElementById(rowId);
						if (rowElement) {
							rowElement.remove();
							document.querySelector('#totalQuantity').innerHTML = newQuantity;
						}

						console.log(`Sản phẩm ${rowId} đã được xóa thành công.`);
						// Cập nhật tổng tiền sau khi xóa
						calculateTotal();
					})
					.catch(error => {
						console.error("Lỗi khi xóa sản phẩm:", error);
						alert("Không thể xóa sản phẩm. Vui lòng thử lại.");
					});
			}
		});
	});
}

// Gọi hàm khởi tạo sau khi DOM tải xong
document.addEventListener("DOMContentLoaded", function() {
	attachDeleteEvent(); // Gắn sự kiện cho các nút xóa
});

