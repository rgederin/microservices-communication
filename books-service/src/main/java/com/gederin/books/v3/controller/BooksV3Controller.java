package com.gederin.books.v3.controller;

import com.gederin.books.v3.dto.BookWithAuthorDto;
import com.gederin.books.v3.service.BooksV3Service;
import com.gederin.books.v3.service.mapper.MapperV3Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v3")
@AllArgsConstructor
@Slf4j
public class BooksV3Controller {

    private final BooksV3Service booksService;
    private final MapperV3Service mapperService;

    @GetMapping("books")
    @ResponseStatus(HttpStatus.OK)
    public List<BookWithAuthorDto> books() {
        return booksService.getBooks()
                .stream()
                .map(mapperService::mapBookWithAuthorToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("book")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> add(@RequestBody BookWithAuthorDto bookWithAuthorDto) throws IOException {
        boolean added = booksService.addBook(mapperService.mapBookWithAuthorDtoToModel(bookWithAuthorDto));

        return ResponseEntity.ok(added);
    }
}
