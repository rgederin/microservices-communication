package com.gederin.bff.v3.controller;

import com.gederin.bff.dto.AuthorDto;
import com.gederin.bff.dto.BookWithAuthorDto;
import com.gederin.bff.v3.dto.AuthorsListDto;
import com.gederin.bff.v3.dto.BooksListDto;
import com.gederin.bff.v3.service.BffV3Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v3")
@AllArgsConstructor
@Slf4j
public class BffV3Controller {

    private final BffV3Service bffService;

    @GetMapping("books")
    @ResponseStatus(HttpStatus.OK)
    public BooksListDto books() {
        return bffService.getBooks();
    }

    @PostMapping("book")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> addBook(@RequestBody BookWithAuthorDto bookWithAuthorDto){
        return bffService.addBook(bookWithAuthorDto);
    }

    @GetMapping("authors")
    @ResponseStatus(HttpStatus.OK)
    public AuthorsListDto authors(){
        return bffService.getAuthors();
    }

    @PutMapping("author")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> updateAuthor(@RequestBody AuthorDto authorDto){
        return bffService.updateAuthor(authorDto);
    }
}
