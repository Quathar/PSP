package com.quathar.psp.jwt.application.controller;

import com.quathar.psp.jwt.application.model.AuthenticationRequest;
import com.quathar.psp.jwt.data.model.User;
import com.quathar.psp.jwt.data.repository.UserRepository;
import com.quathar.psp.jwt.application.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>User Controller</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@RestController
public class UserController {

    // <<-FIELDS->>
    private final UserRepository  _userRepository;
    private final PasswordEncoder _passwordEncoder;
    private final JwtService      _jwtService;

    // <<-CONSTRUCTOR->>
    @Autowired
    public UserController(
            UserRepository  userRepository,
            PasswordEncoder passwordEncoder,
            JwtService      jwtService
    ) {
        _userRepository  = userRepository;
        _passwordEncoder = passwordEncoder;
        _jwtService      = jwtService;
    }

    // <<-METHOD->>
    @PostMapping("user")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest body) {
        User user = _userRepository.findByUsername(body.getUsername());
        if (user == null) {
            return ResponseEntity.notFound()
                                 .build();
        }

        if (!_passwordEncoder.matches(body.getPassword(), user.getEncryptedPassword())) {
            return ResponseEntity.badRequest()
                                 .build();
        }

        String jwt = _jwtService.createJwt(user);
        return ResponseEntity.ok(jwt);
    }

}
