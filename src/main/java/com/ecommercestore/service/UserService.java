package com.ecommercestore.service;

import com.ecommercestore.exceptions.UserException;
import com.ecommercestore.models.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserByJWT(String jwt) throws UserException;

}
