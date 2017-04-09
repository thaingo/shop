package ua.org.javatraining.andrii_tkachenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.javatraining.andrii_tkachenko.data.model.CustomOrder;
import ua.org.javatraining.andrii_tkachenko.data.model.Customer;
import ua.org.javatraining.andrii_tkachenko.data.repository.CustomOrderRepository;
import ua.org.javatraining.andrii_tkachenko.data.repository.CustomerRepository;

/**
 * Created by tkaczenko on 13.03.17.
 */
@Service
@Transactional
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

    public CustomOrder save(Customer customer, int status) {
        CustomOrder order = new CustomOrder();
        order.setCustomer(customer);
        order.setStatus(status);
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
