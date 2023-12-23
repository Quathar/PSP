package com.quathar.psp.jwt.application.config;

import com.quathar.psp.jwt.data.model.User;
import com.quathar.psp.jwt.application.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <h1>JWT Request Filter</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    // <<-CONSTANT->>
    private static final String AUTHORIZATION_HEADER = "Authorization";

    // <<-FIELD->>
    private final JwtService _jwtService;

    // <<-CONSTRUCTOR->>
    public JwtRequestFilter(JwtService jwtService) {
        _jwtService = jwtService;
    }

    // <<-METHOD->>
    @Override
    protected void doFilterInternal(
            HttpServletRequest  request,
            HttpServletResponse response,
            FilterChain         filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith("Bearer ")) {
            String jwt = header.substring(7);
            User user = _jwtService.extractUserFromJwt(jwt);
                SecurityContextHolder.getContext()
                                     .setAuthentication(
                                             new UsernamePasswordAuthenticationToken(
                                                     user,
                                                     null,
                                                     null));
        }
        filterChain.doFilter(request, response);
    }

}
