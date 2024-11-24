document.addEventListener("DOMContentLoaded", function () {
    const searchForm = document.getElementById('searchForm');
    const searchInput = document.getElementById('searchInput');

    searchForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Ngăn chặn hành vi submit mặc định của form
    });

    searchInput.addEventListener('keyup', function (event) {
        if (event.key === 'Enter') {
            searchProducts();
        }
    });
});

function searchProducts() {
    const filter = document.getElementById('searchInput').value.toLowerCase();
    const products = document.querySelectorAll('.product'); // Sử dụng .product

    products.forEach(product => {
        const productNameElement = product.querySelector('.product-name a');
        if (productNameElement) {
            const productName = productNameElement.textContent.toLowerCase();
            if (productName.includes(filter)) {
                product.style.display = '';
            } else {
                product.style.display = 'none';
            }
        }
    });
}