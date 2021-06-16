package com.gederin.books.v3.controller;

import com.gederin.books.v3.dto.BookListDto;
import com.gederin.books.v3.dto.BookWithAuthorDto;
import com.gederin.books.v3.service.BooksV3Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class BooksV3Controller {

    private final BooksV3Service booksService;

    @GetMapping("books")
    @ResponseStatus(HttpStatus.OK)
    public BookListDto books() {
        return booksService.getBooksWithAuthors();
    }

    @PostMapping("book")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> add(@RequestBody BookWithAuthorDto bookWithAuthorDto) {
        boolean added = booksService.addBook(bookWithAuthorDto);

        return ResponseEntity.ok(added);
    }
}
