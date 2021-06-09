package com.gederin.authors.v3.controller;

import com.gederin.authors.dto.AuthorDto;
import com.gederin.authors.exceptions.AuthorNotFoundException;
import com.gederin.authors.v3.service.AuthorsV3Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v3")
@AllArgsConstructor
@Slf4j
public class AuthorsV3Controller {

    private final AuthorsV3Service authorsService;

    @PutMapping("author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto update(@PathVariable int id, @RequestBody AuthorDto authorDto) {
        try {
            return authorsService.updateAuthor(id, authorDto);
        } catch (AuthorNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "author not found for update", ex);
        }
    }
}
