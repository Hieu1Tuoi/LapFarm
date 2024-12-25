package LapFarm.Filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimitingFilter implements Filter {

    private static final int MAX_REQUESTS_PER_MINUTE = 120; // Giới hạn yêu cầu mỗi phút
    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> timestampMap = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo nếu cần
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String clientIP = request.getRemoteAddr(); // Lấy địa chỉ IP của client
        long currentTime = System.currentTimeMillis();

        // Tạo mới nếu IP chưa tồn tại
        requestCounts.putIfAbsent(clientIP, new AtomicInteger(0));
        timestampMap.putIfAbsent(clientIP, currentTime);

        // Reset bộ đếm nếu qua 1 phút
        if (currentTime - timestampMap.get(clientIP) > 20000) {
            timestampMap.put(clientIP, currentTime);
            requestCounts.get(clientIP).set(0);
        }

        // Tăng bộ đếm và kiểm tra
        if (requestCounts.get(clientIP).incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            response.getWriter().write("Too many requests, please try again later.");
            return;
        }

        // Nếu không vượt giới hạn, tiếp tục chuỗi xử lý
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Dọn dẹp nếu cần
    }
}
