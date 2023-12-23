package com.quathar.psp.jwt.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <h1>Team Entity</h1>
 * <br>
 * Alumno, puedes saber más sobre las anotaciones de JPA
 * <a href="https://www.baeldung.com/jpa-entities">aquí</a>
 *
 * @since 2023-01-XX
 * @version 2.0
 * @author jagui, Q
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Team {

    // <<-FIELDS->>
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private String city;

}
