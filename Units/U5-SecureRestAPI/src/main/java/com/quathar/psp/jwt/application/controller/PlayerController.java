package com.quathar.psp.jwt.application.controller;

import com.quathar.psp.jwt.application.model.dto.PlayerDTO;
import com.quathar.psp.jwt.data.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Player Controller</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@RestController
public class PlayerController {

    // <<-FIELDS->>
    private final PlayerRepository _playerRepository;

    // <<-CONSTRUCTOR->>
    @Autowired
    public PlayerController(PlayerRepository playerRepository) {
        _playerRepository = playerRepository;
    }

    // <<-METHOD->>
    @GetMapping("/players")
    public Iterable<PlayerDTO> getAll() {
        // Hola, alumno.
        // Si te interesa saber más sobre DTOs
        // y como mapear Entidades a DTO puedes ver estos dos videos
        // [Uso del patrón Data Transfer Object (DTO)]
        // (https://openwebinars.net/academia/aprende/api-rest-spring-boot/7239/)
        // [Implementando DTO con ModelMapper]
        // (https://openwebinars.net/academia/aprende/api-rest-spring-boot/7240/)
        return _playerRepository.findAll()
                                .stream()
                                .map(PlayerDTO::from)
                                .toList();
    }

}
