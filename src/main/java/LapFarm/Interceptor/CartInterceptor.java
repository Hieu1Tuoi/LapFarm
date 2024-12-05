package LapFarm.Interceptor;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.DTO.CartDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Service.CartServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CartInterceptor implements HandlerInterceptor {

    @Autowired
    private CartServiceImp cartService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // Lấy thông tin người dùng từ session
        AccountEntity user = (AccountEntity) session.getAttribute("user");

        // Chỉ đồng bộ giỏ hàng nếu người dùng đã đăng nhập
        if (user != null) {
            try {
                // Đồng bộ giỏ hàng từ database
                HashMap<Integer, CartDTO> cart = cartService.getCartFromDatabase(user.getUserInfo().getUserId());

                // Cập nhật giỏ hàng vào session
                session.setAttribute("Cart", cart);
                session.setAttribute("TotalQuantyCart", cartService.TotalQuanty(cart));
                session.setAttribute("TotalPriceCart", cartService.TotalPrice(cart));

            } catch (Exception e) {
                // Ghi log lỗi để dễ dàng theo dõi
                System.err.println("Lỗi khi đồng bộ giỏ hàng: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return true; // Tiếp tục xử lý yêu cầu
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Không cần xử lý gì thêm ở đây
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Không cần xử lý gì thêm ở đây
    }
}