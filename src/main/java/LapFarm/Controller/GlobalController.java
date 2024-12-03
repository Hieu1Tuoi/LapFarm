package LapFarm.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import LapFarm.DTO.CartDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Service.BaseServiceImp;
import LapFarm.Service.CartServiceImp;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private BaseServiceImp baseService;
    
    @Autowired
    private CartServiceImp cartService;

    @ModelAttribute("categories")
    public Object getCategories() {
        return baseService.getCategoryEntities();
    }
    
    @ModelAttribute("CartSession")
    public HashMap<Integer, CartDTO> getCart(HttpSession session) {
        // Kiểm tra người dùng đã đăng nhập chưa
        AccountEntity account = (AccountEntity) session.getAttribute("user");
        if (account != null) {
            // Lấy giỏ hàng từ cơ sở dữ liệu nếu đã đăng nhập
            return cartService.getCartFromDatabase(account.getUserInfo().getUserId());
        } else {
            // Nếu chưa đăng nhập, trả về g	iỏ hàng rỗng
            return new HashMap<>();
        }
    }
}
