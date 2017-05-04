package ua.org.javatraining.andrii_tkachenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.javatraining.andrii_tkachenko.data.dao.ProductDAO;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;
import ua.org.javatraining.andrii_tkachenko.service.AdminProductService;
import ua.org.javatraining.andrii_tkachenko.service.CategoryService;
import ua.org.javatraining.andrii_tkachenko.util.URLUtil;
import ua.org.javatraining.andrii_tkachenko.view.ProductForm;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tkaczenko on 18.04.17.
 */
@Controller
@RequestMapping("/edit")
public class AdminController {
    private final AdminProductService productService;
    private final CategoryService categoryService;

    private Set<Category> categories;

    @Autowired
    public AdminController(AdminProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/product/{productSku}")
    public String product(@PathVariable("productSku") String sku,
                          Model model) {
        Product product = productService.getFullProduct(sku);
        Set<Category> cats = categoryService.findAll();
        List<Category> productCats = product.getCategories().parallelStream()
                .map(CategoryAssociation::getCategory)
                .collect(Collectors.toList());

        ProductForm productForm = new ProductForm();
        productForm.setSku(product.getSku());
        productForm.setName(product.getName());
        productForm.setAmount(product.getAmount());
        productForm.setPrice(product.getPrice());
        productForm.setDescription(product.getDescription());

        model.addAttribute("product", product)
                .addAttribute("productForm", productForm)
                .addAttribute("cats", cats)
                .addAttribute("productCats", productCats);
        return "edit_product";
    }

    @PostMapping("/product/{productSku}")
    public String updateProduct(@PathVariable("productSku") String sku,
                                @ModelAttribute("productForm") @Valid ProductForm productForm,
                                BindingResult binding, RedirectAttributes attributes)
            throws UnsupportedEncodingException {
        if (binding.hasErrors()) {
            attributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productForm", binding
            );
            return "redirect:/edit/product/" + URLUtil.encode(sku);
        }

        Product product = new Product();
        product.setSku(productForm.getSku());
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setAmount(productForm.getAmount());
        product.setDescription(productForm.getDescription());
        product.setCategories(new HashSet<>(productForm.getCategories().parallelStream()
                .map(category -> new CategoryAssociation(product, category))
                .collect(Collectors.toList())));

        productService.save(product);
        return "redirect:/edit/product/" + URLUtil.encode(sku);
    }

    @ModelAttribute("categories")
    public Set<Category> loadCategories() {
        return categories = categoryService.findAllByParent(null);
    }
}
