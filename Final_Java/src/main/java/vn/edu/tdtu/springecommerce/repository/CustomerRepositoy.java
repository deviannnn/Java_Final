package vn.edu.tdtu.springecommerce.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.tdtu.springecommerce.model.Customer;

@Repository
@Transactional
public interface CustomerRepositoy extends JpaRepository<Customer, Integer> {
    Customer findById(int id);
    Customer findByAccount_Id(int accountId);
}
