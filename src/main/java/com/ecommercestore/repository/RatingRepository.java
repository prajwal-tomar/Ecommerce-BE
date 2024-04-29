package com.ecommercestore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommercestore.models.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    // Get ratings by product
    @Query("SELECT r FROM Rating r WHERE r.product.id=:productId")
    public List<Rating> findByProductId(Long productId);

    @Query("SELECT r FROM Rating r WHERE r.user.id=:userId")
    public List<Rating> findByUserId(Long userId);

}
