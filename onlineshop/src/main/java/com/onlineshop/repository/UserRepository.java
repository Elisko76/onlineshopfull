package com.onlineshop.repository;

import com.onlineshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository
      extends JpaRepository<User, Long> {
    
    User findByUsername(String username);
}
