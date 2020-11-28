package com.onlineshop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
public @Data class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "price")
    private String price;
    @Column(name = "description")
    private String description;
    @Column(name = "photo_path")
    private String photo;
    @Column(name = "cathegory")
    @Enumerated(EnumType.STRING)
    private ProductCategory cathegory;
    @Transient
    @ManyToMany(mappedBy = "products")
    private Set<User> users;
}
