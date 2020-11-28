package com.onlineshop.controller;

import com.onlineshop.model.Product;
import com.onlineshop.service.ProductService;
import com.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    
    @Autowired
    ProductService productService;


    @GetMapping("/basket")
    public String showUserSettingsPage(Model model) {
        Set<Product> products = userService.getUserProducts(SecurityContextHolder.getContext().getAuthentication().getName());
        int summ = products.stream().mapToInt(product -> Integer.parseInt(product.getPrice().isEmpty() ? "0" :
                                                                          product.getPrice())).sum();
        model.addAttribute("products",
                products
        );
        model.addAttribute("sum",
                summ);

        return "basket";
    }
    
    @PostMapping("/basket/delete/{productId}")
    public String removeProductFromBasket(@PathVariable("productId") Long productId,
                                        Model model) {
        userService.removeProductFromBasket(SecurityContextHolder.getContext().getAuthentication().getName(),
                                            productId
                                           );
        return "redirect:/basket";
    }
    
    @PostMapping("/basket/movetoorder")
    public String removeProductFromBasket(Model model) {
        userService.createOrder(SecurityContextHolder.getContext().getAuthentication().getName());
        return "redirect:/";
    }
}
