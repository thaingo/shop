package io.github.tkaczenko.controller;

import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.attribute.Attribute;
import io.github.tkaczenko.data.model.attribute.AttributeAssociation;
import io.github.tkaczenko.data.model.category.Category;
import io.github.tkaczenko.data.model.category.CategoryAssociation;
import io.github.tkaczenko.data.session.Cart;
import io.github.tkaczenko.service.AttributeService;
import io.github.tkaczenko.service.CategoryService;
import io.github.tkaczenko.service.ProductService;
import io.github.tkaczenko.util.ShopUtil;
import io.github.tkaczenko.util.UrlUtil;
import io.github.tkaczenko.view.AttributesForm;
import io.github.tkaczenko.view.CategoryForm;
import io.github.tkaczenko.view.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tkaczenko on 18.04.17.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private final AttributeService attributeService;
    private final ProductService productService;

    @Autowired
    public AdminController(CategoryService categoryService, Cart cart, AttributeService attributeService,
                           ProductService productService) {
        super(categoryService, cart);
        this.attributeService = attributeService;
        this.productService = productService;
    }

    @GetMapping("/edit/product/{productSku}")
    public String product(@PathVariable("productSku") String sku,
                          Model model) {
        Product product = productService.findFullById(sku);

        Set<Category> categories = categoryService.findAll();
        List<Category> productCategories = product.getCategories().parallelStream()
                .map(CategoryAssociation::getCategory)
                .collect(Collectors.toList());

        List<Attribute> attributes = attributeService.findAll();
        List<Attribute> productAttributes = product.getAttributes().parallelStream()
                .map(AttributeAssociation::getAttribute)
                .collect(Collectors.toList());
        attributes.removeIf(productAttributes::contains);

        if (!model.containsAttribute("productForm")) {
            List<ProductForm.AttributeValue> productAttributeValues = product.getAttributes().parallelStream()
                    .map(attributeAssociation ->
                            new ProductForm.AttributeValue(
                                    attributeAssociation.getAttribute().getName(), attributeAssociation.getValue()
                            ))
                    .collect(Collectors.toList());
            ProductForm productForm = new ProductForm(product.getSku(), product.getName(), product.getAmount(), product.getPrice());
            productForm.setDescription(product.getDescription());
            productForm.setAttributeValues(productAttributeValues);
            model.addAttribute("productForm", productForm);
        }
        model.addAttribute("product", product)
                .addAttribute("categories", categories)
                .addAttribute("productCategories", productCategories)
                .addAttribute("attributes",
                        attributes.parallelStream()
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
            return "redirect:/admin/edit/product/" + UrlUtil.encode(sku);
        }

        // Update attributes
        if (productForm.getSize() > 0) {
            for (int i = 0; i < productForm.getSize(); i++) {
                productForm.getAttributeValues().add(new ProductForm.AttributeValue());
            }
            productForm.setSize(0);
            attributes.addFlashAttribute("productForm", productForm);
            attributes.addFlashAttribute("productCategories", productForm.getCategories());
            return "redirect:/admin/edit/product/" + UrlUtil.encode(sku);
        }

        Product product = productService.findById(sku);
        product.setSku(productForm.getSku());
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setAmount(productForm.getAmount());
        product.setDescription(productForm.getDescription());
        Set<CategoryAssociation> categoryAssociations = product.getCategories();
        categoryAssociations.clear();
        categoryAssociations.addAll(productForm.getCategories().parallelStream()
                .map(category -> new CategoryAssociation(product, category))
                .collect(Collectors.toList()));
        Set<AttributeAssociation> attributeAssociations = product.getAttributes();
        attributeAssociations.clear();
        attributeAssociations.addAll(productForm.getAttributeValues().parallelStream()
                .map(attributeValue ->
                        new AttributeAssociation(
                                product, new Attribute(attributeValue.getAttribute()), attributeValue.getValue())
                )
                .collect(Collectors.toList()));

        productService.save(product);
        attributes.addFlashAttribute("message", "Продукт обновлен");
        return "redirect:/admin/edit/product/" + UrlUtil.encode(sku);
    }

    @GetMapping("/add/product")
    public String product(Model model) {
        Set<Category> categories = categoryService.findAll();
        List<Attribute> attributes = attributeService.findAll();
        if (!model.containsAttribute("productForm")) {
            model.addAttribute("productForm", new ProductForm());
        }
        model.addAttribute("categories", categories)
                .addAttribute("attributes",
                        attributes.parallelStream()
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
            attributes.addFlashAttribute("productCategories", productForm.getCategories());
            return "redirect:/admin/add/product/";
        }

        // Update attributes
        if (productForm.getSize() > 0) {
            for (int i = 0; i < productForm.getSize(); i++) {
                productForm.getAttributeValues().add(new ProductForm.AttributeValue("", ""));
            }
            productForm.setSize(0);
            attributes.addFlashAttribute("productForm", productForm);
            attributes.addFlashAttribute("productCategories", productForm.getCategories());
            return "redirect:/admin/add/product/";
        }

        Product product = new Product(productForm.getName(), productForm.getPrice(), productForm.getAmount());
        product.setDescription(productForm.getDescription());
        product.setCategories(productForm.getCategories().parallelStream()
                .map(category -> new CategoryAssociation(product, category))
                .collect(Collectors.toSet()));
        product.setAttributes(productForm.getAttributeValues().parallelStream()
                .map(attributeValue ->
                        new AttributeAssociation(
                                product, new Attribute(attributeValue.getAttribute()), attributeValue.getValue())
                )
                .collect(Collectors.toSet()));

        productService.save(product);
        attributes.addFlashAttribute("message", "Продукт добавлен");
        return "redirect:/admin/add/product/";
    }

    @GetMapping("/edit/category/{categoryId}")
    public String category(@PathVariable("categoryId") int categoryId, Model model) {
        Category category = categoryService.findById(categoryId);
        Set<Category> categoryChildCategories = categoryService.findAllByParent(category);

        Set<Category> childCategories = categoryService.findAll().parallelStream()
                .filter(c -> !rootCategories.contains(c))
                .collect(Collectors.toSet());

        CategoryForm categoryForm = new CategoryForm(categoryId, category.getName());
        categoryForm.setDescription(category.getDescription());
        categoryForm.setParentCategory(category.getParentCategory());

        model.addAttribute("category", category)
                .addAttribute("categoryForm", categoryForm)
                .addAttribute("rootCategories", rootCategories)
                .addAttribute("childCategories", childCategories)
                .addAttribute("categoryChildCategories", categoryChildCategories);
        return "edit_category";
    }

    @PostMapping("/edit/category/{categoryId}")
    public String updateCategory(@PathVariable("categoryId") int categoryId,
                                 @ModelAttribute("categoryForm") @Valid CategoryForm categoryForm,
                                 BindingResult binding, RedirectAttributes attributes)
            throws UnsupportedEncodingException {
        if (binding.hasErrors()) {
            attributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.categoryForm", binding
            );
            attributes.addFlashAttribute("categoryForm", categoryForm);
            return "redirect:/admin/edit/category/" + categoryId;
        }

        Category category = categoryService.findById(categoryId);
        category.setName(categoryForm.getName());
        category.setDescription(categoryForm.getDescription());
        category.setParentCategory(categoryForm.getParentCategory());
        Set<Category> subCategories = category.getSubCategories();
        subCategories.clear();
        subCategories.addAll(categoryForm.getSubCategories().parallelStream()
                .peek(c -> c.setParentCategory(category))
                .collect(Collectors.toList()));

        categoryService.save(category);
        attributes.addFlashAttribute("message", "Категория обновлена");
        return "redirect:/admin/edit/category/" + categoryId;
    }

    @GetMapping("/add/category")
    public String category(Model model) {
        Set<Category> childCategories = categoryService.findAll().parallelStream()
                .filter(c -> !rootCategories.contains(c))
                .collect(Collectors.toSet());
        if (!model.containsAttribute("categoryForm")) {
            model.addAttribute("categoryForm", new CategoryForm());
        }
        model.addAttribute("rootCategories", rootCategories)
                .addAttribute("childCategories", childCategories);
        return "add_category";
    }

    @PostMapping("/add/category")
    public String addCategory(@ModelAttribute("categoryForm") @Valid CategoryForm categoryForm,
                              BindingResult binding, RedirectAttributes attributes)
            throws UnsupportedEncodingException {
        if (binding.hasErrors()) {
            attributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.categoryForm", binding
            );
            attributes.addFlashAttribute("categoryForm", categoryForm);
            return "redirect:/admin/add/category";
        }

        // Update categories
        Category category = new Category(categoryForm.getName(), categoryForm.getDescription());
        category.setParentCategory(categoryForm.getParentCategory());
        List<Category> subCategories = new ArrayList<>();
        subCategories.add(category);
        subCategories.addAll(categoryForm.getSubCategories().parallelStream()
                .map(c -> {
                    Category temp = categoryService.findById(c.getId());
                    temp.setParentCategory(category);
                    return temp;
                })
                .collect(Collectors.toList()));

        categoryService.save(subCategories);
        attributes.addFlashAttribute("message", "Категория добавлена");
        return "redirect:/admin/add/category";
    }

    @GetMapping("/edit/attributes")
    public String attributes(Model model) {
        if (!model.containsAttribute("attributesForm")) {
            List<AttributesForm.Attribute> attributes = attributeService.findAll().parallelStream()
                    .map(attribute -> new AttributesForm.Attribute(attribute.getName(), null))
                    .collect(Collectors.toList());

            AttributesForm attributesForm = new AttributesForm(attributes);
            model.addAttribute("attributesForm", attributesForm);
        }
        return "attributes";
    }

    @PostMapping("/edit/attributes")
    public String updateAttributes(@ModelAttribute("attributesForm") @Valid AttributesForm attributesForm,
                                   BindingResult binding, RedirectAttributes attributes)
            throws UnsupportedEncodingException {
        if (binding.hasErrors()) {
            attributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.attributesForm", binding
            );
            attributes.addFlashAttribute("attributesForm", attributesForm);
            return "redirect:/admin/edit/attributes";
        }

        // Update attributes
        if (attributesForm.getNumOfAttributes() > 0) {
            for (int i = 0; i < attributesForm.getNumOfAttributes(); i++) {
                attributesForm.getAttributes().add(new AttributesForm.Attribute());
            }
            attributesForm.setNumOfAttributes(0);
            attributes.addFlashAttribute("attributesForm", attributesForm);
            return "redirect:/admin/edit/attributes";
        }

        List<Attribute> createdAttributes = attributesForm.getAttributes().parallelStream()
                .filter(attribute -> attribute.getOldName() == null)
                .map(attribute -> new Attribute(attribute.getNewName()))
                .collect(Collectors.toList());
        attributesForm.getAttributes().parallelStream()
                .filter(attribute -> attribute.getOldName() != null)
                .forEach(attribute -> attributeService.updateForeign(attribute.getOldName(), attribute.getNewName()));

        attributeService.save(createdAttributes);
        return "redirect:/admin/edit/attributes";
    }
}
