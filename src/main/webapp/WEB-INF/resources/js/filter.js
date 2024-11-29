document.addEventListener("DOMContentLoaded", function() {
	const minPriceSlider = document.getElementById('minPriceSlider');
	const maxPriceSlider = document.getElementById('maxPriceSlider');
	const minPriceLabel = document.getElementById('minPriceLabel');
	const maxPriceLabel = document.getElementById('maxPriceLabel');

	function updatePriceLabels() {
		let minPrice = parseInt(minPriceSlider.value);
		let maxPrice = parseInt(maxPriceSlider.value);

		// Đảm bảo minPrice không lớn hơn maxPrice và không nhỏ hơn 0
		if (minPrice > maxPrice - 1000) {
			minPriceSlider.value = maxPrice - 1000;
			minPrice = parseInt(minPriceSlider.value);
		}

		// Đảm bảo maxPrice không nhỏ hơn minPrice
		if (maxPrice < minPrice + 1000) {
			maxPriceSlider.value = minPrice + 1000;
			maxPrice = parseInt(maxPriceSlider.value);
		}

		minPriceLabel.textContent = minPrice.toLocaleString('vi-VN') + ' ₫';
		maxPriceLabel.textContent = maxPrice.toLocaleString('vi-VN') + ' ₫';
	}

	minPriceSlider.addEventListener('input', updatePriceLabels);
	maxPriceSlider.addEventListener('input', updatePriceLabels);

	// Khởi tạo giá trị ban đầu
	updatePriceLabels();
});

document.addEventListener("DOMContentLoaded", function() {
	const minPriceSlider = document.getElementById('minPriceSlider');
	const maxPriceSlider = document.getElementById('maxPriceSlider');
	const minPriceLabel = document.getElementById('minPriceLabel');
	const maxPriceLabel = document.getElementById('maxPriceLabel');
	const idBrandInput = document.getElementById('idBrandInput');

	// Lấy tham số từ URL
	const urlParams = new URLSearchParams(window.location.search);
	let priceRange = urlParams.get('priceRange');
	let idCategory = urlParams.get('idCategory') || '0'; // giá trị mặc định là 0
	const searchtext = urlParams.get('searchtext') || ''; // giá trị mặc định là rỗng

	// Lấy thẻ <select> theo ID hoặc class
	const categorySelect = document.getElementById('categorySelect');
	idCategory = categorySelect ? categorySelect.value : idCategory; // đảm bảo không bị null

	if (priceRange) {
		const prices = priceRange.split('-');
		if (prices.length === 2 && prices[0] && prices[1]) {
			minPriceSlider.value = prices[0];
			maxPriceSlider.value = prices[1];
			updatePriceLabels();
		}
	}

	function updatePriceLabels() {
		const minPrice = parseInt(minPriceSlider.value);
		const maxPrice = parseInt(maxPriceSlider.value);

		minPriceLabel.textContent = minPrice.toLocaleString('vi-VN') + ' ₫';
		maxPriceLabel.textContent = maxPrice.toLocaleString('vi-VN') + ' ₫';
	}

	minPriceSlider.addEventListener('input', updatePriceLabels);
	maxPriceSlider.addEventListener('input', updatePriceLabels);

	// Khởi tạo giá trị ban đầu
	updatePriceLabels();

	window.applyFilter = function() {
		const minPrice = minPriceSlider.value;
		const maxPrice = maxPriceSlider.value;

		// Lấy giá trị priceRange hiện tại từ URL
		const currentPriceRange = urlParams.get('priceRange');

		// Bỏ tham số 'page' nếu có trong URL
		urlParams.delete('page');

		// Tạo giá trị priceRange mới
		const newPriceRange = `${minPrice}-${maxPrice}`;

		// Chỉ thực hiện điều hướng nếu khoảng giá mới khác với khoảng giá hiện tại
		if (currentPriceRange !== newPriceRange) {
			let newUrl;

			if (window.location.href.includes('/search?')) {
				// Đã có search trong URL
				urlParams.set('priceRange', newPriceRange);
				newUrl = `${window.location.origin}${window.location.pathname}?${urlParams.toString()}`;
			} else {
				if (idBrandInput && idBrandInput.value) {
					const idBrandValue = idBrandInput.value;
					newUrl = `${window.location.origin}/LapFarm/search?idCategory=${idCategory}&searchtext=${searchtext}&priceRange=${newPriceRange}&idBrand=${idBrandValue}`;
				} else {
					newUrl = `${window.location.origin}/LapFarm/search?idCategory=${idCategory}&searchtext=${searchtext}&priceRange=${newPriceRange}`;
				}
			}

			window.location.href = newUrl;
		}
	}

	window.resetFilter = function() {
		minPriceSlider.value = minPriceSlider.min;
		maxPriceSlider.value = maxPriceSlider.max;
		updatePriceLabels();
	}
});