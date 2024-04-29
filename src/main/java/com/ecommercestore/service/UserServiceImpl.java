package com.ecommercestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercestore.exceptions.UserException;
import com.ecommercestore.models.User;
import com.ecommercestore.repository.UserRepository;
import com.ecommercestore.security.JwtProvider;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserById(Long userId) throws UserException {
        return userRepository.findById(userId).get();
    }

    @Override
    public User findUserByJWT(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return userRepository.findByEmail(email);
    }

}
