package com.dhruv892.SplitIt.filters;

import com.dhruv892.SplitIt.entities.UserEntity;
import com.dhruv892.SplitIt.services.JWTService;
import com.dhruv892.SplitIt.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //Extract the authorization Header
            final String requestTokenHeader = request.getHeader("Authorization");
            //if authorization header is missing or doesn't start with bearer skip this filter
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
                //go to next filter
                filterChain.doFilter(request, response);
                return;
            }
            //Extract the token
            String token = requestTokenHeader.split("Bearer ")[1];

            //retrieve the user id from token
            Long userId = jwtService.getUserIdFromToken(token);
            //make sure that SecurityContextHolder doesn't already have authenticated user and also
            //make sure there is retrieved userId
            if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                //get user details using retrieved userId
                UserEntity user = userService.getUserById(userId);

                //authenticate the user
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            //pass the request to next filter
            filterChain.doFilter(request, response);
        } catch (Exception e){
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
