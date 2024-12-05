package LapFarm.Controller;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import LapFarm.DAO.BrandDAO;
import LapFarm.DAO.CategoryDAO;
import LapFarm.DAO.ImageDAO;
import LapFarm.DAO.NotificationDAO;
import LapFarm.DAO.OrderDetailDAO;
import LapFarm.DAO.OrdersDAO;
import LapFarm.DAO.ProductDAO;
import LapFarm.DAO.ProductDetailDAO;
import LapFarm.DAO.UserDAO;
import LapFarm.DTO.OrderDetailDTO;
import LapFarm.DTO.OrdersDTO;
import LapFarm.DTO.ProductDTO;
import LapFarm.DTO.UserInfoDTO;
import LapFarm.Entity.BrandEntity;
import LapFarm.Entity.CategoryEntity;
import LapFarm.Entity.ImageEntity;
import LapFarm.Entity.NotificationEntity;
import LapFarm.Entity.OrdersEntity;
import LapFarm.Entity.ProductDetailEntity;
import LapFarm.Entity.ProductEntity;
import jakarta.servlet.ServletContext;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	ServletContext context;
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
	@Autowired
	private OrderDetailDAO orderDetailDAO;
	@Autowired
	private ProductDetailDAO productDetailDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private NotificationDAO notificationDAO;

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

	public static String normalizeStringEachWord(String input) {
		if (input == null || input.isEmpty()) {
			return input; // Nếu chuỗi null hoặc rỗng thì trả về ngay
		}

		// Bước 1: Xóa khoảng trắng dư thừa ở đầu và cuối
		input = input.trim();

		// Bước 2: Xóa khoảng trắng dư thừa ở giữa các từ
		input = input.replaceAll("\\s+", " ");

		// Bước 3: Viết hoa chữ cái đầu tiên của mỗi từ, các ký tự còn lại viết thường
		String[] words = input.split(" ");
		StringBuilder normalizedString = new StringBuilder();

		for (String word : words) {
			// Viết hoa chữ cái đầu và các chữ cái còn lại viết thường
			normalizedString.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase())
					.append(" ");
		}

		// Xóa khoảng trắng cuối cùng
		return normalizedString.toString().trim();
	}

	public static String normalizeStringAllUppercase(String input) {
		if (input == null || input.isEmpty()) {
			return input; // Nếu chuỗi null hoặc rỗng thì trả về ngay
		}

		// Bước 1: Xóa khoảng trắng dư thừa ở đầu và cuối
		input = input.trim();

		// Bước 2: Xóa khoảng trắng dư thừa ở giữa các từ
		input = input.replaceAll("\\s+", " ");

		// Bước 3: Chuyển tất cả các ký tự trong chuỗi thành chữ hoa
		return input.toUpperCase();
	}

	public String saveImage(MultipartFile image) {
		if (image.isEmpty()) {
			return null; // Trả về null nếu file không có nội dung
		} else {
			try {
				// Lấy thư mục lưu ảnh (bạn có thể thay đổi đường dẫn này theo nhu cầu)
				String workspacePath = context.getRealPath("/uploads/images/");
				// Lùi ra folder lưu ảnh
				for (int i = 0; i < 8; i++) {
					workspacePath = Paths.get(workspacePath).getParent().toString(); // Di chuyển lên 1 bậc
				}
				System.out.println(workspacePath + "/LapFarm/src/main/webapp/WEB-INF/resources/img/");
				String uploadDir = workspacePath + "/LapFarm/src/main/webapp/WEB-INF/resources/img/";

				// Tạo thư mục nếu chưa tồn tại
				File directory = new File(uploadDir);
				if (!directory.exists()) {
					directory.mkdirs();
				}

				// Tạo tên file duy nhất bằng cách thêm thời gian vào tên file gốc để tránh
				// trùng
				String originalFileName = image.getOriginalFilename();
				String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

				// Đường dẫn lưu ảnh
				String imagePath = uploadDir + uniqueFileName;

				// Lưu file vào thư mục
				File dest = new File(imagePath);
				image.transferTo(dest);

				// In ra đường dẫn lưu file (có thể thay bằng logger để ghi log)
				System.out.println("Image saved to: " + imagePath);

				return "/LapFarm/resources/img/" + uniqueFileName; // Trả về đường dẫn của file đã lưu
			} catch (Exception e) {
				// In thông báo lỗi để tiện gỡ lỗi (có thể thay bằng logger)
				System.out.println("Loi khi luu anh!!!!!!!!!!!!!!!!!");
				e.printStackTrace();
				return null; // Trả về null nếu có lỗi xảy ra
			}
		}
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String index(ModelMap model) throws JsonProcessingException {
	    // Lấy năm hiện tại
	    int currentYear = java.time.Year.now().getValue();

	    // Tạo danh sách 3 năm gần nhất
	    List<Integer> years = Arrays.asList(currentYear - 2, currentYear - 1, currentYear);

	    // Chuẩn bị dữ liệu doanh thu cho từng năm
	    ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, List<Integer>> revenueData = new HashMap<>();

	    for (int year : years) {
	        List<Integer> monthlyRevenue = new ArrayList<>();
	        for (int month = 1; month <= 12; month++) {
	            int revenue = ordersDAO.getCompletedRevenueByMonthAndYear(month, year);
	            monthlyRevenue.add(revenue);
	        }
	        revenueData.put(String.valueOf(year), monthlyRevenue);
	    }

	    // Lấy dữ liệu số lượng sản phẩm bán theo hãng cho từng năm
	    Map<String, Map<Integer, Integer>> productCountByBrand = ordersDAO.getProductCountByBrandForYears(years);

	    // Lấy dữ liệu số lượng sản phẩm bán theo danh mục cho từng năm
	    Map<String, Map<Integer, Integer>> productCountByCategory = ordersDAO.getProductCountByCategoryForYears(years);

	    // Tạo nhãn tháng
	    List<String> labels = Arrays.asList("Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7",
	            "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12");

	    // Chuyển đổi dữ liệu sang JSON
	    String labelsJson = objectMapper.writeValueAsString(labels);
	    String revenueJson = objectMapper.writeValueAsString(revenueData);
	    String productCountByBrandJson = objectMapper.writeValueAsString(productCountByBrand);
	    String productCountByCategoryJson = objectMapper.writeValueAsString(productCountByCategory);

	    // Truyền dữ liệu JSON sang JSP
	    model.addAttribute("labels", labelsJson);
	    model.addAttribute("revenueData", revenueJson);
	    model.addAttribute("years", years);
	    model.addAttribute("currentYear", currentYear);
	    model.addAttribute("productCountByBrand", productCountByBrandJson);
	    model.addAttribute("productCountByCategory", productCountByCategoryJson); // Thêm dữ liệu theo danh mục

	    return "admin/revenues/index";
	}

	@RequestMapping(value = { "/orders" }, method = RequestMethod.GET)
	public String ordersIndex(ModelMap model) {
		// Lấy danh sách categories từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();

		// Lấy danh sách orders
		List<OrdersDTO> orders = ordersDAO.getAllOrdersWithUserFullname();

		// Sắp xếp danh sách orders theo orderId giảm dần
		Collections.sort(orders, new Comparator<OrdersDTO>() {
			public int compare(OrdersDTO o1, OrdersDTO o2) {
				return Integer.compare(o2.getOrderId(), o1.getOrderId()); // Sắp xếp giảm dần
			}
		});

		// Đưa danh sách vào Model để đẩy sang view
		model.addAttribute("categories", categories);
		model.addAttribute("orders", orders);
		return "/admin/orders/index";
	}

	@RequestMapping(value = "/orders/detail-order/{id}", method = RequestMethod.GET)
	public String orderDetailIndex(@PathVariable("id") int id, ModelMap model) {
		// Lấy danh sách categories từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
		OrdersEntity order = ordersDAO.getOrderById(id);
		List<OrderDetailDTO> detail = orderDetailDAO.getOrderDetailById(id);
		List<String> statuses = Arrays.asList("Đã hủy", "Chờ lấy hàng", "Đang giao hàng", "Hoàn thành");

		// Đưa danh sách vào Model để đẩy sang view
		model.addAttribute("categories", categories);
		model.addAttribute("detail", detail);
		model.addAttribute("statuses", statuses);
		model.addAttribute("order", order);
		model.addAttribute("orderId", id);
		return "/admin/orders/detail";
	}

	@RequestMapping(value = "/orders/update-status/{id}", method = RequestMethod.POST)
	public String updateOrderState(@PathVariable("id") int id, @RequestParam("state") String state, ModelMap model) {
		// Gọi DAO để cập nhật trạng thái
		boolean updateComplete = ordersDAO.updateStateById(id, state);

		NotificationEntity notification = new NotificationEntity();
		OrdersEntity order = ordersDAO.getOrderById(id);
		notification.setOrder(order);
		notification.setUserNoti(order.getUserInfo());
		notification.setState(0);
		String content = "";
		switch (state) {
		case "Chờ lấy hàng":
			content = "Đơn hàng có mã " + id
					+ " đã đặt thành công. Vui lòng kiểm tra lại thông tin trong phần chi tiết đơn hàng và email (nếu có) từ LapFarm.";
			break;
		case "Đang giao hàng":
			content = "Đơn hàng " + id
					+ " đã được LapFarm giao cho đơn vị vận chuyển và dự kiến được giao trong 3-5 ngày tới!";
			break;
		case "Hoàn thành":
			content = "Đơn hàng " + id
					+ " đã được giao thành công đến bạn. Vui lòng kiểm tra và liên hệ với LapFarm nếu có vấn đề về sản phẩm!";
			break;
		case "Đã hủy":
			content = "Đơn hàng " + id + " đã bị hủy. Chúc bạn mua sắm vui vẻ!";
			break;
		default:
			content = "LapFarm đang rất nhớ bạn <3";
		}
		notification.setContent(content);
		notification.setTime(new Timestamp(System.currentTimeMillis()));
		notificationDAO.addNotification(notification);

		if (updateComplete) {
			// Lấy danh sách categories từ DAO
			List<CategoryEntity> categories = categoryDAO.getAllCategories();
			List<OrderDetailDTO> detail = orderDetailDAO.getOrderDetailById(id);
			List<String> statuses = Arrays.asList("Đã hủy", "Chờ lấy hàng", "Đang giao hàng", "Hoàn thành");

			// Đưa danh sách vào Model để đẩy sang view
			model.addAttribute("categories", categories);
			model.addAttribute("detail", detail);
			model.addAttribute("statuses", statuses);

			// Redirect đến trang chi tiết đơn hàng
			return "redirect:/admin/orders/detail-order/" + id;
		}

		// Nếu cập nhật thất bại, chuyển đến trang lỗi 500
		return "redirect:/error/500";
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

	@RequestMapping(value = { "/product/add-product" }, method = RequestMethod.GET)
	public String formAddProduct(ModelMap model) {
		// Lấy danh sách từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
		List<BrandEntity> brands = brandDAO.getAllBrands();
		// Đưa danh sách vào Model để đẩy sang view
		model.addAttribute("categories", categories);
		model.addAttribute("brands", brands);

		// Trả về view cho trang quản lý sản phẩm của danh mục
		return "/admin/products/addProduct";
	}

	@RequestMapping(value = "/product/add-product", method = RequestMethod.POST)
	public String addProduct(@RequestParam("nameProduct") String nameProduct,
			@RequestParam("categoryName") String categoryName, @RequestParam("brand") String brandName,
			@RequestParam("description") String description, @RequestParam("moreinfo") String moreInfo,
			@RequestParam("quantity") int quantity, @RequestParam("discountPercent") double discountPercent,
			@RequestParam("purchasePrice") double purchasePrice, @RequestParam("salePrice") double salePrice,
			@RequestParam("promotion") String promotion, @RequestParam("state") String state,
			@RequestParam("productImages") MultipartFile[] productImages, ModelMap model) {

		// Lấy danh sách từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
		List<BrandEntity> brands = brandDAO.getAllBrands();
		// Đưa danh sách vào Model để đẩy sang view
		model.addAttribute("categories", categories);
		model.addAttribute("brands", brands);
		try {
			// Kiểm tra các trường số
			if (quantity < 0 || discountPercent < 0 || purchasePrice < 0 || salePrice < 0) {
				model.addAttribute("message", "Giá trị số không được nhỏ hơn 0.");
				return "/admin/products/addProduct";
			}
			// Kiểm tra sản phẩm đã tồn tại chưa
			boolean existingProduct = productDAO.checkProductByName(nameProduct);
			if (existingProduct) {
				// Nếu sản phẩm đã tồn tại, trả về thông báo lỗi
				model.addAttribute("message", "Sản phẩm đã tồn tại!");
				return "/admin/products/addProduct"; // Chuyển hướng về trang danh sách sản phẩm
			}

			// Lấy category và brand từ database
			CategoryEntity category = categoryDAO.getCategoryByName(categoryName);
			BrandEntity brand = brandDAO.getBrandByName(brandName);

			// Tạo đối tượng sản phẩm mới
			ProductEntity newProduct = new ProductEntity();
			newProduct.setNameProduct(nameProduct);
			newProduct.setCategory(category);
			newProduct.setBrand(brand);
			newProduct.setDescription(description);
			newProduct.setQuantity(quantity);
			newProduct.setDiscount(discountPercent);
			newProduct.setOriginalPrice(purchasePrice);
			newProduct.setSalePrice(salePrice);
			newProduct.setRelatedPromotions(promotion);
			newProduct.setState(state);

			// Xử lý ảnh
			if (productImages != null && productImages.length > 0) {
				List<ImageEntity> imageEntities = new ArrayList<>(); // Danh sách để lưu các đối tượng ImageEntity
				for (MultipartFile file : productImages) {
					if (!file.isEmpty()) {
						// Lưu ảnh và lấy đường dẫn ảnh
						String filePath = saveImage(file); // Hàm lưu ảnh (cần phải tự triển khai phương thức này để lưu
															// ảnh vào thư mục server hoặc cloud)

						// Tạo đối tượng ImageEntity mới
						ImageEntity imageEntity = new ImageEntity();
						imageEntity.setImageUrl(filePath); // Đặt đường dẫn ảnh vào đối tượng ImageEntity
						imageEntity.setProduct(newProduct); // Liên kết ảnh với sản phẩm mới

						imageEntities.add(imageEntity); // Thêm ImageEntity vào danh sách
					}
				}

				// Liên kết danh sách ImageEntity vào sản phẩm
				newProduct.setImages(imageEntities);
			}

			ProductDetailEntity newProductDetail = new ProductDetailEntity(newProduct, category, moreInfo);

			// Gọi service để thêm sản phẩm
			productDAO.addProduct(newProduct);
			productDetailDAO.addProductDetail(newProductDetail);

			// Thêm thông báo thành công
			model.addAttribute("message", "Sản phẩm đã được thêm thành công!");

		} catch (Exception e) {
			e.printStackTrace();
			// Thêm thông báo lỗi
			model.addAttribute("message", "Đã xảy ra lỗi khi thêm sản phẩm!");
		}

		// Chuyển hướng về trang danh sách sản phẩm
		return "/admin/products/addProduct";
	}

	@RequestMapping(value = "/product/edit-product/{id}", method = RequestMethod.GET)
	public String showFormEditProduct(@PathVariable("id") int id, ModelMap model) {
		// Lấy danh sách từ DAO
		List<CategoryEntity> categories = categoryDAO.getAllCategories();
		List<BrandEntity> brands = brandDAO.getAllBrands();
		ProductEntity product = productDAO.getProductById(id);
		ProductDetailEntity productDetail = productDetailDAO.getProductDetailById(id);
		String brandProduct = product.getBrand().getNameBrand();
		String categoryProduct = product.getCategory().getNameCategory();
		List<ImageEntity> images = imageDAO.getImagesByProductId(id);
		// ProductDetailEntity productDetail = productDetailDAO.g
		// Đưa danh sách vào Model để đẩy sang view
		model.addAttribute("categories", categories);
		model.addAttribute("brands", brands);
		model.addAttribute("product", product);
		model.addAttribute("productDetail", productDetail);
		model.addAttribute("brandProduct", brandProduct);
		model.addAttribute("categoryProduct", categoryProduct);
		model.addAttribute("imagesProduct", images);
		return "/admin/products/editProduct";
	}

	@RequestMapping(value = "/product/edit-product/{id}", method = RequestMethod.POST)
	public String editProduct(@PathVariable("id") int id, @RequestParam("nameProduct") String nameProduct,
			@RequestParam("categoryName") int categoryId, @RequestParam("brand") int brandId,
			@RequestParam("description") String description, @RequestParam("moreinfo") String moreInfo,
			@RequestParam("quantity") int quantity, @RequestParam("discountPercent") double discountPercent,
			@RequestParam("purchasePrice") double originalPrice, @RequestParam("salePrice") double salePrice,
			@RequestParam(value = "promotion", required = false) String promotion, @RequestParam("state") String state,
			@RequestParam(value = "deletedImages", required = false) String deletedImages,
			@RequestParam(value = "productImages", required = false) MultipartFile[] productImages,
			RedirectAttributes redirectAttributes) {

		try {
			// Lấy sản phẩm từ database
			ProductEntity product = productDAO.getProductById(id);
			if (product == null) {
				redirectAttributes.addFlashAttribute("message", "Sản phẩm không tồn tại.");
				return "redirect:/admin/products?category=1";
			}
			BrandEntity brand = brandDAO.getBrandById(brandId);
			CategoryEntity category = categoryDAO.getCategoryById(categoryId);
			ProductDetailEntity productDetail = productDetailDAO.getProductDetailById(id);

			// Cập nhật thông tin cơ bản của sản phẩm
			product.setNameProduct(nameProduct);
			product.setBrand(brand);
			product.setCategory(category);
			product.setBrand(brand);
			product.setDescription(description);
			product.setQuantity(quantity);
			product.setDiscount(discountPercent);
			product.setOriginalPrice(originalPrice);
			product.setSalePrice(salePrice);
			product.setRelatedPromotions(promotion);
			product.setState(state);

			productDetail.setMoreInfo(moreInfo);
			// Thêm các ảnh mới
			if (productImages != null && productImages.length > 0) {
				List<ImageEntity> imageEntities = new ArrayList<>();

				// Thêm các ảnh từ productImages
				for (MultipartFile file : productImages) {
					if (!file.isEmpty()) {
						String imageUrl = saveImage(file); // Lưu ảnh và trả về URL
						ImageEntity newImage = new ImageEntity(imageUrl, product);
						imageEntities.add(newImage);
					}
				}

				// Thêm các ảnh sẵn có trong product mà không bị xóa
				if (product.getImages() != null) {
					// Kiểm tra nếu deletedImages không null, nếu null thì gán giá trị mặc định
					List<Integer> deletedImageIds = (deletedImages != null && !deletedImages.isEmpty()) ? Arrays
							.stream(deletedImages.split(",")).map(Integer::parseInt).collect(Collectors.toList())
							: new ArrayList<>();

					for (ImageEntity existingImage : product.getImages()) {
						if (!deletedImageIds.contains(existingImage.getIdImage())) {
							imageEntities.add(existingImage);
						}
					}
				}

				// Lưu lại danh sách ảnh vào product
				product.setImages(imageEntities);
			}

			// Lưu thay đổi sản phẩm vào database
			boolean updateCompleted = productDAO.updateProduct(product);
			productDetailDAO.updateProductDetail(productDetail);
			if (updateCompleted) {
				redirectAttributes.addFlashAttribute("message", "Cập nhật sản phẩm thành công.");
			} else {
				redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi cập nhật sản phẩm.");
			}
			// Xóa các ảnh bị xóa
			if (deletedImages != null && !deletedImages.isEmpty()) {
				String[] imageIds = deletedImages.split(",");
				for (String imageId : imageIds) {
					imageDAO.deleteImageById(Integer.parseInt(imageId));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi cập nhật sản phẩm.");
		}

		return "redirect:/admin/product/edit-product/{id}";
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

	@RequestMapping(value = "/categories/edit-category/{idCategory}", method = RequestMethod.GET)
	public String showEditCategoryForm(@PathVariable("idCategory") int idCategory, ModelMap model) {
		// Lấy thông tin loại hàng từ database
		CategoryEntity category = categoryDAO.getCategoryById(idCategory);
		if (category == null) {
			model.addAttribute("message", "Loại hàng không tồn tại!");
			return "/admin/categories/editCategory";
		}
		model.addAttribute("category", category);
		return "/admin/categories/editCategory"; // Tên file JSP để hiển thị form
	}

	@RequestMapping(value = "/categories/edit-category/{idCategory}", method = RequestMethod.POST)
	public String updateCategory(@PathVariable("idCategory") int idCategory,
			@RequestParam("categoryName") String categoryName, ModelMap model) {

		// Chuẩn hóa tên loại hàng
		String normalizedCategoryName = normalizeString(categoryName);

		// Kiểm tra nếu loại hàng không tồn tại
		CategoryEntity category = categoryDAO.getCategoryById(idCategory);
		if (category == null) {
			model.addAttribute("message", "Loại hàng không tồn tại!");
			return "/admin/categories/editCategory";
		}

		// Kiểm tra tên loại hàng đã tồn tại chưa
		boolean nameExists = categoryDAO.checkCategoryByName(normalizedCategoryName);
		if (nameExists) {
			model.addAttribute("category", category);
			model.addAttribute("message", "Tên loại hàng đã tồn tại!");
			return "/admin/categories/editCategory";
		}

		// Cập nhật tên loại hàng
		category.setNameCategory(normalizedCategoryName);
		boolean isUpdated = categoryDAO.updateCategory(category);

		if (isUpdated) {
			model.addAttribute("category", category);
			model.addAttribute("message", "Cập nhật loại hàng thành công!");
		} else {
			model.addAttribute("category", category);
			model.addAttribute("message", "Cập nhật loại hàng thất bại!");
		}

		return "/admin/categories/editCategory";
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

	@RequestMapping(value = "/brands/edit-brand/{idBrand}", method = RequestMethod.GET)
	public String showEditBrandForm(@PathVariable("idBrand") int idBrand, ModelMap model) {
		// Lấy thông tin loại hàng từ database
		BrandEntity brand = brandDAO.getBrandById(idBrand);
		if (brand == null) {
			model.addAttribute("message", "Hãng không tồn tại!");
			return "/admin/brands/editBrand";
		}
		model.addAttribute("brand", brand);
		return "/admin/brands/editBrand"; // Tên file JSP để hiển thị form
	}

	@RequestMapping(value = "/brands/edit-brand/{idBrand}", method = RequestMethod.POST)
	public String updateBrand(@PathVariable("idBrand") int idBrand, @RequestParam("brandName") String brandName,
			ModelMap model) {

		// Chuẩn hóa tên loại hàng
		String normalizedCategoryName = normalizeStringEachWord(brandName);

		// Kiểm tra nếu loại hàng không tồn tại
		BrandEntity brand = brandDAO.getBrandById(idBrand);
		if (brand == null) {
			model.addAttribute("message", "Hãng không tồn tại!");
			return "/admin/brands/editBrand";
		}

		// Kiểm tra tên loại hàng đã tồn tại chưa
		boolean nameExists = brandDAO.checkBrandByName(normalizedCategoryName);
		if (nameExists) {
			model.addAttribute("brand", brand);
			model.addAttribute("message", "Tên hãng đã tồn tại!");
			return "/admin/brands/editBrand";
		}

		// Cập nhật tên loại hàng
		brand.setNameBrand(normalizedCategoryName);
		boolean isUpdated = brandDAO.updateBrand(brand);

		if (isUpdated) {
			model.addAttribute("brand", brand);
			model.addAttribute("message", "Cập nhật hãng thành công!");
		} else {
			model.addAttribute("brand", brand);
			model.addAttribute("message", "Cập nhật hãng thất bại!");
		}

		return "/admin/brands/editBrand";
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

	@RequestMapping(value = "/lock-user/{userId}", method = RequestMethod.POST)
	public ResponseEntity<String> lockUser(@PathVariable("userId") int userId) {
		try {
			boolean result = userDAO.updateUserState(userId, "Đã khóa");
			if (result) {
				return ResponseEntity.ok("Tài khoản đã bị khóa thành công.");
			} else {
				return ResponseEntity.badRequest().body("Không thể khóa tài khoản.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Có lỗi xảy ra khi khóa tài khoản.");
		}
	}

	@RequestMapping(value = "/unlock-user/{userId}", method = RequestMethod.POST)
	public ResponseEntity<String> unlockUser(@PathVariable("userId") int userId) {
		try {
			boolean result = userDAO.updateUserState(userId, "Hoạt động");
			if (result) {
				return ResponseEntity.ok("Tài khoản đã được mở khóa thành công.");
			} else {
				return ResponseEntity.badRequest().body("Không thể mở khóa tài khoản.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Có lỗi xảy ra khi mở khóa tài khoản.");
		}
	}
	
	@RequestMapping(value = "/manage-user/history/{id}", method = RequestMethod.GET)
	public String userHistory(@PathVariable("id") int id, ModelMap model) {
		// Lấy danh sách categories từ DAO
				List<CategoryEntity> categories = categoryDAO.getAllCategories();

				// Lấy danh sách orders
				List<OrdersDTO> orders = ordersDAO.getOrdersWithUserFullnameByUserId(id);

				// Sắp xếp danh sách orders theo orderId giảm dần
				Collections.sort(orders, new Comparator<OrdersDTO>() {
					public int compare(OrdersDTO o1, OrdersDTO o2) {
						return Integer.compare(o2.getOrderId(), o1.getOrderId()); // Sắp xếp giảm dần
					}
				});

				// Đưa danh sách vào Model để đẩy sang view
				model.addAttribute("categories", categories);
				model.addAttribute("orders", orders);
				return "/admin/user/userOrders";
	}
}
