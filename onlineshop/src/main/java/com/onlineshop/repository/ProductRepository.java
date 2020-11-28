package com.onlineshop.repository;

import com.onlineshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ProductRepository
      extends JpaRepository<Product, Long>,
              PagingAndSortingRepository<Product, Long> {
    Product findByName(String productName);
    
    @Query(value = "select * from product where cathegory=?1", nativeQuery = true)
    Page<Product> getAllByCategoryName(Pageable pageable, String categoryName);
    
    @Query(value = "select * from product where upper(product_name) like upper(concat(?,'%'))", nativeQuery = true)
    Page<Product> findByNameIgnoreCaseContaining(Pageable pageable, String name);
}
