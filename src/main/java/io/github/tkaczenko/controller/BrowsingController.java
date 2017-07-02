package io.github.tkaczenko.controller;

import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.category.Category;
import io.github.tkaczenko.data.model.category.CategoryAssociation;
import io.github.tkaczenko.data.model.enumeration.VisualizationType;
import io.github.tkaczenko.data.session.Cart;
import io.github.tkaczenko.data.session.LikedCart;
import io.github.tkaczenko.service.CategoryService;
import io.github.tkaczenko.service.ProductService;
import io.github.tkaczenko.util.UrlUtil;
import io.github.tkaczenko.view.CustomerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by tkaczenko on 20.03.17.
 */
@Controller
public class BrowsingController extends BaseController {
    private final ProductService productService;
    private final LikedCart likedCart;

    @Autowired
    public BrowsingController(Cart cart, CategoryService categoryService, ProductService productService,
                              LikedCart likedCart) {
        super(categoryService, cart);
        this.productService = productService;
        this.likedCart = likedCart;
    }

    @GetMapping(value = {"/", "/shop"})
    public String home(Model model) {
        // // TODO: 17.04.17 Implement Best products
        Category category;
        Set<Product> products = null;
        if (rootCategories.size() != 0) {
            category = rootCategories.iterator().next();
            Iterator<Category> iterator = category.getSubCategories().iterator();
            if (iterator.hasNext()) {
                products = productService.findAllByCategoryId(iterator.next().getId());
            }
        }
        model.addAttribute("showCategory", true)
                .addAttribute("products",
                        products == null ? null : prepareMapProductCategoryName(products));
        return "index";
    }

    @GetMapping("/category/{name}")
    public String category(@PathVariable String name,
                           Model model) {
        Category found = findCategoryByName(name);
        if (found == null) {
            return "index";
        }

        // Calculate number of category's products
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
                .addAttribute("step", 3)
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

            model.addAttribute("category", found)
                    .addAttribute("product", product)
                    .addAttribute("customerForm", new CustomerForm())
                    .addAttribute("liked", item.isLiked())
                    .addAttribute("disliked", item.isDisliked());
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
        return "redirect:/category/" + UrlUtil.encode(categoryName) + "/product/" + UrlUtil.encode(productName);
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
        return "redirect:/category/" + UrlUtil.encode(categoryName) + "/product/" + UrlUtil.encode(productName);
    }

    @PostMapping("/category/{categoryName}/product/{productName}/buyByOne")
    public String buyByOne(@PathVariable("categoryName") String categoryName,
                           @PathVariable("productName") String productName,
                           @RequestParam("sku") String sku, HttpSession session) throws UnsupportedEncodingException {
        Product product = productService.findByIdWithVisualization(sku, VisualizationType.ORIGINAL_PICTURE.getCode());
        cart.addItem(product);
        return "redirect:/cart";
    }

    @GetMapping("/search")
    public String search(String query, Model model) {
        List<Product> searchResults;
        try {
            searchResults = productService.search(query);
        } catch (Exception ex) {
            //// TODO: 17.04.17 Implement not found page
            return "";
        }
        model.addAttribute("message", String.format("Найдено %d результатов", searchResults.size()))
                .addAttribute("showCategory", true)
                .addAttribute("products", searchResults.isEmpty() ? null : prepareMapProductCategoryName(searchResults));
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
        Category found = rootCategories.parallelStream()
                .filter(c -> name.equals(c.getName()))
                .findFirst()
                .orElse(null);
        if (found == null) {
            found = rootCategories.parallelStream()
                    .flatMap(c -> c.getSubCategories().stream())
                    .filter(c -> name.equals(c.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return found;
    }
}
