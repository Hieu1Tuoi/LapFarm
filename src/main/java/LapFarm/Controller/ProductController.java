package LapFarm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import LapFarm.Service.ProductService;
import LapFarm.Entity.ProductEntity;

@Controller
@RequestMapping("/product") // Đặt prefix "/product" để giảm lặp lại trong các mapping
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Hiển thị danh sách sản phẩm
     *
     * @param model Model gửi dữ liệu đến view
     * @return tên view danh sách sản phẩm
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String productList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product"; // Tên file JSP hiển thị danh sách sản phẩm
    }

    /**
     * Hiển thị chi tiết sản phẩm
     *
     * @param id    ID của sản phẩm
     * @param model Model gửi dữ liệu đến view
     * @return tên view hiển thị chi tiết sản phẩm hoặc chuyển hướng nếu không tìm thấy
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String productDetail(@PathVariable("id") Integer id, Model model) {
        // Lấy thông tin sản phẩm từ Service
        ProductEntity product = productService.getProductById(id);

        // Nếu không tìm thấy sản phẩm, chuyển hướng đến trang lỗi hoặc 404
        if (product == null) {
            return "redirect:/LapFarm/error"; // Đảm bảo context path được thêm vào URL
        }

        // Gửi thông tin sản phẩm đến trang chi tiết
        model.addAttribute("product", product);
        return "product-detail"; // Tên file JSP hiển thị chi tiết sản phẩm
    }
}
