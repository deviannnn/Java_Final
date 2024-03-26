package vn.edu.tdtu.springecommerce.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.tdtu.springecommerce.model.Category;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
