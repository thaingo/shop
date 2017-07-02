package io.github.tkaczenko.service;

import io.github.tkaczenko.data.model.CustomOrder;
import io.github.tkaczenko.data.model.OrderItem;
import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            BigDecimal sum = e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue()));
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
