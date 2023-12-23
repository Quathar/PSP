package com.quathar.psp.jwt.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <h1>Security Configuration</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@Configuration
public class SecurityConfiguration {

    // <<-FIELD->>
    private final JwtRequestFilter _jwtRequestFilter;

    // <<-CONSTRUCTOR->>
    public SecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
        _jwtRequestFilter = jwtRequestFilter;
    }

    // <<-METHODS->>
    @Bean
    PasswordEncoder passwordEncoder(
            @Value("${password.secret-key}")
            String secretKey
    ) {
        return new Pbkdf2PasswordEncoder(
                secretKey,
                16,
                31000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()     .disable()
                    .csrf()     .disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilterBefore(_jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests().requestMatchers("/user").permitAll()
                    .anyRequest().authenticated();

        return httpSecurity.build();
    }

}
