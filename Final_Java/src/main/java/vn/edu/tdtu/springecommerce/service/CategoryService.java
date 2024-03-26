package vn.edu.tdtu.springecommerce.service;

import vn.edu.tdtu.springecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    void save(Category item);
    void delete(int id);
    Category findById(int id);
}
