package com.example.demo.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class UserRestClient {
    private final RestClient restClient;


    public UserRestClient (RestClient.Builder builder) {
        var client = new JdkClientHttpRequestFactory();
        client.setReadTimeout(5000);

        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .requestFactory(client)
                .build();
    }

    public List<User> findAll() {
        return restClient.get()
                .uri("/users")
                .retrieve()
                .body(new ParameterizedTypeReference<List<User>>() { });
    }

    public User findById(Integer id) {
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(User.class);
    }
}
