package com.birdie.backend.services;

import com.birdie.backend.dto.response.UserDetailsResponse;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.User;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CourseMemberRepository courseMemberRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

    public UserDetailsResponse getAccountDetails(String token) {
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

    public List<Course> getAccountCourses(String token) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        return courseMemberRepository.findByUser(user)
                .stream()
                .map(CourseMember::getCourse)
                .collect(Collectors.toList());
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}