package com.gederin.authors.controller;

import com.gederin.authors.dto.AuthorDto;
import com.gederin.authors.exceptions.AuthorDatabaseException;
import com.gederin.authors.exceptions.AuthorNotFoundException;
import com.gederin.authors.service.AuthorsService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class AuthorsController {

    private final AuthorsService authorsService;

    @GetMapping("health")
    public String health() {
        return "authors service is healthy";
    }

    @GetMapping("authors")
    @ResponseStatus(HttpStatus.OK)
    public Collection<AuthorDto> authors() {
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

    @PostMapping("author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto add(@PathVariable int id, @RequestBody AuthorDto authorDto) {
        try {
            return authorsService.addAuthor(id, authorDto);
        } catch (AuthorDatabaseException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "internal error", ex);
        }
    }
}
