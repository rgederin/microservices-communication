package com.gederin.books.controller;

import com.gederin.books.dto.BookDto;
import com.gederin.books.dto.BookListDto;
import com.gederin.books.dto.BookWithAuthorDto;
import com.gederin.books.exception.BookNotFoundException;
import com.gederin.books.service.BooksApiService;

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
public class BooksController {

    private BooksApiService booksApiService;

    @GetMapping("health")
    public String health() {
        return "books service is healthy";
    }

    @GetMapping("books")
    @ResponseStatus(HttpStatus.OK)
    public BookListDto books() {
        return booksApiService.getBooks();
    }

    @PostMapping("book")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> add(@RequestBody BookWithAuthorDto bookWithAuthorDto) {
        log.info("adding new book: " + bookWithAuthorDto);
        return booksApiService.addBook(bookWithAuthorDto);
    }

    @PutMapping("book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto update(@PathVariable int id, @RequestBody BookDto bookDto){
        try {
            return booksApiService.updateBook(id, bookDto);
        } catch (BookNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "could not update not existed book", ex);
        }
    }

}