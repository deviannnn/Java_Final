package vn.edu.tdtu.springecommerce.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.tdtu.springecommerce.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(int productId);
    List<Product> findByNameContaining(String keyword); //keyword contained in name

    //relevent
    List<Product> findByCategory_Id(int categoryId); //find by category id
    List<Product> findByFeature_Id(int featureId); //find by feature id
    List<Product> findByCategory_IdAndFeature_Id(int categoryId, int featureId); //find by category id & feature id


    //order only
    List<Product> findAllByOrderByPriceAsc(); //order price ^
    List<Product> findAllByOrderByPriceDesc(); //order price v
    List<Product> findAllByOrderByNameAsc(); //order name ^
    List<Product> findAllByOrderByNameDesc(); //order name v


    //order with category id
    List<Product> findByCategory_IdOrderByPriceAsc(int categoryId); //find by category id && order price ^
    List<Product> findByCategory_IdOrderByPriceDesc(int categoryId); //find by category id && order price v
    List<Product> findByCategory_IdOrderByNameAsc(int categoryId); //find by category id && order name ^
    List<Product> findByCategory_IdOrderByNameDesc(int categoryId); //find by category id && order name v


    //order with feature id
    List<Product> findByFeature_IdOrderByPriceAsc(int featureId); //find by feature id && order price ^
    List<Product> findByFeature_IdOrderByPriceDesc(int featureId); //find by feature id && order price v
    List<Product> findByFeature_IdOrderByNameAsc(int featureId); //find by feature id && order name ^
    List<Product> findByFeature_IdOrderByNameDesc(int featureId); //find by feature id && order name v


    //order with category id & feature id
    List<Product> findByCategory_IdAndFeature_IdOrderByPriceAsc(int categoryId, int featureId); //find by category id && feature id && order name ^
    List<Product> findByCategory_IdAndFeature_IdOrderByPriceDesc(int categoryId, int featureId); //find by category id && feature id && order name v
    List<Product> findByCategory_IdAndFeature_IdOrderByNameAsc(int categoryId, int featureId); //find by category id && feature id && order name ^
    List<Product> findByCategory_IdAndFeature_IdOrderByNameDesc(int categoryId, int featureId); //find by category id && feature id && order name v


    //min price & max price
    List<Product> findByPriceBetween(int minPrice, int maxPrice); //min max of price
    List<Product> findByCategory_IdAndPriceBetween(int categoryId, int minPrice, int maxPrice); //find by category id && min max of price
    List<Product> findByFeature_IdAndPriceBetween(int featureId, int minPrice, int maxPrice); //find by feature id && min max of price
    List<Product> findByCategory_IdAndFeature_IdAndPriceBetween(int categoryId, int featureId, int minPrice, int maxPrice); //find by feature id && min max of price


    //min price & max price & order
    List<Product> findByPriceBetweenOrderByPriceAsc(int minPrice, int maxPrice); //min price & max price & order price ^
    List<Product> findByPriceBetweenOrderByPriceDesc(int minPrice, int maxPrice); //min price & max price & order price v
    List<Product> findByPriceBetweenOrderByNameAsc(int minPrice, int maxPrice); //min price & max price & order name ^
    List<Product> findByPriceBetweenOrderByNameDesc(int minPrice, int maxPrice); //min price & max price & order name v


    //min price & max price & order with category id
    List<Product> findByCategory_IdAndPriceBetweenOrderByPriceAsc(int categoryId, int minPrice, int maxPrice); //min price & max price & category id && order price ^
    List<Product> findByCategory_IdAndPriceBetweenOrderByPriceDesc(int categoryId, int minPrice, int maxPrice); //min price & max price & category id && order price v
    List<Product> findByCategory_IdAndPriceBetweenOrderByNameAsc(int categoryId, int minPrice, int maxPrice); //min price & max price & category id && order name ^
    List<Product> findByCategory_IdAndPriceBetweenOrderByNameDesc(int categoryId, int minPrice, int maxPrice); //min price & max price & category id && order name v


    //min price & max price & order with feature id
    List<Product> findByFeature_IdAndPriceBetweenOrderByPriceAsc(int featureId, int minPrice, int maxPrice); //min price & max price & feature id && order price ^
    List<Product> findByFeature_IdAndPriceBetweenOrderByPriceDesc(int featureId, int minPrice, int maxPrice); //min price & max price & feature id && order price v
    List<Product> findByFeature_IdAndPriceBetweenOrderByNameAsc(int featureId, int minPrice, int maxPrice); //min price & max price & feature id && order name ^
    List<Product> findByFeature_IdAndPriceBetweenOrderByNameDesc(int featureId, int minPrice, int maxPrice); //min price & max price & feature id && order name v


    //min price & max price & order with category id & feature id
    List<Product> findByCategory_IdAndFeature_IdAndPriceBetweenOrderByPriceAsc(int categoryId, int featureId, int minPrice, int maxPrice); //min price & max price & category id && feature id && order name ^
    List<Product> findByCategory_IdAndFeature_IdAndPriceBetweenOrderByPriceDesc(int categoryId, int featureId, int minPrice, int maxPrice); //min price & max price & category id && feature id && order name v
    List<Product> findByCategory_IdAndFeature_IdAndPriceBetweenOrderByNameAsc(int categoryId, int featureId, int minPrice, int maxPrice); //min price & max price & category id && feature id && order name ^
    List<Product> findByCategory_IdAndFeature_IdAndPriceBetweenOrderByNameDesc(int categoryId, int featureId, int minPrice, int maxPrice); //fmin price & max price & category id && feature id && order name v
}
