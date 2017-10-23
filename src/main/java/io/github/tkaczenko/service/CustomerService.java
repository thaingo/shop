package io.github.tkaczenko.service;

import io.github.tkaczenko.model.Customer;
import io.github.tkaczenko.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tkaczenko on 13.03.17.
 */
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer entity) {
        return customerRepository.save(entity);
    }

    public Customer findById(String id) {
        return customerRepository.findOne(id);
    }

    public void delete(String id) {
        customerRepository.delete(id);
    }
}

