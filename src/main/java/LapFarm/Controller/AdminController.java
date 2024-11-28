package LapFarm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.OrdersDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DAO.UserDAO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.DTO.UserInfoDTO;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ProductEntity;
import LapFarm.Entity.UserInfoEntity;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
    private CategoryDAO categoryDAO;
	@Autowired
	private OrdersDAO ordersDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/home" , method = RequestMethod.GET)
	public String index(ModelMap model) {
		// Lấy danh sách categories từ DAO
        List<CategoryEntity> categories = categoryDAO.getAllCategories();

        // Đưa danh sách vào Model để đẩy sang view
        model.addAttribute("categories", categories);
		return "/admin/orders";
	}
	
	@RequestMapping(value = { "/orders" }, method = RequestMethod.GET)
	public String ordersIndex(ModelMap model) {
		// Lấy danh sách categories từ DAO
        List<CategoryEntity> categories = categoryDAO.getAllCategories();
        List<OrdersDTO> orders = ordersDAO.getAllOrdersWithUserFullname();

        // Đưa danh sách vào Model để đẩy sang view
        model.addAttribute("categories", categories);
        model.addAttribute("orders", orders);
		return "/admin/orders/index";
	}
	
	@RequestMapping(value = { "/product" }, method = RequestMethod.GET)
	public String categoryIndex(@RequestParam("category") int id, ModelMap model) {
	    // Lấy danh sách từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
	    List<ProductDTO> products = productDAO.getProductsByCategory(id);
	    // Đưa danh sách vào Model để đẩy sang view
	    model.addAttribute("categories", categories);
	    model.addAttribute("products", products); // Sản phẩm của danh mục

	    // Trả về view cho trang quản lý sản phẩm của danh mục
	    return "/admin/products/category";
	}
	
	@RequestMapping(value = { "/product/add-category" }, method = RequestMethod.GET)
	public String showForm(ModelMap model) {
	    // Lấy danh sách từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
	    // Đưa danh sách vào Model để đẩy sang view
	    model.addAttribute("categories", categories);

	    // Trả về view cho trang quản lý sản phẩm của danh mục
	    return "/admin/products/addCategory";
	}
	
	@RequestMapping(value = "/product/add-category", method = RequestMethod.POST)
    public String addCategory(@RequestParam("categoryName") String categoryName, ModelMap model) {
		// Lấy danh sách từ DAO
				List<CategoryEntity> categories = categoryDAO.getAllCategories();
				model.addAttribute("categories", categories);
        try {
            // Kiểm tra nếu loại hàng đã tồn tại
            if (categoryDAO.checkCategory(categoryName)) {
                model.addAttribute("message", "Loại hàng '" + categoryName + "' đã tồn tại!");
                return "/admin/products/addCategory"; // Quay lại trang thêm loại hàng
            }

            // Nếu chưa tồn tại, thêm loại hàng mới
            CategoryEntity category = new CategoryEntity();
            category.setNameCategory(categoryName);
            categoryDAO.saveCategory(category);

            // Thêm thông báo thành công
            model.addAttribute("message", "Loại hàng '" + categoryName + "' đã được thêm thành công!");
        } catch (Exception e) {
            // Thêm thông báo lỗi
            model.addAttribute("message", "Loại hàng '" + categoryName + "' đã tồn tại!");
        }

        // Quay lại trang thêm loại hàng
        return "/admin/products/addCategory";
    }

	
	@RequestMapping(value = { "/manage-user" }, method = RequestMethod.GET)
	public String userIndex(ModelMap model) {
	    // Lấy danh sách từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
		List<UserInfoDTO> userInfoDTO = userDAO.getAllUserInfoWithOrderCount();
	    // Đưa danh sách vào Model để đẩy sang view
		
	    model.addAttribute("categories", categories);
	    model.addAttribute("userInfoDTO", userInfoDTO);

	    // Trả về view cho trang quản lý sản phẩm của danh mục
	    return "/admin/user/index";
	}
}
