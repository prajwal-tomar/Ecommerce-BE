package com.ecommercestore.service;

import java.util.List;

import com.ecommercestore.models.Rating;
import com.ecommercestore.models.User;
import com.ecommercestore.request.RatingRequest;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws Exception;

    public List<Rating> getAllProductRatings(Long productId);

    public List<Rating> getAllUserRatings(Long userId);

}
