package LapFarm.Controller;

import LapFarm.Model.CartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@SessionAttributes("cartItems")
public class CartController {

    @ModelAttribute("cartItems")
    public List<CartItem> initializeCart() {
        return new ArrayList<>();
    }

    @GetMapping("/ViewCart")
    public String showCart(@ModelAttribute("cartItems") List<CartItem> cartItems, Model model) {
        double totalAmount = cartItems.stream().mapToDouble(CartItem::getTotal).sum();
        model.addAttribute("totalAmount", totalAmount);
        return "ViewCart";
    }

    @PostMapping("/cart/add")
    public String addToCart(
            @ModelAttribute("cartItems") List<CartItem> cartItems,
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam int quantity) {

        Optional<CartItem> existingItem = cartItems.stream()
                .filter(item -> item.getName().equals(name))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setTotal(item.getPrice() * item.getQuantity());
        } else {
            CartItem newItem = new CartItem(name, price, quantity);
            cartItems.add(newItem);
        }

        return "redirect:/ViewCart";
    }
}
