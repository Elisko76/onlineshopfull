package com.onlineshop.controller;

import com.onlineshop.model.Product;
import com.onlineshop.service.ProductService;
import com.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    
    private Pageable pageable;
    
    @GetMapping({"/products/search/{searchCriteria}", "/products/search/"})
    public String searchProducts(Model model,
                                 @PathVariable(value = "searchCriteria", required = false) Optional<String> searchCriteria,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page) {
        pageable = PageRequest.of(page - 1,
                                  10,
                                  Sort.by("product_name").ascending()
                                 );
        
        Page<Product> allProducts = productService.searchProducts(pageable,
                                                                  searchCriteria.orElse("")
                                                                 );
        model.addAttribute("products",
                           allProducts.getContent()
                          );
        model.addAttribute("page",
                           allProducts
                          );
        
        return "products";
    }
    
    @GetMapping("/products")
    public String productList(Model model,
                              @RequestParam(value = "page", defaultValue = "1") Integer page) {
        pageable = PageRequest.of(page - 1,
                                  10,
                                  Sort.by("name").ascending()
                                 );
        Page<Product> allProducts = productService.allProducts(pageable);
        
        model.addAttribute("products",
                           allProducts.getContent()
                          );
        model.addAttribute("page",
                           allProducts
                          );
        
        return "products";
    }
    
    @GetMapping("/phones")
    public String phonesList(Model model,
                             @RequestParam(value = "page", defaultValue = "1") Integer page) {
        pageable = PageRequest.of(page - 1,
                                  10
                                 );
        Page<Product> allPhones = productService.findByCategory(pageable,
                                                                "PHONE"
                                                               );
        
        model.addAttribute("allPhones",
                           allPhones.getContent()
                          );
        model.addAttribute("page",
                           allPhones
                          );
        
        return "phones";
    }
    
    @GetMapping("/ebooks")
    public String ebooksList(Model model,
                             @RequestParam(value = "page", defaultValue = "1") Integer page) {
        pageable = PageRequest.of(page - 1,
                                  10
                                 );
        Page<Product> allBooks = productService.findByCategory(pageable,
                                                               "EBOOK"
                                                              );
        
        model.addAttribute("allPhones",
                           allBooks.getContent()
                          );
        model.addAttribute("page",
                           allBooks
                          );
        
        
        return "ebooks";
    }
    
    
    @GetMapping("/headphones")
    public String headphonesList(Model model,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page) {
        pageable = PageRequest.of(page - 1,
                                  10
                                 );
        Page<Product> allBooks = productService.findByCategory(pageable,
                                                               "HEADPHONES"
                                                              );
        
        model.addAttribute("allPhones",
                           allBooks.getContent()
                          );
        model.addAttribute("page",
                           allBooks
                          );
        
        
        return "headphones";
    }
    
    @GetMapping("/watches")
    public String watchList(Model model,
                            @RequestParam(value = "page", defaultValue = "1") Integer page) {
        pageable = PageRequest.of(page - 1,
                                  10
                                 );
        Page<Product> allBooks = productService.findByCategory(pageable,
                                                               "WATCH"
                                                              );
        
        model.addAttribute("allPhones",
                           allBooks.getContent()
                          );
        model.addAttribute("page",
                           allBooks
                          );
        
        return "watches";
    }
    
    @GetMapping("/tablets")
    public String tabletsList(Model model,
                              @RequestParam(value = "page", defaultValue = "1") Integer page) {
        pageable = PageRequest.of(page - 1,
                                  10
                                 );
        Page<Product> allBooks = productService.findByCategory(pageable,
                                                               "TABLET"
                                                              );
        
        model.addAttribute("allPhones",
                           allBooks.getContent()
                          );
        model.addAttribute("page",
                           allBooks
                          );
        
        return "tablets";
    }
    
    @PostMapping("/{username}/add/product/{productId}/{action}")
    public String addProductToTheBasket(@PathVariable("productId") Long productId,
                                        @PathVariable("username") String username,
                                        @PathVariable("action") String action,
                                        Model model) {
        userService.addProductToBasket(username,
                                       productId
                                      );
        if (action.equals("basket")) {
            return "redirect:/basket";
        }
        return "redirect:/basket";
    }
    
    @GetMapping(value = "/product/{productId}/edit")
    public String showEditProductPage(@PathVariable String productId,
                                      Model model) {
        Product product = productService.findProductById(Long.parseLong(productId));
        model.addAttribute("product",
                           product
                          );
        return "update_product";
    }
    
    @PostMapping("/product/{productId}/edit")
    public String updateProductInfo(@PathVariable Long productId,
                                    @ModelAttribute("product") Product updatedProduct,
                                    Model model) {
        updatedProduct.setId(productId);
        productService.update(updatedProduct);
        model.addAttribute("product",
                           updatedProduct
                          );
        
        return "redirect:/product/" + productId;
    }
    
    @GetMapping("/product/add")
    public String addProductPage(Model model) {
        model.addAttribute("productForm",
                           new Product()
                          );
        return "addproduct";
    }
    
    @GetMapping("/product/{productId}")
    public String showProductPage(@PathVariable("productId") Long productId,
                                  Model model) {
        model.addAttribute("product",
                           productService.findProductById(productId)
                          );
        return "product";
    }
    
    @PostMapping("/product/add")
    public String createNewProduct(@ModelAttribute("productForm") @Valid Product productForm, Model model,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addproduct";
        }
        if (!productService.create(productForm)) {
            model.addAttribute("productNameError",
                               "Such Product already exist"
                              );
            return "addproduct";
        }
        return "redirect:/products";
    }
    
    @PostMapping("/product/delete/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId, Model model) {
        productService.deleteProduct(productId);
        return "redirect:/products";
    }
    
    
}
