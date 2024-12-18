package LapFarm.Controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		try {
			_mvShare.addObject("Zerocode",SecureUrlUtil.encrypt("0"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	    HashMap<Integer, CartDTO> cart;

	    if (account != null) {
	        // Nếu người dùng đã đăng nhập, lấy giỏ hàng từ cơ sở dữ liệu
	        cart = cartService.getCartFromDatabase(account.getUserInfo().getUserId());

	        // Duyệt qua giỏ hàng và mã hóa ID sản phẩm
	        for (Map.Entry<Integer, CartDTO> entry : cart.entrySet()) {
	            CartDTO cartDTO = entry.getValue();
	            ProductDTO productDTO = cartDTO.getProduct();
	            try {
	                // Mã hóa ID sản phẩm và gán lại cho ProductDTO trong giỏ hàng
	                String encryptedId = SecureUrlUtil.encrypt(String.valueOf(productDTO.getIdProduct()));
	                productDTO.setEncryptedId(encryptedId);
	            } catch (Exception e) {
	                e.printStackTrace(); // Xử lý lỗi nếu có
	            }
	        }
//	        session.setAttribute("Cart", cart);
	    } else {
	        // Nếu chưa đăng nhập, lấy giỏ hàng từ session (hoặc khởi tạo giỏ hàng rỗng)
	        cart = (HashMap<Integer, CartDTO>) session.getAttribute("Cart");
	        if (cart == null) {
	            cart = new HashMap<>();
	        }

	        // Duyệt qua giỏ hàng và mã hóa ID sản phẩm
	        for (Map.Entry<Integer, CartDTO> entry : cart.entrySet()) {
	            CartDTO cartDTO = entry.getValue();
	            ProductDTO productDTO = cartDTO.getProduct();
	            try {
	                // Mã hóa ID sản phẩm và gán lại cho ProductDTO trong giỏ hàng
	                String encryptedId = SecureUrlUtil.encrypt(String.valueOf(productDTO.getIdProduct()));
	                productDTO.setEncryptedId(encryptedId);
	            } catch (Exception e) {
	                e.printStackTrace(); // Xử lý lỗi nếu có
	            }
	        }
	    }

	    // Lưu giỏ hàng vào session
	    session.setAttribute("Cart", cart);
	}

	
	   @ModelAttribute("categories")
	    public Object getCategories() {
	        return _baseService.getCategoryEntities();
	    }
	
}
