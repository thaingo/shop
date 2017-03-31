package ua.org.javatraining.andrii_tkachenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.service.CategoryService;
import ua.org.javatraining.andrii_tkachenko.service.ProductService;

import java.util.List;

/**
 * Created by tkaczenko on 20.03.17.
 */
@RestController
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("categories", categories);
        if (categories.size() != 0) {
            modelAndView.addObject(
                    "products",
                    productService.findAllByCategoryId(categories.get(0).getSubCategories().iterator().next().getId())
            );
        }
        return modelAndView;
    }

    @GetMapping("/category")
    public ModelAndView category(@RequestParam("id") int categoryId) {
        loadCategories();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("products", productService.findAllByCategoryId(categoryId));
        return modelAndView;
    }

    private void loadCategories() {
        if (categories == null) {
            categories = categoryService.findAll();
        }
    }
}
