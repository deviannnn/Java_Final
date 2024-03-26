package vn.edu.tdtu.springecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.tdtu.springecommerce.model.Category;
import vn.edu.tdtu.springecommerce.model.Feature;
import vn.edu.tdtu.springecommerce.model.Product;
import vn.edu.tdtu.springecommerce.service.CategoryService;
import vn.edu.tdtu.springecommerce.service.FeatureService;
import vn.edu.tdtu.springecommerce.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FeatureService featureService;

    // FIND ALL PRODUCT
    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Product> listAllProduct() {
        return productService.findAll();
    }


    // FIND ONE PRODUCT BY ID // DONE
    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }
    }


    // ADD ONE PRODUCT
    @PostMapping(path = "/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> addProduct(@ModelAttribute("product") Product product,
                                              @RequestParam(name = "imageFile") MultipartFile imageFile,
                                              @RequestParam("category") int categoryId,
                                              @RequestParam("feature") int featureId) {
        try {
            // Find category and feature by id
            Category category = categoryService.findById(categoryId);
            Feature feature = featureService.findById(featureId);

            // Set category and feature for product
            product.setCategory(category);
            product.setFeature(feature);

            // Add product
            Product addedProduct = productService.add(product, imageFile);

            return ResponseEntity.ok(addedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // UPDATE ONE PRODUCT
    @PutMapping(path = "update/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @ModelAttribute("product") Product product, @RequestParam(name = "imageFile", required = false) MultipartFile imageFile) {
        try {
            if (imageFile == null && imageFile.isEmpty()) {
                productService.update(id, product);
            } else {
                productService.update(id, product, imageFile);
            }
            Product updatedProduct = productService.findById(id);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }
    }


    // DELETE ONE PRODUCT
    @DeleteMapping(path = "delete/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
        try {
            Product deletedProduct = productService.delete(id);
            return ResponseEntity.ok(deletedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().header("message", e.getMessage()).build();
        }
    }


    // ---------------------------- FILTER -----------------------------------

    // FIND ALL PRODUCT HAVE KEYWORD CONTAIN IN NAME // DONE
    @GetMapping(path = "/name/{keyword}", produces = "application/json")
    @ResponseBody
    public List<Product> getProductsByNameContaining(@PathVariable("keyword") String keyword) {
        return productService.findByNameContaining(keyword);
    }

    // FIND ALL PRODUCT: CATEGORY ID // DONE
    @GetMapping(path = "/category/{categoryId}", produces = "application/json")
    @ResponseBody
    public List<Product> categoryId(@PathVariable("categoryId") int categoryId) {
        return productService.findByCategory_Id(categoryId);
    }


    // FIND ALL PRODUCT: FEATURE ID // DONE
    @GetMapping(path = "/feature/{featureId}", produces = "application/json")
    @ResponseBody
    public List<Product> featureId(@PathVariable("featureId") int featureId) {
        return productService.findByFeature_Id(featureId);
    }


    // FIND ALL PRODUCT: CATEGORY ID + FEATURE ID // DONE
    @GetMapping(path = "/category/{categoryId}/feature/{featureId}", produces = "application/json")
    @ResponseBody
    public List<Product> category_feature_Id(@PathVariable("categoryId") int categoryId, @PathVariable("featureId") int featureId) {
        return productService.findByCategory_IdAndFeature_Id(categoryId, featureId);
    }


    // FIND ALL PRODUCT: SORT BY PRICE OR NAME // DONE
    @GetMapping(path = "/sort-by/{option}/{order}", produces = "application/json")
    @ResponseBody
    public List<Product> sort(@PathVariable("option") String option, @PathVariable("order") String order) {
        if (option.equals("price") && order.equals("asc")) {
            return productService.findAllByOrderByPriceAsc();
        }
        if (option.equals("price") && order.equals("desc")) {
            return productService.findAllByOrderByPriceDesc();
        }
        if (option.equals("name") && order.equals("asc")) {
            return productService.findAllByOrderByNameAsc();
        }
        if (option.equals("name") && order.equals("desc")) {
            return productService.findAllByOrderByNameDesc();
        }
        return null;
    }


    // FIND ALL PRODUCT: CATEGORY ID + SORT BY PRICE OR NAME // DONE
    @GetMapping(path = "/category/{categoryId}/sort-by/{option}/{order}", produces = "application/json")
    @ResponseBody
    public List<Product> category_sort(@PathVariable("categoryId") int categoryId, @PathVariable("option") String option, @PathVariable("order") String order) {
        if (option.equals("price") && order.equals("asc")) {
            return productService.findByCategory_IdOrderByPriceAsc(categoryId);
        }
        if (option.equals("price") && order.equals("desc")) {
            return productService.findByCategory_IdOrderByPriceDesc(categoryId);
        }
        if (option.equals("name") && order.equals("asc")) {
            return productService.findByCategory_IdOrderByNameAsc(categoryId);
        }
        if (option.equals("name") && order.equals("desc")) {
            return productService.findByCategory_IdOrderByNameDesc(categoryId);
        }
        return null;
    }


    // FIND ALL PRODUCT: FEATURE ID + SORT BY PRICE OR NAME // DONE
    @GetMapping(path = "/feature/{featureId}/sort-by/{option}/{order}", produces = "application/json")
    @ResponseBody
    public List<Product> feature_sort(@PathVariable("featureId") int featureId, @PathVariable("option") String option, @PathVariable("order") String order) {
        if (option.equals("price") && order.equals("asc")) {
            return productService.findByFeature_IdOrderByPriceAsc(featureId);
        }
        if (option.equals("price") && order.equals("desc")) {
            return productService.findByFeature_IdOrderByPriceDesc(featureId);
        }
        if (option.equals("name") && order.equals("asc")) {
            return productService.findByFeature_IdOrderByNameAsc(featureId);
        }
        if (option.equals("name") && order.equals("desc")) {
            return productService.findByFeature_IdOrderByNameDesc(featureId);
        }
        return null;
    }


    // FIND ALL PRODUCT: CATEGORY ID + FEATURE ID + SORT BY PRICE OR NAME // DONE
    @GetMapping(path = "/category/{categoryId}/feature/{featureId}/sort-by/{option}/{order}", produces = "application/json")
    @ResponseBody
    public List<Product> category_featue_sort(@PathVariable("categoryId") int categoryId, @PathVariable("featureId") int featureId,
                                              @PathVariable("option") String option, @PathVariable("order") String order) {
        if (option.equals("price") && order.equals("asc")) {
            return productService.findByCategory_IdAndFeature_IdOrderByPriceAsc(categoryId, featureId);
        }
        if (option.equals("price") && order.equals("desc")) {
            return productService.findByCategory_IdAndFeature_IdOrderByPriceDesc(categoryId, featureId);
        }
        if (option.equals("name") && order.equals("asc")) {
            return productService.findByCategory_IdAndFeature_IdOrderByNameAsc(categoryId, featureId);
        }
        if (option.equals("name") && order.equals("desc")) {
            return productService.findByCategory_IdAndFeature_IdOrderByNameDesc(categoryId, featureId);
        }
        return null;
    }


    // FIND ALL PRODUCT: BETWEEN MIN_PRICE AND MAX_PRICE
    @GetMapping(path = "/price-between/{minPrice}/{maxPrice}", produces = "application/json")
    @ResponseBody
    public List<Product> range(@PathVariable("minPrice") int minPrice, @PathVariable("maxPrice") int maxPrice) {
        return productService.findByPriceBetween(minPrice, maxPrice);
    }


    // FIND ALL PRODUCT: CATEGORY ID + MIN_PRICE AND MAX_PRICE // DONE
    @GetMapping(path = "/category/{categoryId}/price-between/{minPrice}/{maxPrice}", produces = "application/json")
    @ResponseBody
    public List<Product> category_range(@PathVariable int categoryId, @PathVariable("minPrice") int minPrice, @PathVariable("maxPrice") int maxPrice) {
        return productService.findByCategory_IdAndPriceBetween(categoryId, minPrice, maxPrice);
    }


    // FIND ALL PRODUCT: FEATURE ID + MIN_PRICE AND MAX_PRICE // DONE
    @GetMapping(path = "/feature/{featureId}/price-between/{minPrice}/{maxPrice}", produces = "application/json")
    @ResponseBody
    public List<Product> feature_range(@PathVariable int featureId, @PathVariable("minPrice") int minPrice, @PathVariable("maxPrice") int maxPrice) {
        return productService.findByFeature_IdAndPriceBetween(featureId, minPrice, maxPrice);
    }


    // FIND ALL PRODUCT: CATEGORY ID + FEATURE ID + MIN_PRICE AND MAX_PRICE// DONE
    @GetMapping(path = "/category/{categoryId}/feature/{featureId}/price-between/{minPrice}/{maxPrice}", produces = "application/json")
    @ResponseBody
    public List<Product> category_feature_range(@PathVariable("categoryId") int categoryId, @PathVariable("featureId") int featureId,
                                                @PathVariable("minPrice") int minPrice, @PathVariable("maxPrice") int maxPrice) {
        return productService.findByCategory_IdAndFeature_IdAndPriceBetween(categoryId, featureId, minPrice, maxPrice);
    }


    // FIND ALL PRODUCT: MIN_PRICE AND MAX_PRICE + SORT BY PRICE OR NAME // DONE
    @GetMapping(path = "/sort-by/{option}/{order}/price-between/{minPrice}/{maxPrice}", produces = "application/json")
    @ResponseBody
    public List<Product> range_sort(@PathVariable("option") String option, @PathVariable("order") String order,
                                    @PathVariable("minPrice") int minPrice, @PathVariable("maxPrice") int maxPrice) {
        if (option.equals("price") && order.equals("asc")) {
            return productService.findByPriceBetweenOrderByPriceAsc(minPrice, maxPrice);
        }
        if (option.equals("price") && order.equals("desc")) {
            return productService.findByPriceBetweenOrderByPriceDesc(minPrice, maxPrice);
        }
        if (option.equals("name") && order.equals("asc")) {
            return productService.findByPriceBetweenOrderByNameAsc(minPrice, maxPrice);
        }
        if (option.equals("name") && order.equals("desc")) {
            return productService.findByPriceBetweenOrderByNameDesc(minPrice, maxPrice);
        }
        return null;
    }


    // FIND ALL PRODUCT: CATEGORY ID + MIN_PRICE AND MAX_PRICE + SORT BY PRICE OR NAME // DONE
    @GetMapping(path = "/category/{categoryId}/sort-by/{option}/{order}/price-between/{minPrice}/{maxPrice}", produces = "application/json")
    @ResponseBody
    public List<Product> category_range_sort(@PathVariable("categoryId") int categoryId, @PathVariable("option") String option,
                                             @PathVariable("order") String order, @PathVariable("minPrice") int minPrice,
                                             @PathVariable("maxPrice") int maxPrice) {
        if (option.equals("price") && order.equals("asc")) {
            return productService.findByCategory_IdAndPriceBetweenOrderByPriceAsc(categoryId, minPrice, maxPrice);
        }
        if (option.equals("price") && order.equals("desc")) {
            return productService.findByCategory_IdAndPriceBetweenOrderByPriceDesc(categoryId, minPrice, maxPrice);
        }
        if (option.equals("name") && order.equals("asc")) {
            return productService.findByCategory_IdAndPriceBetweenOrderByNameAsc(categoryId, minPrice, maxPrice);
        }
        if (option.equals("name") && order.equals("desc")) {
            return productService.findByCategory_IdAndPriceBetweenOrderByNameDesc(categoryId, minPrice, maxPrice);
        }
        return null;
    }


    // FIND ALL PRODUCT: FEATURE ID + MIN_PRICE AND MAX_PRICE + SORT BY PRICE OR NAME // DONE
    @GetMapping(path = "/feature/{featureId}/sort-by/{option}/{order}/price-between/{minPrice}/{maxPrice}", produces = "application/json")
    @ResponseBody
    public List<Product> feature_range_sort(@PathVariable("featureId") int featureId, @PathVariable("option") String option,
                                            @PathVariable("order") String order, @PathVariable("minPrice") int minPrice,
                                            @PathVariable("maxPrice") int maxPrice) {
        if (option.equals("price") && order.equals("asc")) {
            return productService.findByFeature_IdAndPriceBetweenOrderByPriceAsc(featureId, minPrice, maxPrice);
        }
        if (option.equals("price") && order.equals("desc")) {
            return productService.findByFeature_IdAndPriceBetweenOrderByPriceDesc(featureId, minPrice, maxPrice);
        }
        if (option.equals("name") && order.equals("asc")) {
            return productService.findByFeature_IdAndPriceBetweenOrderByNameAsc(featureId, minPrice, maxPrice);
        }
        if (option.equals("name") && order.equals("desc")) {
            return productService.findByFeature_IdAndPriceBetweenOrderByNameDesc(featureId, minPrice, maxPrice);
        }
        return null;
    }


    // FIND ALL PRODUCT: CATEGORY ID + FEATURE ID + MIN_PRICE AND MAX_PRICE + SORT BY PRICE OR NAME // DONE
    @GetMapping(path = "/category/{categoryId}/feature/{featureId}/sort-by/{option}/{order}/price-between/{minPrice}/{maxPrice}", produces = "application/json")
    @ResponseBody
    public List<Product> category_feature_range_sort(@PathVariable("categoryId") int categoryId,
                                                     @PathVariable("featureId") int featureId,
                                                     @PathVariable("option") String option,
                                                     @PathVariable("order") String order,
                                                     @PathVariable("minPrice") int minPrice,
                                                     @PathVariable("maxPrice") int maxPrice) {
        if (option.equals("price") && order.equals("asc")) {
            return productService.findByCategory_IdAndFeature_IdAndPriceBetweenOrderByPriceAsc(categoryId, featureId, minPrice, maxPrice);
        }
        if (option.equals("price") && order.equals("desc")) {
            return productService.findByCategory_IdAndFeature_IdAndPriceBetweenOrderByPriceDesc(categoryId, featureId, minPrice, maxPrice);
        }
        if (option.equals("name") && order.equals("asc")) {
            return productService.findByCategory_IdAndFeature_IdAndPriceBetweenOrderByNameAsc(categoryId, featureId, minPrice, maxPrice);
        }
        if (option.equals("name") && order.equals("desc")) {
            return productService.findByCategory_IdAndFeature_IdAndPriceBetweenOrderByNameDesc(categoryId, featureId, minPrice, maxPrice);
        }
        return null;
    }
}
