package vn.edu.tdtu.springecommerce.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.tdtu.springecommerce.model.*;
import vn.edu.tdtu.springecommerce.repository.CartDetailRepository;
import vn.edu.tdtu.springecommerce.repository.CartRepository;
import vn.edu.tdtu.springecommerce.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImlp implements CartService{
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartDetailRepository cartDetailRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public void addToCart(Customer currentCustomer, int productId, int quantity, HttpSession session) {
        Cart currentCart = cartRepository.findByCustomer_IdAndStatus(currentCustomer.getId(), 0).get(0);

        Product productPicked = productRepository.findById(productId).orElse(null);

        CartDetail cartDetail = cartDetailRepository.findByCart_IdAndProduct_Id(currentCart.getId(), productPicked.getId());
        // check if product existed in cart
        if (cartDetail != null) {
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
            cartDetail.setAmount(cartDetail.getPrice() * cartDetail.getQuantity());
            cartDetailRepository.save(cartDetail);
        } else {
            CartDetail newCartDetail = new CartDetail();
            newCartDetail.setQuantity(quantity);
            newCartDetail.setPrice(productPicked.getPrice());
            newCartDetail.setAmount(productPicked.getPrice() * quantity);
            newCartDetail.setCart(currentCart);
            newCartDetail.setProduct(productPicked);
            cartDetailRepository.save(newCartDetail);
        }

        int totalAmount = cartDetailRepository.sumAmountByCart_Id(currentCart.getId()); // set new total amount in current cart
        currentCart.setTotalAmount(totalAmount);
        cartRepository.save(currentCart);

        int totalQuantity = cartDetailRepository.sumQuantityByCart_Id(currentCart.getId()); // set new total quantity in current cart
        session.setAttribute("totalQuantity", totalQuantity);
    }

    @Override
    public void updateCart(Customer currentCustomer, List<CartDetail> changedCartDetails, HttpSession session) {
        Cart currentCart = cartRepository.findByCustomer_IdAndStatus(currentCustomer.getId(), 0).get(0);

        for (CartDetail cartDetail : changedCartDetails) {
            if (cartDetail.getQuantity() == 0) {
                cartDetailRepository.deleteById(cartDetail.getId());
            }
            else
            {
                cartDetailRepository.save(cartDetail);
            }
        }

        int totalAmount = cartDetailRepository.sumAmountByCart_Id(currentCart.getId()); // set new total amount in current cart
        currentCart.setTotalAmount(totalAmount);
        cartRepository.save(currentCart);

        int totalQuantity = cartDetailRepository.sumQuantityByCart_Id(currentCart.getId()); // set new total quantity in current cart
        session.setAttribute("totalQuantity", totalQuantity);
    }


    @Override
    public void deleteFromCart(Customer currentCustomer, int cartDetailId, HttpSession session) {
        Cart currentCart = cartRepository.findByCustomer_IdAndStatus(currentCustomer.getId(), 0).get(0);
        cartDetailRepository.deleteById(cartDetailId);

        int totalAmount = cartDetailRepository.sumAmountByCart_Id(currentCart.getId()); // set new total amount in current cart
        currentCart.setTotalAmount(totalAmount);
        cartRepository.save(currentCart);

        int totalQuantity = cartDetailRepository.sumQuantityByCart_Id(currentCart.getId()); // set new total quantity in current cart
        session.setAttribute("totalQuantity", totalQuantity);
    }

    @Override
    public Cart findById(int id) {
        return cartRepository.findById(id);
    }

    @Override
    public List<Cart> findByCustomer_IdAndStatus(int customerId, int status) {
        return cartRepository.findByCustomer_IdAndStatus(customerId, status);
    }
}
