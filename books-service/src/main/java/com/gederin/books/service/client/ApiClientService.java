package com.gederin.books.service.client;

import com.gederin.books.dto.AuthorDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiClientService {
    private static final String AUTHOR_ENDPOINT = "/api/v1/author";

    private final RestTemplate restTemplate;

    @Value("${authors.url}")
    private String authrosServiceUrl;

    @Autowired
    public ApiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Boolean> callAddAuthor(AuthorDto authorDto) {
        log.info("adding author in author service");

        return restTemplate.postForEntity(authrosServiceUrl + AUTHOR_ENDPOINT, authorDto, Boolean.class);
    }
}
