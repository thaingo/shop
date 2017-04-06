package ua.org.javatraining.andrii_tkachenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.org.javatraining.andrii_tkachenko.data.model.Customer;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.session.Cart;
import ua.org.javatraining.andrii_tkachenko.service.CategoryService;
import ua.org.javatraining.andrii_tkachenko.service.ProductService;

import java.util.*;

/**
 * Created by tkaczenko on 20.03.17.
 */
@Controller
public class FrontStoreController {
    private final Cart cart;
    private final CategoryService categoryService;
    private final ProductService productService;

    private List<Category> categories;

    @Autowired
    public FrontStoreController(Cart cart, CategoryService categoryService, ProductService productService) {
        this.cart = cart;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping(value = {"/", "/shop"})
    public ModelAndView home() {
        loadCategories();
        Set<Product> products = null;
        if (categories.size() != 0) {
            products = productService.findAllByCategoryId(
                    categories.get(0).getSubCategories().iterator().next().getId()
            );
        }
        return new ModelAndView("index")
                .addObject("categories", categories)
                .addObject("products", products);
    }

    @GetMapping("/category/{name}")
    public ModelAndView category(@PathVariable String name) {
        loadCategories();

        Category found = findCategoryByName(name);

        if (found == null) {
            return getNotFoundPage();
        }

        Map<Category, Long> categoryCountMap = new HashMap<>();
        List<Map.Entry<Category, Long>> entries = null;
        Set<Product> products = null;
        if (found.getParentCategory() == null) {
            found.getSubCategories().forEach(c ->
                    categoryCountMap.put(c, productService.countByCategory(c)));
            entries = new ArrayList<>(categoryCountMap.entrySet());

            Iterator<Category> iterator = found.getSubCategories().iterator();
            if (iterator.hasNext()) {
                products = productService.findAllByCategoryId(iterator.next().getId());
            }
        } else {
            products = productService.findAllByCategoryName(name);
        }
        return new ModelAndView("category")
                .addObject("category", found)
                .addObject("categories", categories)
                .addObject("step", 6)
                .addObject("entries", entries)
                .addObject("products", products);
    }

    @GetMapping("/category/{categoryName}/product/{productName}")
    public ModelAndView product(@PathVariable String categoryName,
                                @PathVariable String productName) {
        loadCategories();
        Category found = findCategoryByName(categoryName);

        if (found == null) {
            return getNotFoundPage();
        } else {
            Product product = productService.findByName(productName);
            if (product == null) {
                return getNotFoundPage();
            }
            return new ModelAndView("product")
                    .addObject("category", found)
                    .addObject("product", product);
        }
    }

    private ModelAndView getNotFoundPage() {
        //// TODO: 02.04.17 Fix Not Found page
        return new ModelAndView("index")
                .addObject("categories", categories);
    }

    private Category findCategoryByName(String name) {
        Category found = categories.parallelStream()
                .filter(c -> name.equals(c.getName()))
                .findFirst()
                .orElse(null);
        if (found == null) {
            found = categories.parallelStream()
                    .flatMap(c -> c.getSubCategories().stream())
                    .filter(c -> name.equals(c.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return found;
    }

    private void loadCategories() {
        if (categories == null) {
            categories = categoryService.findAllParentCategoryWithSubcategories();
        }
    }
}
