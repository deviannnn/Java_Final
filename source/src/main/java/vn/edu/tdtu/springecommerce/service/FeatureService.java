package vn.edu.tdtu.springecommerce.service;

import vn.edu.tdtu.springecommerce.model.Feature;

import java.util.List;

public interface FeatureService {
    List<Feature> findAll();
    void save(Feature item);
    void delete(int id);
    Feature findById(int id);
}
