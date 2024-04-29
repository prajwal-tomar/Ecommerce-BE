package com.ecommercestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercestore.models.Cart;
import com.ecommercestore.models.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    public Cart findByUser(User user);

}
