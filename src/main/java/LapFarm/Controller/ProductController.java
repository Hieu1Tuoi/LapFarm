package LapFarm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


import LapFarm.DAO.ProductDAO;
import LapFarm.Entity.ProductEntity;

@Controller
public class ProductController {
	
	@RequestMapping(value = {"/product", "/product/*"}, method = RequestMethod.GET)
	public String product() {

		return "product";
	}
	@Autowired
    private ProductDAO productDAO;

    @RequestMapping(value = "/product/{idProduct}")
    public String productDetail(@PathVariable("idProduct") int idProduct, Model model) {
        // Lấy thông tin sản phẩm từ DAO
        ProductEntity product = productDAO.getProductById(idProduct);

        // Kiểm tra nếu sản phẩm không tồn tại
        if (product == null) {
            model.addAttribute("errorMessage", "Không tìm thấy sản phẩm.");
            return "error"; // Điều hướng tới trang lỗi
        }

        // Truyền thông tin sản phẩm vào model để hiển thị ở JSP
        model.addAttribute("product", product);
        return "product";
    }
}
