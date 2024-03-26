package vn.edu.tdtu.springecommerce.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.tdtu.springecommerce.model.CartDetail;

import java.util.List;

@Repository
@Transactional
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    List<CartDetail> findByCart_Id(int id);
    @Query("SELECT SUM(cd.quantity) FROM CartDetail cd WHERE cd.cart.id = :cartId")
    int sumQuantityByCart_Id(@Param("cartId") int cartId);

    @Query("SELECT SUM(cd.amount) FROM CartDetail cd WHERE cd.cart.id = :cartId")
    int sumAmountByCart_Id(@Param("cartId") int cartId);

    CartDetail findByCart_IdAndProduct_Id(int cartId, int productId);
}
