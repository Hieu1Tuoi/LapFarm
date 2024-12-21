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

    // Cập nhật giá trị ban đầu nếu có giá trị trong priceRange
    if (priceRange) {
        const prices = priceRange.split('-');
        if (prices.length === 2 && prices[0] && prices[1]) {
            minPriceSlider.value = prices[0];
            maxPriceSlider.value = prices[1];
            updatePriceLabels();
        }
    }

    // Cập nhật nhãn giá trị của thanh trượt
    function updatePriceLabels() {
        const minPrice = parseInt(minPriceSlider.value);
        const maxPrice = parseInt(maxPriceSlider.value);

        minPriceLabel.textContent = minPrice.toLocaleString('vi-VN') + ' ₫';
        maxPriceLabel.textContent = maxPrice.toLocaleString('vi-VN') + ' ₫';
    }

    // Điều chỉnh giá trị minPriceSlider và maxPriceSlider khi thay đổi
    function handleSliderChange(slider) {
        const minValue = parseInt(minPriceSlider.value);
        const maxValue = parseInt(maxPriceSlider.value);

        if (slider === minPriceSlider) {
            // Đảm bảo min không lớn hơn nửa max
            if (minValue > maxValue / 2) {
                slider.value = Math.floor(maxValue / 2);
            }
        } else if (slider === maxPriceSlider) {
            // Đảm bảo max không nhỏ hơn 2 lần min
            if (maxValue < minValue * 2) {
                slider.value = minValue * 2;
            }
        }
        updatePriceLabels();
    }

    // Lắng nghe sự kiện thay đổi giá trị của các thanh trượt
    minPriceSlider.addEventListener('input', function() {
        handleSliderChange(minPriceSlider);
    });

    maxPriceSlider.addEventListener('input', function() {
        handleSliderChange(maxPriceSlider);
    });

    // Khởi tạo giá trị ban đầu
    updatePriceLabels();

    // Hàm áp dụng bộ lọc
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

            // Nếu URL có chứa search
            if (window.location.href.includes('/search?')) {
                urlParams.set('priceRange', newPriceRange);
                newUrl = `${window.location.origin}${window.location.pathname}?${urlParams.toString()}`;
            } else {
                // Nếu không có search trong URL
                if (idBrandInput && idBrandInput.value) {
                    const idBrandValue = idBrandInput.value;
                    newUrl = `${window.location.origin}/LapFarm/search?idCategory=${encodeURIComponent(idCategory)}&searchtext=${encodeURIComponent(searchtext)}&priceRange=${encodeURIComponent(newPriceRange)}&idBrand=${encodeURIComponent(idBrandValue)}`;
                } else {
                    newUrl = `${window.location.origin}/LapFarm/search?idCategory=${encodeURIComponent(idCategory)}&searchtext=${encodeURIComponent(searchtext)}&priceRange=${encodeURIComponent(newPriceRange)}`;
                }
            }

            window.location.href = newUrl;
        }
    };

    // Hàm reset bộ lọc
    window.resetFilter = function() {
        minPriceSlider.value = minPriceSlider.min;
        maxPriceSlider.value = maxPriceSlider.max;
        updatePriceLabels();
    };
});
