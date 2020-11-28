package com.onlineshop.controller;

import com.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("allUsers",
                           userService.allUsers()
                          );
        return "admin";
    }
    
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long userId,
                             Model model) {
        
        userService.deleteUser(userId);
        return "redirect:/admin";
    }
    
    @GetMapping("/orders")
    public String showOrders(Model model) {
        model.addAttribute("allUsers",
                           userService.allUsers()
                          );
        
        
        return "orders";
    }
    
    @PostMapping("/orders/delete/{orderId}")
    public String showOrders(@PathVariable("orderId") Long orderId,
                             Model model) {
        
        userService.removeOrder(orderId);
        return "redirect:/admin/orders";
    }
}
