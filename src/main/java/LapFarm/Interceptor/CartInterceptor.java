package LapFarm.Interceptor;

import LapFarm.DTO.CartDTO;
import LapFarm.Service.CartServiceImp;
import LapFarm.Entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;

public class CartInterceptor implements HandlerInterceptor {

    @Autowired
    private CartServiceImp cartService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Lấy session từ request
        HttpSession session = request.getSession();

        // Kiểm tra người dùng đã đăng nhập chưa
        AccountEntity account = (AccountEntity) session.getAttribute("user");
        if (account != null) {
            // Lấy giỏ hàng từ cơ sở dữ liệu và lưu vào session
            HashMap<Integer, CartDTO> cart = cartService.getCartFromDatabase(account.getUserInfo().getUserId());
            session.setAttribute("CartSession", cart);
        } else {
            // Nếu chưa đăng nhập, khởi tạo giỏ hàng rỗng
            session.setAttribute("CartSession", new HashMap<Integer, CartDTO>());
        }

        return true; // Tiếp tục xử lý request
    }
}
