package ua.org.javatraining.andrii_tkachenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.javatraining.andrii_tkachenko.data.dao.ProductDAO;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.util.URLUtil;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * Created by tkaczenko on 18.04.17.
 */
@Controller
@RequestMapping("/edit")
public class AdminController {
    private final ProductDAO productDAO;

    @Autowired
    public AdminController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/product/{productSku}")
    public String product(@PathVariable("productSku") String sku,
                          Model model) {
        Product product = productDAO.get(sku);
        model.addAttribute("product", product);
        return "edit_product";
    }

    @PostMapping("/product/{productSku}")
    public String updateProduct(@PathVariable("productSku") String sku,
                                @ModelAttribute("product") @Valid Product product,
                                BindingResult binding, RedirectAttributes attributes)
            throws UnsupportedEncodingException {
        if (binding.hasErrors()) {
            attributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.customerForm", binding
            );
            return "redirect:/edit/product/" + URLUtil.encode(sku);
        }
        productDAO.update(product);
        return "redirect:/edit/product/" + URLUtil.encode(sku);
    }
}
