package io.github.tkaczenko.service;

import io.github.tkaczenko.data.model.CustomOrder;
import io.github.tkaczenko.data.model.Customer;
import io.github.tkaczenko.data.repository.CustomOrderRepository;
import io.github.tkaczenko.data.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tkaczenko on 13.03.17.
 */
@Service
public class OrderService {
    private final CustomOrderRepository customOrderRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(CustomOrderRepository customOrderRepository, CustomerRepository customerRepository) {
        this.customOrderRepository = customOrderRepository;
        this.customerRepository = customerRepository;
    }

    public CustomOrder save(String customerId, String address) {
        Customer customer = customerRepository.findOne(customerId);
        CustomOrder order = new CustomOrder();
        order.setAddress(address);
        order.setCustomer(customer);
        return customOrderRepository.save(order);
    }

    public CustomOrder save(Customer customer, int status, String address) {
        CustomOrder order = new CustomOrder();
        order.setCustomer(customer);
        order.setStatus(status);
        order.setAddress(address);
        return customOrderRepository.save(order);
    }

    public CustomOrder save(CustomOrder entity) {
        return customOrderRepository.save(entity);
    }

    public void delete(Integer id) {
        customOrderRepository.delete(id);
    }

    public CustomOrder findById(Integer id) {
        return customOrderRepository.findOne(id);
    }
}
