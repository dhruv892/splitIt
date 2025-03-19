package com.dhruv892.ShareIt.controllers;

import com.dhruv892.ShareIt.dto.LogInDTO;
import com.dhruv892.ShareIt.dto.LogInResponseDto;
import com.dhruv892.ShareIt.dto.SignUpDTO;
import com.dhruv892.ShareIt.dto.UserDTO;
import com.dhruv892.ShareIt.services.AuthService;
import com.dhruv892.ShareIt.services.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JWTService jwtService;
    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        return new ResponseEntity<>(
                authService.signUp(signUpDTO), HttpStatus.CREATED
        );
    }

    @PostMapping("/logIn")
    public ResponseEntity<LogInResponseDto> logIn(@RequestBody LogInDTO logInDTO, HttpServletResponse response) {
        LogInResponseDto logInResponseDto = authService.logIn(logInDTO);

        Cookie cookie = new Cookie("refreshToken", logInResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        return ResponseEntity.ok(logInResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LogInResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));
        LogInResponseDto loginResponseDto = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }
}
