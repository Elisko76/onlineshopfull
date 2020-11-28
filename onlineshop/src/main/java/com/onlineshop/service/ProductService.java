package com.onlineshop.service;

import com.onlineshop.model.Product;
import com.onlineshop.repository.ProductRepository;
import com.sun.jdi.StringReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    
    public Page<Product> allProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    
    public Page<Product> findByCategory(Pageable pageable,
                                    String name) {
        return productRepository.getAllByCategoryName(pageable,
                                                      name
                                                     );
    }
    
    public boolean create(Product product) {
        Product productFromDB = productRepository.findByName(product.getName());
        if (productFromDB != null) {
            return false;
        }
        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setCathegory(product.getCathegory());
        product.setPrice(product.getPrice());
        product.setPhoto(product.getPhoto());
        productRepository.save(product);
        return true;
    }
    
    public boolean deleteProduct(final Long productId) {
        if (productRepository.findById(productId).isPresent()) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }
    
    public Product findProductById(final Long productId) {
        Optional<Product> productFromDB = productRepository.findById(productId);
        return productFromDB.orElse(new Product());
    }
    
    public boolean update(Product product) {
        Optional<Product> productFromDB = productRepository.findById(product.getId());
        productFromDB.ifPresent(product1 -> {
            product1.setName(product.getName());
            product1.setDescription(product.getDescription());
            product1.setPrice(product.getPrice());
            product1.setCathegory(product.getCathegory());
            product1.setPhoto(product.getPhoto());
            productRepository.save(product1);
        });
        return true;
    }
    
    public Page<Product> searchProducts(Pageable pageable,
                                        String criteria) {
        return productRepository.findByNameIgnoreCaseContaining(pageable, criteria);
    }
}
