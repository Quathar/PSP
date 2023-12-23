package com.quathar.psp.jwt.data.repository;

import com.quathar.psp.jwt.data.entity.Player;

import org.springframework.data.repository.ListCrudRepository;

/**
 * <h1>Player Repository</h1>
 * <br>
 * Alumno: más información sobre repositorios
 * <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories">aquí</a>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
public interface PlayerRepository extends ListCrudRepository<Player, Integer> {
}
