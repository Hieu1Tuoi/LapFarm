package LapFarm.Filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo nếu cần
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String clientIP = request.getRemoteAddr();
        String protocol = request.getProtocol();

        // Ghi log
        logger.info("Request received from IP: {}", clientIP);
        logger.info("Protocol: {}", protocol);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Dọn dẹp nếu cần
    }
}
