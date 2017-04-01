package ua.org.javatraining.andrii_tkachenko.controller;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.service.CategoryService;
import ua.org.javatraining.andrii_tkachenko.service.ProductService;

import java.util.*;

/**
 * Created by tkaczenko on 20.03.17.
 */
@Controller
public class FrontStoreController {
    private final CategoryService categoryService;
    private final ProductService productService;

    private List<Category> categories;

    @Autowired
    public FrontStoreController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
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

        Map<Category,Long> categoryCountMap = new HashMap<>();
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
