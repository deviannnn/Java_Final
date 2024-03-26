package vn.edu.tdtu.springecommerce.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.tdtu.springecommerce.model.Orders;

import java.util.Optional;

@Repository
@Transactional
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findById(int ordersId);
    Orders findByCart_Id(int cartId);
}
