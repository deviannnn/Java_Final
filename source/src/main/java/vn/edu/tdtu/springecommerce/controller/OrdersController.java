package vn.edu.tdtu.springecommerce.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.tdtu.springecommerce.model.*;
import vn.edu.tdtu.springecommerce.service.OrdersService;

import java.util.List;

@Controller
@RequestMapping("/api/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Orders> listAllOrdersPlaced() {
        return ordersService.findAllOrderPlaced();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Orders> getProductById(@PathVariable("id") int id) {
        try {
            Orders order = ordersService.findById(id);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addOrders(@RequestBody Orders order, HttpSession session) {
        Boolean isLogged = (Boolean) session.getAttribute("isLogged");
        if (isLogged == null || !isLogged) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry. You need to log in before accessing the shopping cart feature.");
        }
        Customer currentCustomer = (Customer) session.getAttribute("customer");
        if (currentCustomer == null || currentCustomer.getAccount().getPermission() != 1) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry. You cannot access the shopping cart feature.");
        }
        ordersService.addOrder(order, currentCustomer);
        session.setAttribute("totalQuantity", 0);
        return ResponseEntity.status(HttpStatus.OK).body("Order placed successfully.");
    }


    @PutMapping(path = "/update/{orderId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Orders> edit(@PathVariable("orderId") int orderId, @ModelAttribute("orders") Orders item) {
        try {
            ordersService.updateOrder(orderId, item);
            Orders updatedOrders = ordersService.findById(orderId);
            return ResponseEntity.ok(updatedOrders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }
    }


    @PostMapping(path = "/cancel/{orderId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Orders> cancel(@PathVariable("orderId") int orderId) {
        try {
            ordersService.updateStatus(orderId, 0);
            Orders updatedOrders = ordersService.findById(orderId);
            return ResponseEntity.ok(updatedOrders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }
    }

    @PostMapping(path = "/confirm/{orderId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Orders> confirm(@PathVariable("orderId") int orderId) {
        try {
            ordersService.updateStatus(orderId, 2);
            Orders updatedOrders = ordersService.findById(orderId);
            return ResponseEntity.ok(updatedOrders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }
    }
}
