package vn.edu.tdtu.springecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.tdtu.springecommerce.model.Cart;
import vn.edu.tdtu.springecommerce.model.CartDetail;
import vn.edu.tdtu.springecommerce.model.Product;
import vn.edu.tdtu.springecommerce.repository.CartDetailRepository;

import java.util.List;

@Service
public class CartDetailServiceImlp implements CartDetailService{
    @Autowired
    CartDetailRepository cartDetailRepository;

    @Override
    public void save(CartDetail item) {
        cartDetailRepository.save(item);
    }

    @Override
    public void delete(int id) {
        cartDetailRepository.deleteById(id);
    }

    @Override
    public List<CartDetail> findByCart_Id(int cartId) {
        return cartDetailRepository.findByCart_Id(cartId);
    }
}
