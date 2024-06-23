package com.birdie.backend.services;

import com.birdie.backend.config.MessageProvider;
import com.birdie.backend.exceptions.EntityDoesNotExistException;
import com.birdie.backend.models.User;
import com.birdie.backend.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityDoesNotExistException(MessageProvider.USER_NOT_FOUND));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityDoesNotExistException(MessageProvider.USER_NOT_FOUND));
    }

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityDoesNotExistException(MessageProvider.USER_NOT_FOUND));
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}