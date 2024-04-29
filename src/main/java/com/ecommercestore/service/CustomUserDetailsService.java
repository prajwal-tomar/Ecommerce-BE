package com.ecommercestore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommercestore.models.USER_ROLE;
import com.ecommercestore.models.User;
import com.ecommercestore.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    // This is the custom implementation so that we could override the default
    // behaviour of Spring Security
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get the User associated with the username
        User user = userRepository.findByEmail(username);

        // User not found
        if (user == null) {
            throw new UsernameNotFoundException("User does not exist with email: " + username);
        }

        // Get the role associated with the user
        USER_ROLE role = user.getRole();

        // Set role as Customer if no role is set explicitly
        if (role == null) {
            role = USER_ROLE.ROLE_CUSTOMER;
        }

        // Add the role of the user to the GrantedAuthorities list
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}
