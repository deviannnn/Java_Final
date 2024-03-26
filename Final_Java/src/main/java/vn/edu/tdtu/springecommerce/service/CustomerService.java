package vn.edu.tdtu.springecommerce.service;

import vn.edu.tdtu.springecommerce.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    void save(Customer item);
    void delete(int id);
    Customer findById(int id);
    Customer findByAccount_Id(int accountId);
}
