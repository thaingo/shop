package ua.org.javatraining.andrii_tkachenko.webpages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/category/{categoryName}/product/{productName}/buyByOne")
    public String buyByOne(@PathVariable("categoryName") String categoryName,
                           @PathVariable("productName") String productName,
                           @ModelAttribute("customerForm") @Valid CustomerForm customerForm,
                           BindingResult result, RedirectAttributes redirectAttributes)
            throws UnsupportedEncodingException {
        loadCategories();

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

    @PostMapping("/cart/buyByOne")
    public String cartBuy(@ModelAttribute("customerForm") @Valid CustomerForm customerForm,
                          BindingResult result, RedirectAttributes redirectAttributes) {
        loadCategories();

        if (result.hasErrors()) {
            return "cart";
        }

        // Save customer
        Customer customer = new Customer();
        customer.setEmail(customerForm.getEmail());
        customer.setPhone(customerForm.getPhone());
        customer.setFirstName(customerForm.getName());
        customerService.save(customer);

        // Save order
        double subTotal = cart.calculateSubTotal();
        double total = cart.calculateTotal(subTotal);
        CustomOrder order = orderService.save(customer, OrderType.IN_PROGRESS.getCode());

        // Save ordered products
        Map<Product, Integer> itemMap = new HashMap<>(cart.getItems());
        orderItemService.save(order, itemMap);

        redirectAttributes.addFlashAttribute("mess", "Ваш заказ добавлен");
        cart.clear();

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        Map<Product, Integer> itemMap = cart.getItems();
        double subTotal = cart.calculateSubTotal();
        double total = cart.calculateTotal(subTotal);
        Integer numOfItems = cart.sumQuantity();
        model.addAttribute("itemMap", itemMap)
                .addAttribute("subTotal", subTotal)
                .addAttribute("total", subTotal == 0 ? 0 : total)
                .addAttribute("numOfItems", numOfItems)
                .addAttribute("customerForm", new CustomerForm());
        return "cart";
    }

    @GetMapping("/addToCart")
    @ResponseBody
    public String addToCart(@RequestParam("sku") String sku) {
        Product product = productService.findByIdWithVisualizations(sku);
        cart.addItem(product);
        return cart.sumQuantity().toString();
    }

    @GetMapping("/addLike")
    @ResponseBody
    public String addLike(@RequestParam("sku") String sku) {
        Product product = productService.findById(sku);
        product.setLikes(product.getLikes() + 1);
        productService.save(product);
        return String.valueOf(product.getLikes());
    }

    @GetMapping("/addDislike")
    @ResponseBody
    public String addDislike(@RequestParam("sku") String sku) {
        Product product = productService.findById(sku);
        product.setDislikes(product.getDislikes() + 1);
        productService.save(product);
        return String.valueOf(product.getDislikes());
    }

    @GetMapping("/clearCart")
    public String clearCart() {
        cart.clear();
        return "redirect:/cart";
    }

    @PostMapping("/updateCart")
    public String updateCart(@RequestParam("sku") String sku, @RequestParam("quantity") Integer quantity) {
        cart.updateItem(sku, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/search")
    public String search(String query, Model model) {
        loadCategories();

        List<Product> searchResults;
        try {
            searchResults = productService.search(query);
        } catch (Exception ex) {
            return "";
        }
        model.addAttribute("mess", "Found")
                .addAttribute("categories", categories)
                .addAttribute("products", searchResults);
        return "search";
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
