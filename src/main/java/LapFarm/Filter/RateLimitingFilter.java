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

    private static final int MAX_REQUESTS_PER_MINUTE = 100; // Giới hạn yêu cầu mỗi phút
    private static final int MAX_VIOLATIONS = 10;          // Số lần vi phạm tối đa trước khi bị cấm
    private static final long BAN_DURATION_MS = 5 * 60 * 1000; // Thời gian cấm (5 phút)

    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> timestampMap = new ConcurrentHashMap<>();
    private final Map<String, Integer> violationCounts = new ConcurrentHashMap<>(); // Đếm số lần vi phạm
    private final Map<String, Long> banEndTimeMap = new ConcurrentHashMap<>(); // Thời gian cấm kết thúc

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String clientIP = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        // Kiểm tra nếu IP đang bị cấm
        if (banEndTimeMap.containsKey(clientIP)) {
            long banEndTime = banEndTimeMap.get(clientIP);
            if (currentTime < banEndTime) {
                // Thông báo cấm
                String banMessage = String.format(
                    "\n*****************************\n" +
                    " YOU ARE TEMPORARILY BANNED \n" +
                    "Your IP: %s\n" +
                    "Ban Duration: Until %tc\n" +
                    "*****************************\n",
                    clientIP, banEndTime
                );
                response.getWriter().write(banMessage);
                return;
            } else {
                // Xóa khỏi danh sách bị cấm nếu thời gian cấm đã hết
                banEndTimeMap.remove(clientIP);
                violationCounts.put(clientIP, 0); // Reset số lần vi phạm
            }
        }

        // Tạo mới nếu IP chưa tồn tại
        requestCounts.putIfAbsent(clientIP, new AtomicInteger(0));
        timestampMap.putIfAbsent(clientIP, currentTime);
        violationCounts.putIfAbsent(clientIP, 0);

        // Reset bộ đếm nếu qua 1 phút
        if (currentTime - timestampMap.get(clientIP) > 60000) {
            timestampMap.put(clientIP, currentTime);
            requestCounts.get(clientIP).set(0);
        }

        // Tăng bộ đếm và kiểm tra giới hạn
        if (requestCounts.get(clientIP).incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            int violations = violationCounts.get(clientIP) + 1;
            violationCounts.put(clientIP, violations);

            if (violations > MAX_VIOLATIONS) {
                // Nếu vi phạm vượt quá giới hạn, cấm IP trong 15 phút
                banEndTimeMap.put(clientIP, currentTime + BAN_DURATION_MS);

                String banNotice = String.format(
                    "\n====================================\n" +
                    "  ACCESS DENIED: TEMPORARY BAN  \n" +
                    "Your IP: %s\n" +
                    "Reason: Too many violations.\n" +
                    "Ban Duration: 5 minutes\n" +
                    "====================================\n",
                    clientIP
                );
                response.getWriter().write(banNotice);
                return;
            }

            // Thông báo vượt quá giới hạn
            String warningMessage = String.format(
                "\n------------------------------------\n" +
                " WARNING: TOO MANY REQUESTS \n" +
                "Your IP: %s\n" +
                "Please slow down and try again later.\n" +
                "------------------------------------\n",
                clientIP
            );
            response.getWriter().write(warningMessage);
            return;
        }

        // Nếu không vượt giới hạn, tiếp tục chuỗi xử lý
        chain.doFilter(request, response);
    }
}