package LapFarm.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import LapFarm.DAO.BrandDAO;
import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.OrdersDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DAO.UserDAO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.DTO.UserInfoDTO;
import LapFarm.Entity.BrandEntity;
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
	@Autowired
	private BrandDAO brandDAO;
	
	public static String normalizeString(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Nếu chuỗi null hoặc rỗng thì trả về ngay
        }

        // Bước 1: Xóa khoảng trắng dư thừa ở đầu và cuối
        input = input.trim();

        // Bước 2: Xóa khoảng trắng dư thừa ở giữa các từ
        input = input.replaceAll("\\s+", " ");

        // Bước 3: Viết hoa chữ cái đầu tiên của chuỗi, các ký tự còn lại viết thường
        input = Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();

        return input;
    }
	
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
	
	@RequestMapping(value = { "/categories" }, method = RequestMethod.GET)
	public String showListCatogory(ModelMap model) {
	    // Lấy danh sách từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
	    // Đưa danh sách vào Model để đẩy sang view
	    model.addAttribute("categories", categories);

	    // Trả về view cho trang quản lý sản phẩm của danh mục
	    return "/admin/categories/index";
	}
	
	@RequestMapping(value = { "/categories/add-category" }, method = RequestMethod.GET)
	public String showFormAddCategory(ModelMap model) {
	    // Lấy danh sách từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
	    // Đưa danh sách vào Model để đẩy sang view
	    model.addAttribute("categories", categories);

	    // Trả về view cho trang quản lý sản phẩm của danh mục
	    return "/admin/categories/addCategory";
	}
	
	@RequestMapping(value = "/categories/add-category", method = RequestMethod.POST)
    public String addCategory(@RequestParam("categoryName") String categoryName, ModelMap model) {
		// Lấy danh sách từ DAO
				List<CategoryEntity> categories = categoryDAO.getAllCategories();
				model.addAttribute("categories", categories);
        try {
        	categoryName = normalizeString(categoryName);
            // Kiểm tra nếu loại hàng đã tồn tại
            if (categoryDAO.checkCategoryByName(categoryName)) {
                model.addAttribute("message", "Loại hàng '" + categoryName + "' đã tồn tại!");
                return "/admin/categories/addCategory"; // Quay lại trang thêm loại hàng
            }

            // Nếu chưa tồn tại, thêm loại hàng mới
            CategoryEntity category = new CategoryEntity();
            category.setNameCategory(categoryName);
            categoryDAO.saveCategory(category);

            // Thêm thông báo thành công
            model.addAttribute("message", "Loại hàng '" + categoryName + "' đã được thêm thành công!");
        } catch (Exception e) {
            // Thêm thông báo lỗi
            model.addAttribute("message", "Thêm loại hàng '" + categoryName + "' thất bại!");
        }

        // Quay lại trang thêm loại hàng
        return "/admin/categories/addCategory";
    }
	
	@RequestMapping(value = { "/brands" }, method = RequestMethod.GET)
	public String brandsIndex(ModelMap model) {
		// Lấy danh sách categories từ DAO
        List<CategoryEntity> categories = categoryDAO.getAllCategories();
        List<BrandEntity> brands = brandDAO.getAllBrands();

        // Đưa danh sách vào Model để đẩy sang view
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
		return "/admin/brands/index";
	}
	
	@RequestMapping(value = { "/brands/add-brand" }, method = RequestMethod.GET)
	public String showFormAddBrand(ModelMap model) {
	    // Lấy danh sách từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
        List<BrandEntity> brands = brandDAO.getAllBrands();
	    // Đưa danh sách vào Model để đẩy sang view
	    model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);

	    // Trả về view cho trang quản lý sản phẩm của danh mục
	    return "/admin/brands/addBrand";
	}
	
	@RequestMapping(value = "/brands/add-brand", method = RequestMethod.POST)
    public String addBrand(@RequestParam("brandName") String brandName, ModelMap model) {
		// Lấy danh sách từ DAO
				List<CategoryEntity> categories = categoryDAO.getAllCategories();
		        List<BrandEntity> brands = brandDAO.getAllBrands();
				model.addAttribute("categories", categories);
		        model.addAttribute("brands", brands);
        try {
        	brandName = normalizeString(brandName);
            // Kiểm tra nếu loại hàng đã tồn tại
            if (brandDAO.checkBrandByName(brandName)) {
                model.addAttribute("message", "Hãng '" + brandName + "' đã tồn tại!");
                return "/admin/brands/addBrand"; // Quay lại trang thêm loại hàng
            }

            // Nếu chưa tồn tại, thêm loại hàng mới
            BrandEntity brand = new BrandEntity();
            brand.setNameBrand(brandName);
            brandDAO.saveBrand(brand);

            // Thêm thông báo thành công
            model.addAttribute("message", "Hãng '" + brandName + "' đã được thêm thành công!");
        } catch (Exception e) {
            // Thêm thông báo lỗi
            model.addAttribute("message", "Thêm hãng '" + brandName + "' thất bại!");
        }

        // Quay lại trang thêm loại hàng
        return "/admin/brands/addBrand";
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
