package vn.edu.tdtu.springecommerce.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.tdtu.springecommerce.model.Account;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findById(int id);
    Account findByUsername(String username);
    Account findByUsernameAndPassword(String username, String password);

}
