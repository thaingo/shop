package ua.org.javatraining.andrii_tkachenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.javatraining.andrii_tkachenko.data.model.CustomOrder;
import ua.org.javatraining.andrii_tkachenko.data.model.OrderItem;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.repository.OrderItemRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tkaczenko on 13.03.17.
 */
@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem save(OrderItem entity) {
        return orderItemRepository.save(entity);
    }

    public OrderItem findById(Integer id) {
        return orderItemRepository.findOne(id);
    }

    public void delete(Integer id) {
        orderItemRepository.delete(id);
    }

    public Set<OrderItem> save(CustomOrder order, Map<Product, Integer> items) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(e.getKey());
            orderItem.setAmount(e.getValue());
            int sum = e.getKey().getPrice() * e.getValue();
            orderItem.setSubtotal(sum);
            orderItems.add(orderItemRepository.save(orderItem));
        }
        return orderItems;
    }

    public OrderItem save(CustomOrder order, Product product, int amount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(1);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        return orderItemRepository.save(orderItem);
    }
}
