package com.gederin.books.controller;

import com.gederin.books.dto.BookDto;
import com.gederin.books.exception.BookNotFoundException;
import com.gederin.books.service.BooksService;

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
public class BooksController {

    private BooksService booksService;

    @GetMapping("health")
    public String health() {
        return "books service is healthy";
    }

    @GetMapping("books")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> books() {
        return booksService.getBooks();
    }

    @PostMapping("book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto add(@PathVariable int id, @RequestBody BookDto bookDto) {
        return booksService.addBook(id, bookDto);
    }

    @PutMapping("book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto update(@PathVariable int id, @RequestBody BookDto bookDto){
        try {
            return booksService.updateBook(id, bookDto);
        } catch (BookNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "could not update not existed book", ex);
        }
    }

}