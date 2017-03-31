package ua.org.javatraining.andrii_tkachenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.session.Cart;
import ua.org.javatraining.andrii_tkachenko.service.CategoryService;
import ua.org.javatraining.andrii_tkachenko.service.ProductService;

import java.util.List;

/**
 * Created by tkaczenko on 20.03.17.
 */
@RestController
public class FrontStoreController {
    private final Cart cart;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public FrontStoreController(Cart cart, CategoryService categoryService, ProductService productService) {
        this.cart = cart;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> category(@PathVariable("id") int categoryId) {
        return productService.findAllByCategoryId(categoryId);
    }
}
