package vn.edu.tdtu.springecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.tdtu.springecommerce.model.Product;
import vn.edu.tdtu.springecommerce.repository.ProductRepository;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Override
    public Product add(Product item, MultipartFile imageFile) {
        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        item.setImage(fileName);
        try {
            Product addedProduct = productRepository.save(item);
            String uploadDir = "src/main/resources/static/images";
            saveFile(uploadDir, fileName, imageFile);
            return addedProduct;
        } catch (Exception e) {
            throw new RuntimeException("Error saving product image: " + e.getMessage());
        }
    }


    @Override
    public void update(int id, Product item, MultipartFile imageFile) {
        Product updatedProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
        updateProductInfo(updatedProduct, item);

        if(imageFile != null && !imageFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            updatedProduct.setImage(fileName);
            try {
                productRepository.save(updatedProduct);
                String uploadDir = "src/main/resources/static/images";
                saveFile(uploadDir, fileName, imageFile);
            } catch (Exception e) {
                throw new RuntimeException("Error saving product image: " + e.getMessage());
            }
        }
    }

    @Override
    public void update(int id, Product product) {
        Product updatedProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
        updateProductInfo(updatedProduct, product);
        productRepository.save(updatedProduct);
    }

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
    }

    private void updateProductInfo(Product updatedProduct, Product newProduct) {
        if (newProduct.getName() != null) updatedProduct.setName(newProduct.getName());
        if (newProduct.getQuantity() != 0) updatedProduct.setQuantity(newProduct.getQuantity());
        if (newProduct.getPrice() != 0) updatedProduct.setPrice(newProduct.getPrice());
        if (newProduct.getUnit() != null) updatedProduct.setUnit(newProduct.getUnit());
        if (newProduct.getDescription() != null) updatedProduct.setDescription(newProduct.getDescription());
        if (newProduct.getCategory() != null) updatedProduct.setCategory(newProduct.getCategory());
        if (newProduct.getFeature() != null) updatedProduct.setFeature(newProduct.getFeature());
    }

    @Override
    public Product delete(int id) {
        Product deletedProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
        productRepository.deleteById(id);
        return deletedProduct;
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }

    @Override
    public List<Product> findByNameContaining(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }


    //relevent
    @Override
    public List<Product> findByCategory_Id(int categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }

    @Override
    public List<Product> findByFeature_Id(int featureId) {
        return productRepository.findByFeature_Id(featureId);
    }

    @Override
    public List<Product> findByCategory_IdAndFeature_Id(int categoryId, int featureId) {
        return productRepository.findByCategory_IdAndFeature_Id(categoryId, featureId);
    }


    //order only
    @Override
    public List<Product> findAllByOrderByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }
    @Override
    public List<Product> findAllByOrderByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }
    @Override
    public List<Product> findAllByOrderByNameAsc() {
        return productRepository.findAllByOrderByNameAsc();
    }
    @Override
    public List<Product> findAllByOrderByNameDesc() {
        return productRepository.findAllByOrderByNameDesc();
    }


    //order with category id
    @Override
    public List<Product> findByCategory_IdOrderByPriceAsc(int categoryId) {
        return productRepository.findByCategory_IdOrderByPriceAsc(categoryId);
    }
    @Override
    public List<Product> findByCategory_IdOrderByPriceDesc(int categoryId) {
        return productRepository.findByCategory_IdOrderByPriceDesc(categoryId);
    }
    @Override
    public List<Product> findByCategory_IdOrderByNameAsc(int categoryId) {
        return productRepository.findByCategory_IdOrderByNameAsc(categoryId);
    }
    @Override
    public List<Product> findByCategory_IdOrderByNameDesc(int categoryId) {
        return productRepository.findByCategory_IdOrderByNameDesc(categoryId);
    }


    //order with feature id
    @Override
    public List<Product> findByFeature_IdOrderByPriceAsc(int featureId) {
        return productRepository.findByFeature_IdOrderByPriceAsc(featureId);
    }
    @Override
    public List<Product> findByFeature_IdOrderByPriceDesc(int featureId) {
        return productRepository.findByFeature_IdOrderByPriceDesc(featureId);
    }
    @Override
    public List<Product> findByFeature_IdOrderByNameAsc(int featureId) {
        return productRepository.findByFeature_IdOrderByNameAsc(featureId);
    }
    @Override
    public List<Product> findByFeature_IdOrderByNameDesc(int featureId) {
        return productRepository.findByFeature_IdOrderByNameDesc(featureId);
    }


    //order with category id & feature id
    @Override
    public List<Product> findByCategory_IdAndFeature_IdOrderByPriceAsc(int categoryId, int featureId) {
        return productRepository.findByCategory_IdAndFeature_IdOrderByPriceAsc(categoryId, featureId);
    }
    @Override
    public List<Product> findByCategory_IdAndFeature_IdOrderByPriceDesc(int categoryId, int featureId) {
        return productRepository.findByCategory_IdAndFeature_IdOrderByPriceDesc(categoryId, featureId);
    }
    @Override
    public List<Product> findByCategory_IdAndFeature_IdOrderByNameAsc(int categoryId, int featureId) {
        return productRepository.findByCategory_IdAndFeature_IdOrderByNameAsc(categoryId, featureId);
    }
    @Override
    public List<Product> findByCategory_IdAndFeature_IdOrderByNameDesc(int categoryId, int featureId) {
        return productRepository.findByCategory_IdAndFeature_IdOrderByNameDesc(categoryId, featureId);
    }


    //min price & max price
    @Override
    public List<Product> findByPriceBetween(int minPrice, int maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    @Override
    public List<Product> findByCategory_IdAndPriceBetween(int categoryId, int minPrice, int maxPrice) {
        return productRepository.findByCategory_IdAndPriceBetween(categoryId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByFeature_IdAndPriceBetween(int featureId, int minPrice, int maxPrice) {
        return productRepository.findByFeature_IdAndPriceBetween(featureId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByCategory_IdAndFeature_IdAndPriceBetween(int categoryId, int featureId, int minPrice, int maxPrice) {
        return productRepository.findByCategory_IdAndFeature_IdAndPriceBetween(categoryId, featureId, minPrice, maxPrice);
    }


    //min price & max price & order
    @Override
    public List<Product> findByPriceBetweenOrderByPriceAsc(int minPrice, int maxPrice) {
        return productRepository.findByPriceBetweenOrderByPriceAsc(minPrice, maxPrice);
    }
    @Override
    public List<Product> findByPriceBetweenOrderByPriceDesc(int minPrice, int maxPrice) {
        return productRepository.findByPriceBetweenOrderByPriceDesc(minPrice, maxPrice);
    }
    @Override
    public List<Product> findByPriceBetweenOrderByNameAsc(int minPrice, int maxPrice) {
        return productRepository.findByPriceBetweenOrderByNameAsc(minPrice, maxPrice);
    }
    @Override
    public List<Product> findByPriceBetweenOrderByNameDesc(int minPrice, int maxPrice) {
        return productRepository.findByPriceBetweenOrderByNameDesc(minPrice, maxPrice);
    }


    //min price & max price & order with category id
    @Override
    public List<Product> findByCategory_IdAndPriceBetweenOrderByPriceAsc(int categoryId, int minPrice, int maxPrice) {
        return productRepository.findByCategory_IdAndPriceBetweenOrderByPriceAsc(categoryId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByCategory_IdAndPriceBetweenOrderByPriceDesc(int categoryId, int minPrice, int maxPrice) {
        return productRepository.findByCategory_IdAndPriceBetweenOrderByPriceDesc(categoryId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByCategory_IdAndPriceBetweenOrderByNameAsc(int categoryId, int minPrice, int maxPrice) {
        return productRepository.findByCategory_IdAndPriceBetweenOrderByNameAsc(categoryId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByCategory_IdAndPriceBetweenOrderByNameDesc(int categoryId, int minPrice, int maxPrice) {
        return productRepository.findByCategory_IdAndPriceBetweenOrderByNameDesc(categoryId, minPrice, maxPrice);
    }


    //min price & max price & order with feature id
    @Override
    public List<Product> findByFeature_IdAndPriceBetweenOrderByPriceAsc(int featureId, int minPrice, int maxPrice) {
        return productRepository.findByFeature_IdAndPriceBetweenOrderByPriceAsc(featureId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByFeature_IdAndPriceBetweenOrderByPriceDesc(int featureId, int minPrice, int maxPrice) {
        return productRepository.findByFeature_IdAndPriceBetweenOrderByPriceDesc(featureId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByFeature_IdAndPriceBetweenOrderByNameAsc(int featureId, int minPrice, int maxPrice) {
        return productRepository.findByFeature_IdAndPriceBetweenOrderByNameAsc(featureId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByFeature_IdAndPriceBetweenOrderByNameDesc(int featureId, int minPrice, int maxPrice) {
        return productRepository.findByFeature_IdAndPriceBetweenOrderByNameDesc(featureId, minPrice, maxPrice);
    }


    //min price & max price & order with category id & feature id
    @Override
    public List<Product> findByCategory_IdAndFeature_IdAndPriceBetweenOrderByPriceAsc(int categoryId, int featureId, int minPrice, int maxPrice) {
        return productRepository.findByCategory_IdAndFeature_IdAndPriceBetweenOrderByPriceAsc(categoryId, featureId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByCategory_IdAndFeature_IdAndPriceBetweenOrderByPriceDesc(int categoryId, int featureId, int minPrice, int maxPrice) {
        return productRepository.findByCategory_IdAndFeature_IdAndPriceBetweenOrderByPriceDesc(categoryId, featureId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByCategory_IdAndFeature_IdAndPriceBetweenOrderByNameAsc(int categoryId, int featureId, int minPrice, int maxPrice) {
        return productRepository.findByCategory_IdAndFeature_IdAndPriceBetweenOrderByNameAsc(categoryId, featureId, minPrice, maxPrice);
    }
    @Override
    public List<Product> findByCategory_IdAndFeature_IdAndPriceBetweenOrderByNameDesc(int categoryId, int featureId, int minPrice, int maxPrice) {
        return productRepository.findByCategory_IdAndFeature_IdAndPriceBetweenOrderByNameDesc(categoryId, featureId, minPrice, maxPrice);
    }
}
