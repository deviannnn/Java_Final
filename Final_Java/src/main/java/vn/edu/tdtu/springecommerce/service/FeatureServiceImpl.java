package vn.edu.tdtu.springecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.tdtu.springecommerce.model.Feature;
import vn.edu.tdtu.springecommerce.repository.FeatureRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureServiceImpl implements FeatureService {
    @Autowired
    FeatureRepository featureRepository;

    @Override
    public List<Feature> findAll() {
        return featureRepository.findAll();
    }

    @Override
    public void save(Feature item) {
        featureRepository.save(item);
    }

    @Override
    public void delete(int id) {
        featureRepository.deleteById(id);
    }

    @Override
    public Feature findById(int id) {
        Optional<Feature> optionalFeature= featureRepository.findById(id);
        if (optionalFeature.isPresent()) {
            return optionalFeature.get();
        } else {
            return null;
        }
    }
}
