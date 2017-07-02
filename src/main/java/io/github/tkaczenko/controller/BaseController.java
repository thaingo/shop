package io.github.tkaczenko.controller;

import io.github.tkaczenko.data.model.category.Category;
import io.github.tkaczenko.data.session.Cart;
import io.github.tkaczenko.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Set;

/**
 * Created by tkaczenko on 02.07.17.
 */
public abstract class BaseController {
    protected final CategoryService categoryService;
    protected final Cart cart;

    protected Set<Category> rootCategories;

    @Autowired
    public BaseController(CategoryService categoryService, Cart cart) {
        this.categoryService = categoryService;
        this.cart = cart;
    }

    @ModelAttribute("rootCategories")
    protected Set<Category> loadCategories() {
        return rootCategories = categoryService.findAllByParent(null);
    }

    @ModelAttribute("cartSize")
    protected int cartSize() {
        return cart.sumQuantity();
    }
}
