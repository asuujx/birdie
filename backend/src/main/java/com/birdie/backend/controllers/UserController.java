package com.birdie.backend.controllers;

import com.birdie.backend.dto.response.UserDetailsResponse;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.User;
import com.birdie.backend.services.CourseService;
import com.birdie.backend.services.JwtService;
import com.birdie.backend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public UserDetailsResponse getAccountDetails(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }

        if (jwtService.isTokenValid(jwt, userDetails)) {
            User user = userService.getUserByEmail(userDetails.getUsername());
            return new UserDetailsResponse(user.getId(), user.getEmail(), user.getName(), user.getSurname(), user.getRole());
        } else {
            throw new RuntimeException("Invalid or expired token");
        }
    }

    @GetMapping("/courses")
    @PreAuthorize("isAuthenticated()")
    public List<Course> getAccountCourses(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        return courseService.getCoursesByUser(user);
    }
}