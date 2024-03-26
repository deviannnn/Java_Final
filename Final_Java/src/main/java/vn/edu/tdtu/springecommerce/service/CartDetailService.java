package vn.edu.tdtu.springecommerce.service;

import vn.edu.tdtu.springecommerce.model.CartDetail;

import java.util.List;

public interface CartDetailService {
    void save(CartDetail item);
    void delete(int id);
    List<CartDetail> findByCart_Id(int cartId);
}
