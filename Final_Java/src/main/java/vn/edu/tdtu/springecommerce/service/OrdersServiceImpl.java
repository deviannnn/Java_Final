package vn.edu.tdtu.springecommerce.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.tdtu.springecommerce.model.*;
import vn.edu.tdtu.springecommerce.repository.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartDetailRepository cartDetailRepository;
    @Autowired
    CustomerRepositoy customerRepositoy;
    @Autowired
    ProductRepository productRepository;

    @Override
    public void addOrder(Orders order, Customer currentCustomer) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String formattedDate = formatter.format(date);

        Cart currentCart = cartRepository.findByCustomer_IdAndStatus(currentCustomer.getId(), 0).get(0);
        currentCart.setStatus(1); // update current cart has been done status

        order.setCart(currentCart);
        order.setDateCreated(formattedDate);
        order.setStatus(1); // order is set waiting status
        ordersRepository.save(order); // create new order

        Cart newCart = new Cart(); // create new cart for this customer
        newCart.setStatus(0); // cart is set shopping status
        newCart.setTotalAmount(0);
        newCart.setCustomer(currentCustomer);
        cartRepository.save(newCart);
    }

    @Override
    public void updateOrder(int orderId, Orders order) {
        Orders updatedOrder = ordersRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found"));
        updateOrderInfo(updatedOrder, order);
        ordersRepository.save(updatedOrder);
    }

    @Override
    public Orders findById(int orderId) {
        return ordersRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found"));
    }

    private void updateOrderInfo(Orders updatedOrder, Orders newOrder) {
        if (newOrder.getPhone() != null) updatedOrder.setPhone(newOrder.getPhone());
        if (newOrder.getSpecificAddress() != null) updatedOrder.setSpecificAddress(newOrder.getSpecificAddress());
        if (newOrder.getCity() != null) updatedOrder.setCity(newOrder.getCity());
        if (newOrder.getDistrict() != null) updatedOrder.setDistrict(newOrder.getDistrict());
        if (newOrder.getWard() != null) updatedOrder.setWard(newOrder.getWard());
        if (newOrder.getDateCreated() != null) updatedOrder.setDateCreated(newOrder.getDateCreated());
        if (newOrder.getStatus() != 0) updatedOrder.setStatus(newOrder.getStatus());
        if (newOrder.getCart() != null) updatedOrder.setCart(newOrder.getCart());
    }

    @Override
    public List<Orders> findAllOrderPlaced()
    {
        List<Cart> listCartDone = cartRepository.findByStatus(1); // list all cart have done
        List<Orders> allOrdersPlaced = new ArrayList<>();

        for (Cart cartDone : listCartDone) {
            List<CartDetail> cartDetails = cartDetailRepository.findByCart_Id(cartDone.getId()); // list cart detail in each cart have done
            Orders orderPlaced = ordersRepository.findByCart_Id(cartDone.getId()); // find order with each cart have done
            orderPlaced.setCartDetails(cartDetails);
            allOrdersPlaced.add(orderPlaced);
        }

        return allOrdersPlaced;
    }

    @Override
    public List<Orders> findOrderPlacedOfCustomer(Customer currentCustomer) {
        List<Cart> listCartDone = cartRepository.findByCustomer_IdAndStatus(currentCustomer.getId(), 1); // list cart have done of customer
        List<Orders> listOrdersPlaced = new ArrayList<>();

        for (Cart cartDone : listCartDone) {
            List<CartDetail> cartDetails = cartDetailRepository.findByCart_Id(cartDone.getId()); // list cart detail in each cart have done
            Orders orderPlaced = ordersRepository.findByCart_Id(cartDone.getId()); // find order with each cart have done
            orderPlaced.setCartDetails(cartDetails);
            listOrdersPlaced.add(orderPlaced);
        }

        return listOrdersPlaced;
    }

    @Override
    public void updateStatus(int orderId, int status) {
        Orders orderStatusUpdate = ordersRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found"));
        orderStatusUpdate.setStatus(status);
        ordersRepository.save(orderStatusUpdate);
        if (status == 2) {
            for (CartDetail item : orderStatusUpdate.getCartDetails()) {
                Product productTrigger = item.getProduct();
                productTrigger.setQuantity(productTrigger.getQuantity() - item.getQuantity());
                productRepository.save(productTrigger);
            }
        }
    }
}
