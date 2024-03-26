package vn.edu.tdtu.springecommerce.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.tdtu.springecommerce.model.Feature;

@Repository
@Transactional
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}
