package vn.edu.tdtu.springecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.tdtu.springecommerce.model.Customer;
import vn.edu.tdtu.springecommerce.repository.CustomerRepositoy;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImlp implements CustomerService{
    @Autowired
    CustomerRepositoy customerRepositoy;

    @Override
    public List<Customer> findAll() {
        return customerRepositoy.findAll();
    }

    @Override
    public void save(Customer item) {
        customerRepositoy.save(item);
    }

    @Override
    public void delete(int id) {
        customerRepositoy.deleteById(id);
    }

    @Override
    public Customer findById(int id) {
        return customerRepositoy.findById(id);
    }

    @Override
    public Customer findByAccount_Id(int accountId) {
        return customerRepositoy.findByAccount_Id(accountId);
    }
}
