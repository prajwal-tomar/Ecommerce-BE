package com.ecommercestore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommercestore.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

        @Query("SELECT p FROM Product p WHERE (:category IS NULL OR LOWER(p.category.name) = LOWER(:category)) AND (:minPrice IS NULL OR p.price >= :minPrice) AND (:maxPrice IS NULL OR p.price <= :maxPrice) AND (:minDiscount IS NULL OR p.discountPercentage >= :minDiscount) ORDER BY CASE WHEN :sort = 'price_low' THEN p.discountedPrice ELSE p.discountedPrice END DESC")
        public List<Product> filteredProducts(@Param("category") String category,
                        @Param("minPrice") Integer minPrice,
                        @Param("maxPrice") Integer maxPrice,
                        @Param("minDiscount") Integer minDiscount,
                        @Param("sort") String sort);
}
