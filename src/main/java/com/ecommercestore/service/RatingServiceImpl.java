package com.ecommercestore.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercestore.models.Product;
import com.ecommercestore.models.Rating;
import com.ecommercestore.models.User;
import com.ecommercestore.repository.RatingRepository;
import com.ecommercestore.request.RatingRequest;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ProductService productService;

    @Override
    public Rating createRating(RatingRequest req, User user) throws Exception {
        // req has productId. Find product associated to this particular request.
        Product product = productService.getProductById(req.getProductId());

        // Create Rating
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setRating(req.getRating());
        rating.setUser(user);
        rating.setCreatedAt(LocalDateTime.now());

        // Add rating to product
        product.getRatings().add(rating);

        // save rating in db
        return ratingRepository.save(rating);

    }

    @Override
    public List<Rating> getAllProductRatings(Long productId) {
        return ratingRepository.findByProductId(productId);

    }

    @Override
    public List<Rating> getAllUserRatings(Long userId) {
        return ratingRepository.findByUserId(userId);
    }

}
