package vn.edu.tdtu.springecommerce.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.tdtu.springecommerce.model.Cart;

import java.util.List;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findById(int id);
    List<Cart> findByCustomer_IdAndStatus(int customerId, int status);
    List<Cart> findByStatus(int status);
}
