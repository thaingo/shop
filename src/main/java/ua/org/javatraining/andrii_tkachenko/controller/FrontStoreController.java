package ua.org.javatraining.andrii_tkachenko.controller;

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
import ua.org.javatraining.andrii_tkachenko.data.model.enumeration.VisualizationType;
import ua.org.javatraining.andrii_tkachenko.data.session.Cart;
import ua.org.javatraining.andrii_tkachenko.service.*;
import ua.org.javatraining.andrii_tkachenko.util.URLUtil;
import ua.org.javatraining.andrii_tkachenko.view.CustomerForm;

import javax.servlet.http.HttpSession;
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
    public String home(Model model, HttpSession session) {
        loadCategories();
        Category category = null;
        Set<Product> products = null;
        if (categories.size() != 0) {
            category = categories.iterator().next();
            products = productService.findAllByCategoryId(
                    category.getSubCategories().iterator().next().getId()
            );
        }
        session.setAttribute("cartSize", cart.sumQuantity());
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
    public String product(@PathVariable String categoryName,
                          @PathVariable String productName,
                          Model model, HttpSession session) {
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
            Boolean liked = (Boolean) session.getAttribute("liked");
            Boolean disliked = (Boolean) session.getAttribute("disliked");
            if (liked == null && disliked == null) {
                session.setAttribute("liked", false);
                session.setAttribute("disliked", false);
            }
            model.addAttribute("category", found)
                    .addAttribute("product", product)
                    .addAttribute("customerForm", new CustomerForm());
            return "product";
        }
    }

    @PostMapping("/category/{categoryName}/product/{productName}/addLike")
    public String addLike(@PathVariable("categoryName") String categoryName,
                          @PathVariable("productName") String productName,
                          @RequestParam("sku") String sku, HttpSession session) throws UnsupportedEncodingException {
        Boolean liked = (Boolean) session.getAttribute("liked");
        Boolean disliked = (Boolean) session.getAttribute("disliked");
        if (!liked) {
            Product product = productService.findById(sku);
            if (disliked) {
                product.setDislikes(product.getDislikes() - 1);
                session.setAttribute("disliked", false);
            }
            product.setLikes(product.getLikes() + 1);
            session.setAttribute("liked", true);
            productService.save(product);
        }
        return "redirect:/category/" + URLUtil.encode(categoryName) + "/product/" + URLUtil.encode(productName);
        // String.valueOf(product.getLikes());
    }

    @PostMapping("/category/{categoryName}/product/{productName}/addDislike")
    public String addDislike(@PathVariable("categoryName") String categoryName,
                             @PathVariable("productName") String productName,
                             @RequestParam("sku") String sku, HttpSession session) throws UnsupportedEncodingException {
        Boolean liked = (Boolean) session.getAttribute("liked");
        Boolean disliked = (Boolean) session.getAttribute("disliked");
        if (!disliked) {
            Product product = productService.findById(sku);
            if (liked) {
                product.setLikes(product.getLikes() - 1);
                session.setAttribute("liked", false);
            }
            product.setDislikes(product.getDislikes() + 1);
            session.setAttribute("disliked", true);
            productService.save(product);
        }
        return "redirect:/category/" + URLUtil.encode(categoryName) + "/product/" + URLUtil.encode(productName);
        // String.valueOf(product.getLikes());
    }

    @PostMapping("/category/{categoryName}/product/{productName}/buyByOne")
    public String buyByOne(@PathVariable("categoryName") String categoryName,
                           @PathVariable("productName") String productName,
                           @RequestParam("sku") String sku, HttpSession session) throws UnsupportedEncodingException {
        Product product = productService.findByIdWithVisualization(sku, VisualizationType.ORIGINAL_PICTURE.getCode());
        cart.addItem(product);
        return "redirect:/cart";
    }

    @PostMapping("/cart/buyByOne")
    public String buyByOne(@ModelAttribute("customerForm") @Valid CustomerForm customerForm,
                           BindingResult result, RedirectAttributes redirectAttributes,
                           Model model, HttpSession session) {
        boolean isEmpty = cart.getItems().size() == 0;
        if (result.hasErrors() || isEmpty) {
            if (isEmpty) {
                model.addAttribute("mess", "Корзина пуста");
            }
            Map<Product, Integer> itemMap = cart.getItems();
            double subTotal = cart.calculateSubTotal();
            double total = cart.calculateTotal(subTotal);
            Integer numOfItems = cart.sumQuantity();
            model.addAttribute("itemMap", itemMap)
                    .addAttribute("subTotal", subTotal)
                    .addAttribute("total", subTotal == 0 ? 0 : total)
                    .addAttribute("numOfItems", numOfItems);
            return "cart";
        }

        for (Map.Entry<Product, Integer> item : cart.getItems().entrySet()) {
            if (item.getKey().getAmount() < item.getValue()) {
                Map<Product, Integer> itemMap = cart.getItems();
                double subTotal = cart.calculateSubTotal();
                double total = cart.calculateTotal(subTotal);
                Integer numOfItems = cart.sumQuantity();
                model.addAttribute("itemMap", itemMap)
                        .addAttribute("subTotal", subTotal)
                        .addAttribute("total", subTotal == 0 ? 0 : total)
                        .addAttribute("numOfItems", numOfItems)
                        .addAttribute("mess", "Невозможно столько купить");
                return "cart";
            }
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
        CustomOrder order = orderService.save(customer, OrderType.IN_PROGRESS.getCode(), customerForm.getAddress());

        // Save ordered products
        Map<Product, Integer> itemMap = new HashMap<>(cart.getItems());
        orderItemService.save(order, itemMap);

        // Submit
        itemMap.entrySet().parallelStream().forEach(e -> {
            Product key = e.getKey();
            key.setAmount(key.getAmount() - e.getValue());
        });
        productService.save(itemMap.keySet());
        order.setStatus(OrderType.SUBMITTED.getCode());
        orderService.save(order);

        redirectAttributes.addFlashAttribute("orderId", order.getId());
        cart.clear();

        session.setAttribute("cartSize", cart.sumQuantity());

        return "redirect:/order";
    }

    @GetMapping("/order")
    public String order(Model model) {
        return "order";
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

    @PostMapping("/addToCart")
    @ResponseBody
    public String addToCart(@RequestParam("sku") String sku, HttpSession session) {
        Product product = productService.findByIdWithVisualization(sku, VisualizationType.ORIGINAL_PICTURE.getCode());
        cart.addItem(product);
        session.setAttribute("cartSize", cart.sumQuantity());
        return cart.sumQuantity().toString();
    }

    @PostMapping("/clearCart")
    public String clearCart() {
        cart.clear();
        return "redirect:/cart";
    }

    @PostMapping("/updateCart")
    public String updateCart(@RequestParam("sku") String sku, @RequestParam("quantity") Integer quantity) {
        cart.updateItem(sku, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/deleteFromCart")
    public String deleteFromCart(@RequestParam("sku") String sku) {
        cart.removeItem(sku);
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
