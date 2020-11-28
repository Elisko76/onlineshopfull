package com.onlineshop.repository;

import com.onlineshop.model.Order;
import com.onlineshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository
      extends JpaRepository<Order, Long>
               {
}
