package com.gederin.authors.controller;

import com.gederin.authors.dto.AuthorDto;
import com.gederin.authors.dto.AuthorsListDto;
import com.gederin.authors.exceptions.AuthorDatabaseException;
import com.gederin.authors.exceptions.AuthorNotFoundException;
import com.gederin.authors.service.AuthorsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@Slf4j
public class AuthorsController {

    private final AuthorsService authorsService;

    @GetMapping("health")
    public String health() {
        return "authors service is healthy";
    }

    @GetMapping("authors")
    @ResponseStatus(HttpStatus.OK)
    public AuthorsListDto authors() {
        return authorsService.getAuthors();
    }

    @PutMapping("author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto update(@PathVariable int id, @RequestBody AuthorDto authorDto) {
        try {
            return authorsService.updateAuthor(id, authorDto);
        } catch (AuthorNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "author not found for update", ex);
        }
    }

    @PostMapping("author")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> add(@RequestBody AuthorDto authorDto) {
        try {
            log.info("add author endpoint: {}", authorDto);
            return ResponseEntity.ok(authorsService.addAuthor(authorDto));
        } catch (AuthorDatabaseException ex) {
            log.info("author not added due to exception: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "internal error", ex);
        } catch (Exception ex) {
            log.error("unknown exception happened while adding new author");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "unknown exception happened while adding new author", ex);
        }
    }
}
