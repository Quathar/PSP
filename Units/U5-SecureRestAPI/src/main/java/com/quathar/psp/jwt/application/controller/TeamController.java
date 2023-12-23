package com.quathar.psp.jwt.application.controller;

import com.quathar.psp.jwt.data.entity.Team;
import com.quathar.psp.jwt.data.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Team Controller</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@RestController
public class TeamController {

    // <<-FIELD->>
    private final TeamRepository _teamRepository;

    // <<-CONSTRUCTOR->>
    @Autowired
    public TeamController(TeamRepository teamRepository) {
        _teamRepository = teamRepository;
    }

    // <<-METHOD->>
    @GetMapping("teams")
    public Iterable<Team> getAllTeams() {
        return _teamRepository.findAll();
    }

}
