package com.onlineshop.service;

import com.onlineshop.repository.OrderRepository;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.repository.RoleRepository;
import com.onlineshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
}
