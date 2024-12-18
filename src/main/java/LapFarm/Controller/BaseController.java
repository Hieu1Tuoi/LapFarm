package LapFarm.Controller;



import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import LapFarm.DTO.CartDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.Entity.AccountEntity;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Service.BaseServiceImp;
import LapFarm.Service.CartServiceImp;
import LapFarm.Utils.SecureUrlUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;


@Transactional
@Controller
public class BaseController {
	@Autowired
	BaseServiceImp _baseService;
	
	@Autowired
	CartServiceImp cartService;


	public ModelAndView _mvShare = new ModelAndView();
	
	
	
	public ModelAndView Init() {
		  // Lấy danh sách Category
	    List<CategoryEntity> categories = _baseService.getCategoryEntities();

	    // Mã hóa idCategory cho mỗi CategoryEntity
	    categories.forEach(category -> {
	        try {
	            String encryptedId = SecureUrlUtil.encrypt(String.valueOf(category.getIdCategory()));
	            category.setEncryptedId(encryptedId); // Thêm setter cho trường này trong CategoryEntity
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });

	    // Thêm danh sách categories với idCategory đã mã hóa vào ModelAndView
	    _mvShare.addObject("categories", categories);

		// Lấy danh sách Brand và mã hóa idBrand
		List<BrandEntity> brands = _baseService.getBrandEntities();
		brands.forEach(brand -> {
		    try {
		        // Mã hóa idBrand và gán vào đối tượng BrandEntity
		        String encryptedId = SecureUrlUtil.encrypt(String.valueOf(brand.getIdBrand()));
		        brand.setEncryptedId(encryptedId) ;// Thêm setter cho trường này trong BrandEntity
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		});

		// Thêm danh sách brands với idBrand đã mã hóa vào ModelAndView
		_mvShare.addObject("brands", brands);


		// Lấy số lượng sản phẩm theo tất cả danh mục
		_mvShare.addObject("productCounts",
				_baseService.getProductCountByAllCategories(_baseService.getCategoryEntities())); // Truyền Map vào
																									// Model

		// Lấy số lượng sản phẩm theo tất cả brand
		_mvShare.addObject("productCountsByBrand",
				_baseService.getProductCountByAllBrands(_baseService.getBrandEntities()));

		 // Lấy sản phẩm top bán chạy nhất
	    List<ProductDTO> topSellingProducts = _baseService.getTop5ProductsByLowestQuantity();

	    // Mã hóa ID sản phẩm trong danh sách top selling
	    topSellingProducts.forEach(product -> {
	        try {
	            product.setEncryptedId(SecureUrlUtil.encrypt(String.valueOf(product.getIdProduct())));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });

	    _mvShare.addObject("products_top_sell", topSellingProducts);
		
		return _mvShare;
	}

	public void addCartToSession(HttpSession session) {
	    AccountEntity account = (AccountEntity) session.getAttribute("user");
	    if (account != null) {
	        // Lấy giỏ hàng từ cơ sở dữ liệu và lưu vào session
	        HashMap<Integer, CartDTO> cart = cartService.getCartFromDatabase(account.getUserInfo().getUserId());
	        session.setAttribute("Cart", cart);
	    }
//	    } else {
//	        // Nếu chưa đăng nhập, khởi tạo giỏ hàng rỗng
//	        session.setAttribute("Cart", new HashMap<Integer, CartDTO>());
//	    }
	}
	
	   @ModelAttribute("categories")
	    public Object getCategories() {
	        return _baseService.getCategoryEntities();
	    }
	
}
