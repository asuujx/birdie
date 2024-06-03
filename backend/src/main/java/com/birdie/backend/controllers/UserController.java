package com.birdie.backend.controllers;

import com.birdie.backend.dto.response.UserDetailsResponse;
import com.birdie.backend.models.Course;
import com.birdie.backend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public UserDetailsResponse getAccountDetails(@RequestHeader("Authorization") String token) {
        return userService.getAccountDetails(token);
    }

    @GetMapping("/courses")
    @PreAuthorize("isAuthenticated()")
    public List<Course> getAccountCourses(@RequestHeader("Authorization") String token) {
        return userService.getAccountCourses(token);
    }
}