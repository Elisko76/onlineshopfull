package com.onlineshop.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public @Data class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_num")
    private Integer orderNum;
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
    @ManyToOne
    private User user;
}
