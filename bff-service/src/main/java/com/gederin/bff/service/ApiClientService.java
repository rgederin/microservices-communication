package com.gederin.bff.service;

import com.gederin.bff.dto.AuthorsListDto;
import com.gederin.bff.dto.BookWithAuthorDto;
import com.gederin.bff.dto.BooksListDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiClientService {
    private static final String HEALTH_ENDPOINT = "/api/v1/health";
    private static final String ALL_BOOKS_ENDPOINT = "/api/v1/books";
    private static final String ALL_AUTHORS_ENDPOINT = "/api/v1/authors";
    private static final String ADD_BOOK_ENDPOINT = "/api/v1/book/";

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
    public CompletableFuture<AuthorsListDto> callAuthors() {
        log.info("getting all authors from authors service in seprate thread");

        AuthorsListDto authorsListDto = restTemplate.getForObject(authrosServiceUrl + ALL_AUTHORS_ENDPOINT, AuthorsListDto.class);
        return CompletableFuture.completedFuture(authorsListDto);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<BooksListDto> callBooks() {
        log.info("getting all books from books service in seprate thread");

        BooksListDto booksListDto = restTemplate.getForObject(booksServiceUrl + ALL_BOOKS_ENDPOINT, BooksListDto.class);
        return CompletableFuture.completedFuture(booksListDto);
    }

    public ResponseEntity<Boolean> callAddBook(int id, BookWithAuthorDto bookWithAuthorDto) {
        log.info("adding book in book service");

        return restTemplate.postForEntity(booksServiceUrl + ADD_BOOK_ENDPOINT + id, bookWithAuthorDto, Boolean.class);
    }
}
