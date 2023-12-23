package com.quathar.psp.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;

/**
 * <h1>REST Client Application</h1>
 *
 * @since 2022-12-14
 * @version 1.0
 * @author Q
 */
@SpringBootApplication
public class RestClientApplication {

    // <<-CONSTANTS->>
    public static final String QUERY_JOKES = "https://api.chucknorris.io/jokes/search?query=";
    private static final Logger log = LoggerFactory.getLogger(RestClientApplication.class);

    // <<-METHODS->>
    public static void main(String[] args) {
        SpringApplication.run(RestClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        try (BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in))) {
            String input;
            while (true) {
                System.out.println("Insert the joke's theme:");
                input = br.readLine();
                if (input.isBlank()) break;
                if (input.length() < 3 || input.length() > 120)
                    System.err.println("The theme length must be between 3 and 120");
                else query(restTemplate, input);
            }
        }
        System.out.println("S Y S T E M: bye...");
        return args -> System.exit(0);
    }

    private static void query(RestTemplate restTemplate, String input) {
            JokeSearch jokeSearch = restTemplate.getForObject(
                    QUERY_JOKES + input, JokeSearch.class);
            if (jokeSearch.getTotal() != 0) {
                int resultSize = jokeSearch.getResult().size();
                Joke joke = jokeSearch.getResult()
                        .get(new java.util.Random().nextInt(resultSize));
                log.info(joke.toString());
                System.out.println(joke.getValue());
            } else System.err.println("There is not jokes for that theme");
    }

}
