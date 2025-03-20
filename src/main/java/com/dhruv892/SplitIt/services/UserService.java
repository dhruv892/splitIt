package com.dhruv892.SplitIt.services;

import com.dhruv892.SplitIt.entities.UserEntity;
import com.dhruv892.SplitIt.exceptions.ResourceNotFoundException;
import com.dhruv892.SplitIt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User with username " + username + " not found"));
    }

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                    new ResourceNotFoundException("User with id " + userId + "not found")
                );

    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }
}
