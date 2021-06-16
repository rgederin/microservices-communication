package com.gederin.bff.v3.service;

import com.gederin.bff.dto.AuthorDto;
import com.gederin.bff.dto.BookWithAuthorDto;
import com.gederin.bff.v3.dto.AuthorsListDto;
import com.gederin.bff.v3.dto.BooksListDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiClientV3Service {
    private static final String BOOKS_ENDPOINT = "/api/v3/books";
    private static final String ADD_BOOK_ENDPOINT = "/api/v3/book";
    private static final String AUTHORS_ENDPOINT = "/api/v3/authors";
    private static final String UPDATE_AUTHOR_ENDPOINT = "/api/v3/author";


    @Value("${books.url}")
    private String booksServiceUrl;

    @Value("${authors.url}")
    private String authorsServiceUrl;

    private RestTemplate restTemplate;

    @Autowired
    public ApiClientV3Service(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BooksListDto getBooksApiCall() {
        BooksListDto booksListDto = restTemplate.getForObject(booksServiceUrl + BOOKS_ENDPOINT, BooksListDto.class);

        log.info("received books from books v3 service: {}", booksListDto);

        return booksListDto;
    }


    public ResponseEntity<Boolean> addBookApiCall(BookWithAuthorDto bookWithAuthorDto) {
        ResponseEntity<Boolean> result = restTemplate.postForEntity(booksServiceUrl + ADD_BOOK_ENDPOINT, bookWithAuthorDto, Boolean.class);

        log.info("result of submitting book in book service: {}", result.getBody());

        return result;
    }

    public AuthorsListDto getAuthorsApiCall() {
        AuthorsListDto authorsListDto = restTemplate.getForObject(authorsServiceUrl + AUTHORS_ENDPOINT, AuthorsListDto.class);

        log.info("received books from books v3 service: {}", authorsListDto);

        return authorsListDto;
    }

    public ResponseEntity<Boolean> getAuthorsApiCall(AuthorDto authorDto) {
        restTemplate.put(authorsServiceUrl + UPDATE_AUTHOR_ENDPOINT, authorDto);

        return ResponseEntity.ok(true);
    }
}
