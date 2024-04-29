package com.ecommercestore.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommercestore.models.Product;
import com.ecommercestore.models.Review;
import com.ecommercestore.models.User;
import com.ecommercestore.repository.ReviewRepository;
import com.ecommercestore.request.ReviewRequest;

public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review creatReview(ReviewRequest req, User user) throws Exception {

        Product product = productService.getProductById(req.getProductId());

        Review review = new Review();
        review.setCreatedAt(LocalDateTime.now());
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllProductReviews(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

}
