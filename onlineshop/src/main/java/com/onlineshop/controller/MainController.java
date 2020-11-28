package com.onlineshop.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    
    @GetMapping("/login")
    public String login(HttpServletRequest httpServletRequest, Model model) {

        if (
              SecurityContextHolder.getContext().getAuthentication() != null &&
              SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
              !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
        
        ) {
            System.out.println(SecurityContextHolder.getContext().getAuthentication());
            System.out.println( SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
            return "redirect:/";
        }
        return "login";
        
    }
}
