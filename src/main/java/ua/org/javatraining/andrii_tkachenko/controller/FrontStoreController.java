package ua.org.javatraining.andrii_tkachenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.javatraining.andrii_tkachenko.data.model.CustomOrder;
import ua.org.javatraining.andrii_tkachenko.data.model.Customer;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.model.enumeration.OrderType;
import ua.org.javatraining.andrii_tkachenko.data.session.Cart;
import ua.org.javatraining.andrii_tkachenko.service.*;
import ua.org.javatraining.andrii_tkachenko.util.URLUtil;
import ua.org.javatraining.andrii_tkachenko.view.CustomerForm;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by tkaczenko on 20.03.17.
 */
@Controller
public class FrontStoreController {
    private final Cart cart;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    private Set<Category> categories;

    @Autowired
    public FrontStoreController(Cart cart, CategoryService categoryService, ProductService productService,
                                CustomerService customerService, OrderService orderService,
                                OrderItemService orderItemService) {
        this.cart = cart;
        this.categoryService = categoryService;
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping(value = {"/", "/shop"})
    public String home(Model model) {
        loadCategories();
        Category category = null;
        Set<Product> products = null;
        if (categories.size() != 0) {
            category = categories.iterator().next();
            products = productService.findAllByCategoryId(
                    category.getSubCategories().iterator().next().getId()
            );
        }
        model.addAttribute("category", category)
                .addAttribute("categories", categories)
                .addAttribute("products", products);
        return "index";
    }

    @GetMapping("/category/{name}")
    public String category(@PathVariable String name,
                           Model model) {
        loadCategories();

        Category found = findCategoryByName(name);

        if (found == null) {
            return "index";
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

        model.addAttribute("category", found)
                .addAttribute("categories", categories)
                .addAttribute("step", 6)
                .addAttribute("entries", entries)
                .addAttribute("products", products);
        return "category";
    }

    @GetMapping("/category/{categoryName}/product/{productName}")
    public String product(Model model,
                          @PathVariable String categoryName,
                          @PathVariable String productName) {
        loadCategories();
        Category found = findCategoryByName(categoryName);
        if (found == null) {
            //// TODO: 09.04.17 Implement not found page
            model.addAttribute("categories", categories);
            return "index";
        } else {
            Product product = productService.findByName(productName);
            if (product == null) {
                //// TODO: 09.04.17 Implement not found page
                model.addAttribute("categories", categories);
                return "index";
            }
            model.addAttribute("category", found)
                    .addAttribute("product", product)
                    .addAttribute("customerForm", new CustomerForm());
            return "product";
        }
    }

    @PostMapping(value = "/category/{categoryName}/product/{productName}/buyByOne")
    public String buyByOne(@PathVariable("categoryName") String categoryName,
                           @PathVariable("productName") String productName,
                           @ModelAttribute("customerForm") @Valid CustomerForm customerForm,
                           BindingResult result, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        if (result.hasErrors()) {
            return "product";
        }
        // Save customer
        Customer customer = new Customer();
        customer.setEmail(customerForm.getEmail());
        customer.setPhone(customerForm.getPhone());
        customer.setFirstName(customerForm.getName());
        customerService.save(customer);

        // Save order
        CustomOrder order = orderService.save(customer, OrderType.IN_PROGRESS.getCode());

        // Save ordered product
        Product product = productService.findByName(productName);
        orderItemService.save(order, product, 1);

        redirectAttributes.addFlashAttribute("mess", "Ваш заказ добавлен");
        return "redirect:/category/" + URLUtil.encode(categoryName) + "/product/" + URLUtil.encode(productName);
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
            categories = categoryService.findAllByParent(null);
        }
    }
}
