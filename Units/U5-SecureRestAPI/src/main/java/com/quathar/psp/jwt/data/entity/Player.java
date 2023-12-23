package com.quathar.psp.jwt.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * <h1>Player Entity</h1>
 * <br>
 * Alumno, puedes saber más sobre las anotaciones de JPA
 * <a href="https://www.baeldung.com/jpa-entities">aquí</a>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Player {

    // <<-FIELDS->>
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private LocalDate birthDate;

    @Column
    private String position;

    @ManyToOne
    private Team currentTeam;

}
