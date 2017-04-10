package ua.org.javatraining.andrii_tkachenko.data.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by tkaczenko on 05.03.17.
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class Cart {
    private Map<Product, Integer> items = new HashMap<>(0);

    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public void addItem(Product product) {
        if (!items.isEmpty()) {
            Set<Product> keys = items.keySet();
            int i = 0;
            for (Product key : keys) {
                if (Objects.equals(key.getSku(), product.getSku())) {
                    int quntity = items.get(key);
                    quntity++;
                    items.put(key, quntity);
                    break;
                } else {
                    i++;
                    if (i >= keys.size()) {
                        items.put(product, 1);
                    }
                }
            }
        } else {
            items.put(product, 1);
        }
    }

    public void clear() {
        items.clear();
    }

    public void updateItem(String id, Integer qty) {
        Set<Product> keys = items.keySet();
        for (Product key : keys) {
            if (key.getSku().equals(id)) {
                if (qty <= 0) {
                    items.remove(key);
                } else {
                    items.put(key, qty);
                }
                break;
            }
        }
    }

    public double calculateSubTotal() {
        double sum = 0;
        if (items.size() != 0) {
            Set<Map.Entry<Product, Integer>> entries = items.entrySet();
            for (Map.Entry<Product, Integer> entry : entries) {
                sum += entry.getKey().getPrice()
                        * entry.getValue();
            }
        }
        DecimalFormat form = new DecimalFormat("#.##");
        return Double.valueOf(form.format(sum));
    }

    public double calculateTotal(double subTotal) {
        DecimalFormat form = new DecimalFormat("#.##");
        return Double.valueOf(form.format(subTotal + 3));
    }

    public Integer sumQuantity() {
        Integer count = 0;
        Set<Product> keys = items.keySet();
        for (Product key : keys) {
            count += items.get(key);
        }
        return count;
    }
}
