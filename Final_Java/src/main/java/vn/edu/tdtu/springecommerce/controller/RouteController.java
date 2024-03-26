package vn.edu.tdtu.springecommerce.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.tdtu.springecommerce.model.*;
import vn.edu.tdtu.springecommerce.service.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class RouteController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDetailService cartDetailService;
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/")
    public String shopIndexPage(HttpSession session) {
        Customer currentCustomer = (Customer) session.getAttribute("customer");
        if (session.getAttribute("totalQuantity") == null || currentCustomer == null) {
            session.setAttribute("totalQuantity", 0);
        }
        return "shop";
    }

    @GetMapping("/product-detail/{id}")
    public String detailProductPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product";
    }

    @GetMapping("/order-history")
    public String purchaseHistoryPage(Model model, HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        if (isLogged == null || !isLogged) {
            return "redirect:/";
        }
        Customer currentCustomer = (Customer) session.getAttribute("customer");
        if (currentCustomer == null || currentCustomer.getAccount().getPermission() > 1) {
            return "redirect:/";
        }
        List<Orders> listOrdersPlaced = ordersService.findOrderPlacedOfCustomer(currentCustomer);
        model.addAttribute("listOrders", listOrdersPlaced);
        return "order-history";
    }


    @GetMapping("/cart")
    public String cartPage(Model model, RedirectAttributes redirectModel, HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        if (isLogged == null || !isLogged) {
            redirectModel.addFlashAttribute("loginWarning", "Sorry. You need to log in before accessing the shopping cart feature.");
            return "redirect:/login";
        }
        Customer currentCustomer = (Customer) session.getAttribute("customer");
        if (currentCustomer == null || currentCustomer.getAccount().getPermission() != 1) {
            redirectModel.addFlashAttribute("addCartWarning", "Sorry. You cannot access the shopping cart feature.");
            return "redirect:/";
        }
        Cart currentCart = cartService.findByCustomer_IdAndStatus(currentCustomer.getId(), 0).get(0);

        List<CartDetail> listCartDetail = cartDetailService.findByCart_Id(currentCart.getId());
        model.addAttribute("listCartDetail", listCartDetail);
        model.addAttribute("totalAmount", currentCart.getTotalAmount());
        return "cart";
    }


    @GetMapping("/checkout")
    public String checkoutPage(Model model, RedirectAttributes redirectModel, HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        if (isLogged == null || !isLogged) {
            redirectModel.addFlashAttribute("loginWarning", "Sorry. You need to log in before accessing the shopping cart feature.");
            return "redirect:/login";
        }
        Customer currentCustomer = (Customer) session.getAttribute("customer");
        if (currentCustomer == null || currentCustomer.getAccount().getPermission() > 1) {
            redirectModel.addFlashAttribute("addCartWarning", "Sorry. You cannot access the shopping cart feature.");
            return "redirect:/";
        }
        Cart currentCart = cartService.findByCustomer_IdAndStatus(currentCustomer.getId(), 0).get(0);
        if (currentCart.getTotalAmount() == 0) {
            redirectModel.addFlashAttribute("addCartWarning", "Sorry. There's nothing to place order.");
            return "redirect:/";
        }
        List<CartDetail> listCartDetail = cartDetailService.findByCart_Id(currentCart.getId());
        model.addAttribute("listCartDetail", listCartDetail);
        model.addAttribute("totalAmount", currentCart.getTotalAmount());
        return "checkout";
    }

    @GetMapping("/thankyou")
    public String thankPage(HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        Customer currentCustomer = (Customer) session.getAttribute("customer");
        if (isLogged == null || !isLogged || currentCustomer == null || currentCustomer.getAccount().getPermission() != 1) {
            return "redirect:/";
        }
        return "thankyou";
    }

    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        if (isLogged == null || !isLogged) {
            return "redirect:/";
        }
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        if (isLogged == null || !isLogged) {
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        if (isLogged == null || !isLogged) {
            return "register";
        }
        return "redirect:/";
    }
}
