package vn.edu.tdtu.springecommerce.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.tdtu.springecommerce.model.*;
import vn.edu.tdtu.springecommerce.service.CartService;

import java.util.List;

@Controller
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @DeleteMapping("/delete-cartDetail/{cartDetailId}")
    public ResponseEntity<String> deleteCartDetail(@PathVariable("cartDetailId") int cartDetailId, HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        if (isLogged == null || !isLogged) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry. You need to log in before accessing the shopping cart feature.");
        }
        Customer currentCustomer = (Customer) session.getAttribute("customer");
        if (currentCustomer == null || currentCustomer.getAccount().getPermission() != 1) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry. You cannot access the shopping cart feature.");
        }
        cartService.deleteFromCart(currentCustomer, cartDetailId, session);
        return ResponseEntity.status(HttpStatus.OK).body("Products have been deleted successfully");
    }

    @PostMapping("/update-cartDetail")
    @ResponseBody
    public ResponseEntity<String> updateCartDetail(@RequestBody List<CartDetail> changedCartDetails, HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        if (isLogged == null || !isLogged) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry. You need to log in before accessing the shopping cart feature.");
        }
        Customer currentCustomer = (Customer) session.getAttribute("customer");
        if (currentCustomer == null || currentCustomer.getAccount().getPermission() != 1) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry. You cannot access the shopping cart feature.");
        }

        if (changedCartDetails != null && !changedCartDetails.isEmpty()) {
            cartService.updateCart(currentCustomer, changedCartDetails, session);
            return ResponseEntity.status(HttpStatus.OK).body("Products have been updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("No products have been updated yet");
        }
    }


    @GetMapping("add/{product-id}/{quantity}")
    public String addToCart(@PathVariable("product-id") int productId, @PathVariable("quantity") int quantity,
                            RedirectAttributes model, HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        if (isLogged == null || !isLogged) {
            model.addFlashAttribute("loginWarning", "Sorry. You need to log in before accessing the shopping cart feature.");
            return "redirect:/login";
        }
        Customer currentCustomer = (Customer) session.getAttribute("customer");
        if (currentCustomer == null || currentCustomer.getAccount().getPermission() != 1) {
            model.addFlashAttribute("addCartWarning", "Sorry. You cannot access the shopping cart feature.");
            return "redirect:/";
        }
        cartService.addToCart(currentCustomer, productId, quantity, session);
        model.addFlashAttribute("addCartSuccess", "The product has been added to cart.");
        return (quantity > 1) ? "redirect:/cart" : "redirect:/";
    }
}
