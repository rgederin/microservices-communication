package com.gederin.authors.v3.controller;

import com.gederin.authors.v3.dto.AuthorDto;
import com.gederin.authors.v3.dto.AuthorsListDto;
import com.gederin.authors.v3.model.Author;
import com.gederin.authors.v3.service.AuthorsV3Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v3")
@AllArgsConstructor
@Slf4j
public class AuthorsV3Controller {

    private final AuthorsV3Service authorsService;

    @GetMapping("authors")
    @ResponseStatus(HttpStatus.OK)
    public AuthorsListDto authors() {
        return authorsService.getAuthors();
    }

    @PutMapping("author")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> update(@RequestBody AuthorDto authorDto) {
        return ResponseEntity.of(Optional.of(authorsService.updateAuthor(
                Author.builder().id(authorDto.getId())
                        .firstName(authorDto.getFirstName())
                        .lastName(authorDto.getLastName()).build())));
    }
}
