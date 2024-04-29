package com.ecommercestore.service;

import java.util.List;

import com.ecommercestore.models.Review;
import com.ecommercestore.models.User;
import com.ecommercestore.request.ReviewRequest;

public interface ReviewService {
    public Review creatReview(ReviewRequest req, User user) throws Exception ;

    public List<Review> getAllProductReviews(Long productId);

}
