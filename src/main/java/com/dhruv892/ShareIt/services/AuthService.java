package com.dhruv892.ShareIt.services;

import com.dhruv892.ShareIt.dto.LogInDTO;
import com.dhruv892.ShareIt.dto.LogInResponseDto;
import com.dhruv892.ShareIt.dto.SignUpDTO;
import com.dhruv892.ShareIt.dto.UserDTO;
import com.dhruv892.ShareIt.entities.UserEntity;
import com.dhruv892.ShareIt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;

    public UserDTO signUp(SignUpDTO signUpDTO) {
        // check if user exist or not
        Optional<UserEntity> user = userRepository.findByEmail(signUpDTO.getEmail());
        if (user.isPresent()) {
            throw new BadCredentialsException("User already exists with the email: " + signUpDTO.getEmail());
        }
        //Map SignUpDTO to UserEntity and encode password
        UserEntity mappedUser = modelMapper.map(signUpDTO, UserEntity.class);
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        //save
        UserEntity savedUser = userRepository.save(mappedUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }


    public LogInResponseDto logIn(LogInDTO logInDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInDTO.getEmail(), logInDTO.getPassword())
        );
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new LogInResponseDto(user.getId(), accessToken, refreshToken);
    }

    public LogInResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        UserEntity user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        return new LogInResponseDto(user.getId(), accessToken, refreshToken);
    }

}
