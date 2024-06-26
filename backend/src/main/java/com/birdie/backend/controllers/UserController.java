package com.birdie.backend.controllers;

import com.birdie.backend.dto.response.UserDetailsResponse;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.User;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.services.JwtService;
import com.birdie.backend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.birdie.backend.config.MessageProvider.*;

@RestController
@RequestMapping("/api/account")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final CourseMemberRepository courseMemberRepository;

    @Autowired
    public UserController(UserService userService, JwtService jwtService, CourseMemberRepository courseMemberRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.courseMemberRepository = courseMemberRepository;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public UserDetailsResponse getAccountDetails(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new IllegalArgumentException(TOKEN_INVALID);
        }

        if (jwtService.isTokenValid(jwt, userDetails)) {
            User user = userService.getUserByEmail(userDetails.getUsername());
            return new UserDetailsResponse(user.getId(), user.getEmail(), user.getName(), user.getSurname(), user.getRole());
        } else {
            throw new IllegalArgumentException(TOKEN_INVALID);
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
            throw new IllegalArgumentException(TOKEN_INVALID);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        return courseMemberRepository.findByUser(user)
                .stream()
                .map(CourseMember::getCourse)
                .collect(Collectors.toList());
    }
}