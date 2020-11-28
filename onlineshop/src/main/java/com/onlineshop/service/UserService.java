package com.onlineshop.service;

import com.onlineshop.model.*;
import com.onlineshop.repository.OrderRepository;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.repository.RoleRepository;
import com.onlineshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService
      implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    
    
    public boolean save(User userFromRegistration) {
        User userFromDB = userRepository.findByUsername(userFromRegistration.getUsername());
        if (userFromDB != null) {
            return false;
        }
        userFromRegistration.setPassword(bCryptPasswordEncoder.encode(userFromRegistration.getPassword()));
        userFromRegistration.setEmail(userFromRegistration.getEmail());
        userFromRegistration.setFirstName(userFromRegistration.getFirstName());
        userFromRegistration.setLastName(userFromRegistration.getLastName());
        userFromRegistration.setSecondName(userFromRegistration.getSecondName());
        userFromRegistration.setAddress(userFromRegistration.getAddress());
        userFromRegistration.setPhoneNumber(userFromRegistration.getPhoneNumber());
        userFromRegistration.setRoles(Collections.singleton(new Role(1L,
                                                                     UserRoles.USER_ROLE
        )));
        userRepository.save(userFromRegistration);
        return true;
    }
    
    
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
    
    /////////////////////////////
    public User findUserById(final Long userId) {
        Optional<User> userFromDB = userRepository.findById(userId);
        return userFromDB.orElse(new User());
    }
    
    public List<User> allUsers() {
        return userRepository.findAll();
    }
    
    public boolean deleteUser(final Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
    
    /////////////////////////////
    public boolean addProductToBasket(String username, Long productId) {
        User userInDb = userRepository.findByUsername(username);
        Product product = productRepository.findById(productId).orElseThrow(NoSuchFieldError::new);
        Set<Product> products = userInDb.getProducts();
        products.add(product);
        product.setUsers(product.getUsers());
        productRepository.save(product);
        return true;
    }
    
    /////////////////////////////
    public Set<Product> getUserProducts(String username) {
        return userRepository.findByUsername(username).getProducts();
    }
    
    /////////////////////////////
    public boolean removeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NoSuchFieldError::new);
        orderRepository.delete(order);
        return true;
    }
    
    public boolean removeProductFromBasket(String name, Long productId) {
        User userInDB = userRepository.findByUsername(name);
        Product product = productRepository.findById(productId).orElseThrow(NoSuchFieldError::new);
        Set<Product> products = userInDB.getProducts();
        products.remove(product);
        productRepository.save(product);
        return true;
    }
    
    public boolean createOrder(String name) {
        User userInDB = userRepository.findByUsername(name);
        Set<Product> products = userInDB.getProducts();
        for (Product product : products) {
            Order order = new Order();
            order.setUser(userInDB);
            order.setName(product.getName());
            order.setPrice(product.getPrice());
            order.setCathegory(product.getCathegory());
            order.setDescription(product.getDescription());
            orderRepository.save(order);
        }
        products.removeAll(userInDB.getProducts());
        userInDB.setProducts(products);
        userRepository.save(userInDB);
        return true;
    }
}
