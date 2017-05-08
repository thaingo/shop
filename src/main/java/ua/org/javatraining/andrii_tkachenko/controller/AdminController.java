package ua.org.javatraining.andrii_tkachenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.Attribute;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.AttributeAssociation;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;
import ua.org.javatraining.andrii_tkachenko.service.AdminProductService;
import ua.org.javatraining.andrii_tkachenko.service.AttributeService;
import ua.org.javatraining.andrii_tkachenko.service.CategoryService;
import ua.org.javatraining.andrii_tkachenko.util.URLUtil;
import ua.org.javatraining.andrii_tkachenko.view.ProductForm;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tkaczenko on 18.04.17.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminProductService productService;
    private final CategoryService categoryService;
    private final AttributeService attributeService;

    private Set<Category> categories;

    @Autowired
    public AdminController(AdminProductService productService, CategoryService categoryService,
                           AttributeService attributeService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.attributeService = attributeService;
    }

    @GetMapping("/edit/product/{productSku}")
    public String product(@PathVariable("productSku") String sku,
                          Model model) {
        Product product = productService.getFullProduct(sku);
        Set<Category> cats = categoryService.findAll();
        List<Category> productCats = product.getCategories().parallelStream()
                .map(CategoryAssociation::getCategory)
                .collect(Collectors.toList());

        List<Attribute> attributes = attributeService.findAll();
        List<ProductForm.AttributeValue> productAttributeValues = product.getAttributes().parallelStream()
                .map(attributeAssociation ->
                        new ProductForm.AttributeValue(
                                attributeAssociation.getAttribute().getName(), attributeAssociation.getValue()
                        ))
                .collect(Collectors.toList());

        List<Attribute> attributeList = product.getAttributes().parallelStream()
                .map(AttributeAssociation::getAttribute)
                .collect(Collectors.toList());
        attributes.removeIf(attributeList::contains);

        if (!model.containsAttribute("productForm")) {
            ProductForm productForm = new ProductForm();
            productForm.setSku(product.getSku());
            productForm.setName(product.getName());
            productForm.setAmount(product.getAmount());
            productForm.setPrice(product.getPrice());
            productForm.setDescription(product.getDescription());
            productForm.setAttributeValues(productAttributeValues);
            model.addAttribute("productForm", productForm);
        }
        model.addAttribute("product", product)
                .addAttribute("cats", cats)
                .addAttribute("productCats", productCats)
                .addAttribute("attributes", attributes.parallelStream()
                        .map(Attribute::getName)
                        .collect(Collectors.toList()));
        return "edit_product";
    }

    @PostMapping("/edit/product/{productSku}")
    public String updateProduct(@PathVariable("productSku") String sku,
                                @ModelAttribute("productForm") @Valid ProductForm productForm,
                                BindingResult binding, RedirectAttributes attributes)
            throws UnsupportedEncodingException {
        if (binding.hasErrors()) {
            attributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productForm", binding
            );
            attributes.addFlashAttribute("productForm", productForm);
            return "redirect:/admin/edit/product/" + URLUtil.encode(sku);
        }
        if (productForm.getSize() > 0) {
            for (int i = 0; i < productForm.getSize(); i++) {
                productForm.getAttributeValues().add(productForm.getAttributeValues().get(0));
            }
            productForm.setSize(0);
            attributes.addFlashAttribute("productForm", productForm);
            attributes.addFlashAttribute("productCats", productForm.getCategories());
            return "redirect:/admin/edit/product/" + URLUtil.encode(sku);
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
        product.setAttributes(new HashSet<>(productForm.getAttributeValues().parallelStream()
                .map(attributeValue ->
                        new AttributeAssociation(
                                product, new Attribute(attributeValue.getAttribute()), attributeValue.getValue())
                )
                .collect(Collectors.toList())));

        productService.update(product);
        attributes.addFlashAttribute("mess", "Продукт обновлен");
        return "redirect:/admin/edit/product/" + URLUtil.encode(sku);
    }

    @GetMapping("/add/product")
    public String product(Model model) {
        Set<Category> cats = categoryService.findAll();
        List<Attribute> attributes = attributeService.findAll();
        if (!model.containsAttribute("productForm")) {
            model.addAttribute("productForm", new ProductForm());
        }
        model.addAttribute("cats", cats)
                .addAttribute("attributes", attributes.parallelStream()
                        .map(Attribute::getName)
                        .collect(Collectors.toList())
                );
        return "add_product";
    }

    @PostMapping("/add/product")
    public String addProduct(@ModelAttribute("productForm") @Valid ProductForm productForm,
                             BindingResult binding, RedirectAttributes attributes) {
        if (binding.hasErrors()) {
            attributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productForm", binding
            );
            attributes.addFlashAttribute("productForm", productForm);
            attributes.addFlashAttribute("productCats", productForm.getCategories());
            return "redirect:/admin/add/product/";
        }
        if (productForm.getSize() > 0) {
            for (int i = 0; i < productForm.getSize(); i++) {
                productForm.getAttributeValues().add(new ProductForm.AttributeValue("", ""));
            }
            productForm.setSize(0);
            attributes.addFlashAttribute("productForm", productForm);
            attributes.addFlashAttribute("productCats", productForm.getCategories());
            return "redirect:/admin/add/product/";
        }

        Product product = new Product();
        //// FIXME: 08.05.17 Create sequence to generate product sku
        Random random = new Random();
        product.setSku(generateRandomString(random, 5));
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setAmount(productForm.getAmount());
        product.setDescription(productForm.getDescription());
        product.setCategories(new HashSet<>(productForm.getCategories().parallelStream()
                .map(category -> new CategoryAssociation(product, category))
                .collect(Collectors.toList())));
        product.setAttributes(new HashSet<>(productForm.getAttributeValues().parallelStream()
                .map(attributeValue ->
                        new AttributeAssociation(
                                product, new Attribute(attributeValue.getAttribute()), attributeValue.getValue())
                )
                .collect(Collectors.toList())));

        productService.create(product);
        attributes.addFlashAttribute("mess", "Продукт добавлен");
        return "redirect:/admin/add/product/";
    }

    @ModelAttribute("categories")
    public Set<Category> loadCategories() {
        return categories = categoryService.findAllByParent(null);
    }

    private static String generateRandomString(Random random, int length) {
        return random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
