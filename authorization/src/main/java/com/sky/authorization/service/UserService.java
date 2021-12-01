package com.sky.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.authorization.dto.UserDto;
import com.sky.authorization.model.UserEntity;
import com.sky.authorization.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    JwtUserDetailsService userDetailsService;
    @Autowired
    UserRepository repository;

    public UserEntity create(UserDto user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exist");
        }
        return userDetailsService.save(user);
    }

}
