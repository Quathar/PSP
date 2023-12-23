package com.quathar.psp.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * <h1>Joke Search Model</h1>
 *
 * @since 2022-12-14
 * @version 1.0
 * @author Q
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class JokeSearch {

    // <<-FIELDS->>
    private Integer total;
    private List<Joke> result;

    // <<-METHODS->>
    @Override
    public String toString() {
        return String.format("Jokes {total=%d, result=%s}", this.total, this.result);
    }

    // <<-GETTERS & SETTERS->>
    public Integer getTotal() {
        return this.total;
    }

    public List<Joke> getResult() {
        return this.result;
    }

}
