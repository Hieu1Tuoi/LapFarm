<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/layout/adminHeader.jsp"%>
<%@ include file="/WEB-INF/views/admin/layout/adminSidebar.jsp"%>
<style>
    /* Đảm bảo các biểu đồ được căn giữa */
    .chart-container {
        position: relative;
        height: 400px;
        width: 100%;
        background-color: #f9f9f9; /* Nền sáng cho biểu đồ */
        border-radius: 10px; /* Bo góc cho khung biểu đồ */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Tạo hiệu ứng đổ bóng nhẹ */
        margin-top: 20px;
        display: flex;
        justify-content: center;  /* Căn giữa theo chiều ngang */
        align-items: center;  /* Căn giữa theo chiều dọc */
    }

    /* Thêm padding cho tiêu đề biểu đồ */
    .content-header h1 {
        font-size: 28px;
        font-weight: 600;
        color: #333;
        margin-bottom: 30px;
        text-align: center; /* Căn giữa tiêu đề */
    }

    /* Đảm bảo rằng biểu đồ sẽ không tràn ra ngoài */
    .chartjs-render-monitor {
        border-radius: 8px;
    }
</style>

<div class="content-wrapper">
    <section class="content-header">
        <select id="yearSelector" onchange="updateChart()">
            <c:forEach var="year" items="${years}">
                <option value="${year}" <c:if test="${year == currentYear}">selected</c:if>>${year}</option>
            </c:forEach>
        </select>
    </section>

    <h1>Doanh thu</h1>
    <section class="content">
        <!-- Biểu đồ doanh thu -->
        <div class="chart-container">
            <canvas id="myChart"></canvas>
        </div>

        <h1>Số lượng máy bán theo hãng</h1>
        <!-- Biểu đồ tròn số lượng máy bán theo hãng -->
        <div class="chart-container">
            <canvas id="brandChart"></canvas>
        </div>

        <h1>Số lượng máy bán theo danh mục</h1>
        <!-- Biểu đồ tròn số lượng máy bán theo danh mục -->
        <div class="chart-container">
            <canvas id="categoryChart"></canvas>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
            var labels = JSON.parse('${labels}');
            var revenueData = JSON.parse('${revenueData}');
            var currentYear = new Date().getFullYear();
            var productCountByBrand = JSON.parse('${productCountByBrand}');
            var productCountByCategory = JSON.parse('${productCountByCategory}');  // Dữ liệu theo danh mục

            var brandChartInstance = null;  // Biến lưu trữ đối tượng biểu đồ tròn theo hãng
            var categoryChartInstance = null;  // Biến lưu trữ đối tượng biểu đồ tròn theo danh mục

            // Biểu đồ doanh thu theo tháng
            var ctx = document.getElementById('myChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Doanh thu theo tháng',
                        data: revenueData[currentYear],
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });

            // Biểu đồ tròn số lượng máy bán theo hãng
            function updatePieChartForBrand(year) {
                if (brandChartInstance) {
                    brandChartInstance.destroy();
                }

                var brandLabels = Object.keys(productCountByBrand);
                var brandCounts = brandLabels.map(function(brand) {
                    return productCountByBrand[brand][year] || 0;
                });

                var ctxBrand = document.getElementById('brandChart').getContext('2d');
                ctxBrand.width = ctxBrand.offsetWidth;
                ctxBrand.height = ctxBrand.offsetHeight;

                brandChartInstance = new Chart(ctxBrand, {
                    type: 'pie',
                    data: {
                        labels: brandLabels,
                        datasets: [{
                            label: 'Số lượng sản phẩm bán theo hãng',
                            data: brandCounts,
                            backgroundColor: ['#FF5733', '#33FF57', '#3357FF', '#FF33A1', '#FFD433'],
                            borderColor: '#ffffff',
                            borderWidth: 2
                        }]
                    },
                    options: {
                        responsive: true
                    }
                });
            }

            // Biểu đồ tròn số lượng máy bán theo danh mục
            function updatePieChartForCategory(year) {
                if (categoryChartInstance) {
                    categoryChartInstance.destroy();
                }

                var categoryLabels = Object.keys(productCountByCategory);
                var categoryCounts = categoryLabels.map(function(category) {
                    return productCountByCategory[category][year] || 0;
                });

                var ctxCategory = document.getElementById('categoryChart').getContext('2d');
                ctxCategory.width = ctxCategory.offsetWidth;
                ctxCategory.height = ctxCategory.offsetHeight;

                categoryChartInstance = new Chart(ctxCategory, {
                    type: 'pie',
                    data: {
                        labels: categoryLabels,
                        datasets: [{
                            label: 'Số lượng sản phẩm bán theo danh mục',
                            data: categoryCounts,
                            backgroundColor: ['#FF6347', '#32CD32', '#4682B4', '#FFD700', '#8A2BE2'],
                            borderColor: '#ffffff',
                            borderWidth: 2
                        }]
                    },
                    options: {
                        responsive: true
                    }
                });
            }

            // Hàm cập nhật biểu đồ khi thay đổi năm
            function updateChart() {
                var selectedYear = document.getElementById('yearSelector').value;

                // Cập nhật dữ liệu doanh thu
                myChart.data.datasets[0].data = revenueData[selectedYear];
                myChart.update();

                // Cập nhật dữ liệu biểu đồ tròn số lượng máy bán theo hãng
                updatePieChartForBrand(selectedYear);

                // Cập nhật dữ liệu biểu đồ tròn số lượng máy bán theo danh mục
                updatePieChartForCategory(selectedYear);
            }

            // Khởi tạo biểu đồ tròn số lượng máy bán theo hãng cho năm mặc định
            updatePieChartForBrand(currentYear);
            // Khởi tạo biểu đồ tròn số lượng máy bán theo danh mục cho năm mặc định
            updatePieChartForCategory(currentYear);
        </script>
    </section>
</div>

<%@ include file="/WEB-INF/views/admin/layout/adminFooter.jsp"%>
