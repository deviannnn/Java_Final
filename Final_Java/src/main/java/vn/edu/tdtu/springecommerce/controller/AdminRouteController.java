package vn.edu.tdtu.springecommerce.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.tdtu.springecommerce.service.OrdersService;

@Controller
@RequestMapping("/admin")
public class AdminRouteController {
    @Autowired
    OrdersService ordersService;

    @GetMapping
    public String indexAdminPage(HttpSession session) {
        if (session.getAttribute("permission") == null) {
            return "redirect:/";
        }
        int permission = (int) session.getAttribute("permission");
        if (permission < 2) {
            return "redirect:/";
        }
        return "admin";
    }

    @GetMapping("/category")
    public String manageCategoryPage(HttpSession session) {
        if (session.getAttribute("permission") == null) {
            return "redirect:/";
        }
        int permission = (int) session.getAttribute("permission");
        if (permission < 2) {
            return "redirect:/";
        }
        return "admin-category";
    }

    @GetMapping("/feature")
    public String manageFeaturePage(HttpSession session) {
        if (session.getAttribute("permission") == null) {
            return "redirect:/";
        }
        int permission = (int) session.getAttribute("permission");
        if (permission < 2) {
            return "redirect:/";
        }
        return "admin-feature";
    }

    @GetMapping("/product")
    public String manageProductPage(HttpSession session) {
        if (session.getAttribute("permission") == null) {
            return "redirect:/";
        }
        int permission = (int) session.getAttribute("permission");
        if (permission < 2) {
            return "redirect:/";
        }
        return "admin-product";
    }

    @GetMapping("/account")
    public String manageAccountPage(HttpSession session) {
        if (session.getAttribute("permission") == null) {
            return "redirect:/";
        }
        int permission = (int) session.getAttribute("permission");
        if (permission < 2) {
            return "redirect:/";
        }
        return "admin-account";
    }

    @GetMapping("/orders")
    public String manageOrdersPage(Model model, HttpSession session) {
        if (session.getAttribute("permission") == null) {
            return "redirect:/";
        }
        int permission = (int) session.getAttribute("permission");
        if (permission < 2) {
            return "redirect:/";
        }
        return "admin-orders";
    }

}
