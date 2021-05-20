package com.gederin.bff.service;

import com.gederin.bff.dto.AuthorDto;
import com.gederin.bff.dto.BookDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiClientService {
    private static final String HEALTH_ENDPOINT = "/api/v1/health";
    private static final String ALL_BOOKS_ENDPOINT = "/api/v1/books";
    private static final String ALL_AUTHORS_ENDPOINT = "/api/v1/authors";

    private final RestTemplate restTemplate;

    @Value("${books.url}")
    private String booksServiceUrl;

    @Value("${authors.url}")
    private String authrosServiceUrl;

    @Autowired
    public ApiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<String> callAuthorsHealth() {
        log.info("getting health status from authors service in seprate thread");

        String authorsHealth = restTemplate.getForObject(authrosServiceUrl + HEALTH_ENDPOINT, String.class);
        return CompletableFuture.completedFuture(authorsHealth);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<String> callBooksHealth() {
        log.info("getting health status from books service in seprate thread");

        String booksHealth = restTemplate.getForObject(booksServiceUrl + HEALTH_ENDPOINT, String.class);
        return CompletableFuture.completedFuture(booksHealth);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<AuthorDto>> callAuthors() {
        log.info("getting all authors from authors service in seprate thread");

        List<AuthorDto> authors = restTemplate.getForObject(authrosServiceUrl + ALL_AUTHORS_ENDPOINT, List.class);
        return CompletableFuture.completedFuture(authors);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<BookDto>> callBooks() {
        log.info("getting all books from books service in seprate thread");

        List<BookDto> books = restTemplate.getForObject(booksServiceUrl + ALL_BOOKS_ENDPOINT, List.class);
        return CompletableFuture.completedFuture(books);
    }
}
