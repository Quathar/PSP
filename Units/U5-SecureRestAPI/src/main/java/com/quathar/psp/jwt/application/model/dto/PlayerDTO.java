package com.quathar.psp.jwt.application.model.dto;

import com.quathar.psp.jwt.data.entity.Player;

import lombok.Data;

import java.time.LocalDate;

/**
 * <h1>Player DTO (Data Transfer Object)</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@Data
public class PlayerDTO {

    // <<-FIELDS->>
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private Integer currentTeamId;

    // <<-METHOD->>
    public static PlayerDTO from(Player player){
        PlayerDTO playerDTO = new PlayerDTO();

        playerDTO.setId           (player.getId());
        playerDTO.setName         (player.getName());
        playerDTO.setBirthDate    (player.getBirthDate());
        playerDTO.setCurrentTeamId(player.getCurrentTeam().getId());

        return playerDTO;
    }

}
