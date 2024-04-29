package com.ecommercestore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommercestore.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Get reviews by product
    @Query("SELECT r FROM Review r WHERE r.product.id=:productId")
    public List<Review> findByProductId(Long productId);
}
