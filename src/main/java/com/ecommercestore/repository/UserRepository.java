package com.ecommercestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercestore.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // to find user by email address.
    public User findByEmail(String email);
}
