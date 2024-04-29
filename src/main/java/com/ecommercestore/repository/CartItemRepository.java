package com.ecommercestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommercestore.models.Cart;
import com.ecommercestore.models.CartItem;
import com.ecommercestore.models.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci FROM CartItem ci where ci.cart=:cart AND ci.size=:size AND ci.product=:product AND ci.userId=:userId")
    public CartItem isCartItemExists(@Param("cart") Cart cart, @Param("product") Product product,
            @Param("size") String size, @Param("userId") Long userId);

}
