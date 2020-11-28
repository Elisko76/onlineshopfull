package com.onlineshop.controller;

import com.onlineshop.model.User;
import com.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm",
                           new User()
                          );
        if (
              SecurityContextHolder.getContext().getAuthentication() != null &&
              SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
              !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        
        ) {
            
            return "redirect:/";
        }
        return "registration";
    }
    
    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("userForm") @Valid User userForm, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "registration";
        }
        if (!userService.save(userForm)) {
            errors.addError(new FieldError("userForm", "username", "Пользователь с таким именем уже существует"));
            
            return "registration";
        }
        
        return "redirect:/login";
    }
}
