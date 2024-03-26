package vn.edu.tdtu.springecommerce.service;

import jakarta.servlet.http.HttpSession;
import vn.edu.tdtu.springecommerce.model.Cart;
import vn.edu.tdtu.springecommerce.model.CartDetail;
import vn.edu.tdtu.springecommerce.model.Customer;

import java.util.List;

public interface CartService {
    void addToCart(Customer currentCustomer, int productId, int quantity, HttpSession session);
    void updateCart(Customer currentCustomer, List<CartDetail> changedCartDetails, HttpSession session);
    void deleteFromCart(Customer currentCustomer, int cartDetailId, HttpSession session);
    Cart findById(int id);
    List<Cart> findByCustomer_IdAndStatus(int customerId, int status);
}
