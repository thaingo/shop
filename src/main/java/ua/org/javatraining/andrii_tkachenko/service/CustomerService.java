package ua.org.javatraining.andrii_tkachenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.javatraining.andrii_tkachenko.data.model.Customer;
import ua.org.javatraining.andrii_tkachenko.data.repository.CustomerRepository;

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

