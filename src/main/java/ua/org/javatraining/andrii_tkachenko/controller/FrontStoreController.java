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
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;
import ua.org.javatraining.andrii_tkachenko.data.model.enumeration.OrderType;
import ua.org.javatraining.andrii_tkachenko.data.model.enumeration.VisualizationType;
import ua.org.javatraining.andrii_tkachenko.data.session.Cart;
import ua.org.javatraining.andrii_tkachenko.data.session.LikedCart;
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
    private final LikedCart likedCart;

    private Set<Category> categories;

    @Autowired
    public FrontStoreController(Cart cart, CategoryService categoryService, ProductService productService,
                                CustomerService customerService, OrderService orderService,
                                OrderItemService orderItemService, LikedCart likedCart) {
        this.cart = cart;
        this.categoryService = categoryService;
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.likedCart = likedCart;
    }

    @GetMapping(value = {"/", "/shop"})
    public String home(Model model, HttpSession session) {
        Category category = null;
        Set<Product> products = null;
        if (categories.size() != 0) {
            category = categories.iterator().next();
            Iterator<Category> iterator = category.getSubCategories().iterator();
            if (iterator.hasNext()) {
                products = productService.findAllByCategoryId(iterator.next().getId());
            }
        }
        session.setAttribute("cartSize", cart.sumQuantity());
        model.addAttribute("products", prepareMapProductCategoryName(products));
        return "index";
    }

    @GetMapping("/category/{name}")
    public String category(@PathVariable String name,
                           Model model) {
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
                .addAttribute("step", 6)
                .addAttribute("entries", entries)
                .addAttribute("products", products);
        return "category";
    }

    @GetMapping("/category/{categoryName}/product/{productName}")
    public String product(@PathVariable String categoryName,
                          @PathVariable String productName,
                          Model model) {
        Category found = findCategoryByName(categoryName);
        if (found == null) {
            //// TODO: 09.04.17 Implement not found page
            return "index";
        } else {
            Product product = productService.findByName(productName);
            if (product == null) {
                //// TODO: 09.04.17 Implement not found page
                return "index";
            }
            String sku = product.getSku();
            LikedCart.Item item = likedCart.getItem(sku);
            if (item == null) {
                item = new LikedCart.Item();
                likedCart.addItem(sku, item);
            }

            model.addAttribute("liked", item.isLiked())
                    .addAttribute("disliked", item.isDisliked());

            model.addAttribute("category", found)
                    .addAttribute("product", product)
                    .addAttribute("customerForm", new CustomerForm());
            return "product";
        }
    }

    @PostMapping("/category/{categoryName}/product/{productName}/addLike")
    public String addLike(@PathVariable("categoryName") String categoryName,
                          @PathVariable("productName") String productName,
                          @RequestParam("sku") String sku)
            throws UnsupportedEncodingException {
        LikedCart.Item item = likedCart.getItem(sku);
        if (!item.isLiked()) {
            Product product = productService.findById(sku);
            if (item.isDisliked()) {
                product.setDislikes(product.getDislikes() - 1);
                item.setDisliked(false);
            }
            product.setLikes(product.getLikes() + 1);
            item.setLiked(true);
            productService.save(product);
        }
        return "redirect:/category/" + URLUtil.encode(categoryName) + "/product/" + URLUtil.encode(productName);
    }

    @PostMapping("/category/{categoryName}/product/{productName}/addDislike")
    public String addDislike(@PathVariable("categoryName") String categoryName,
                             @PathVariable("productName") String productName,
                             @RequestParam("sku") String sku)
            throws UnsupportedEncodingException {
        LikedCart.Item item = likedCart.getItem(sku);
        if (!item.isDisliked()) {
            Product product = productService.findById(sku);
            if (item.isLiked()) {
                product.setLikes(product.getLikes() - 1);
                item.setLiked(false);
            }
            product.setDislikes(product.getDislikes() + 1);
            item.setDisliked(true);
            productService.save(product);
        }
        return "redirect:/category/" + URLUtil.encode(categoryName) + "/product/" + URLUtil.encode(productName);
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
                           BindingResult binding, RedirectAttributes attributes, HttpSession session) {
        // Validate form
        boolean isEmpty = cart.getItems().size() == 0;
        if (binding.hasErrors() || isEmpty) {
            if (isEmpty) {
                attributes.addFlashAttribute("mess", "Корзина пуста");
            }
            attributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.customerForm", binding
            );
            attributes.addFlashAttribute("customerForm", customerForm);
            return "redirect:/cart";
        }

        // Check amount and quantity of the product from cart
        for (Map.Entry<Product, Integer> item : cart.getItems().entrySet()) {
            if (item.getKey().getAmount() < item.getValue()) {
                attributes.addFlashAttribute("mess", "Невозможно столько купить");
                attributes.addFlashAttribute("customerForm", customerForm);
                return "redirect:/cart";
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

        attributes.addFlashAttribute("orderId", order.getId());
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
                .addAttribute("numOfItems", numOfItems);
        if (!model.containsAttribute("customerForm")) {
            model.addAttribute("customerForm", new CustomerForm());
        }
        return "cart";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("sku") String sku,
                            @RequestHeader("referer") String referer, HttpSession session) {
        Product product = productService.findByIdWithVisualization(sku, VisualizationType.ORIGINAL_PICTURE.getCode());
        cart.addItem(product);
        session.setAttribute("cartSize", cart.sumQuantity());
        return "redirect:" + referer;
    }

    @PostMapping("/clearCart")
    public String clearCart(HttpSession session) {
        cart.clear();
        session.setAttribute("cartSize", cart.sumQuantity());
        return "redirect:/cart";
    }

    @PostMapping("/updateCart")
    public String updateCart(@RequestParam("sku") String sku,
                             @RequestParam("quantity") Integer quantity, HttpSession session) {
        cart.updateItem(sku, quantity);
        session.setAttribute("cartSize", cart.sumQuantity());
        return "redirect:/cart";
    }

    @PostMapping("/deleteFromCart")
    public String deleteFromCart(@RequestParam("sku") String sku, HttpSession session) {
        cart.removeItem(sku);
        session.setAttribute("cartSize", cart.sumQuantity());
        return "redirect:/cart";
    }

    @GetMapping("/search")
    public String search(String query, Model model) {
        List<Product> searchResults;
        try {
            searchResults = productService.search(query);
        } catch (Exception ex) {
            return "";
        }

        model.addAttribute("mess", "Найдено " + searchResults.size() + " результатов")
                .addAttribute("products", prepareMapProductCategoryName(searchResults));
        return "search";
    }

    private Map<Product, String> prepareMapProductCategoryName(Collection<Product> products) {
        Map<Product, String> map = new HashMap<>();
        for (Product product : products) {
            Iterator<CategoryAssociation> iterator = product.getCategories().iterator();
            if (iterator.hasNext()) {
                map.put(product, iterator.next().getCategory().getName());
            } else {
                map.put(product, null);
            }
        }
        return map;
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

    @ModelAttribute("categories")
    public Set<Category> loadCategories() {
        return categories = categoryService.findAllByParent(null);
    }
}
