package com.sky.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sky.authorization.dto.JwtResponseDto;
import com.sky.authorization.dto.UserDto;
import com.sky.authorization.service.AuthorizationService;
import com.sky.authorization.service.UserService;

@RestController
public class AuthenticationController {

    @Autowired
    UserService userService;
    @Autowired
    AuthorizationService authorizationService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto user) {
        JwtResponseDto jwtResponseDto;
        try {
            jwtResponseDto = authorizationService.createAuthenticationToken(user);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto user) throws Exception {
        try {
            userService.create(user);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}