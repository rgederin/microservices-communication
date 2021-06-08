package com.gederin.books.service;

import com.gederin.books.dto.AuthorDto;
import com.gederin.books.dto.BookDto;
import com.gederin.books.dto.BookListDto;
import com.gederin.books.dto.BookWithAuthorDto;
import com.gederin.books.exception.AuthorServiceInvocationFailedException;
import com.gederin.books.exception.BookNotAddedException;
import com.gederin.books.model.Book;
import com.gederin.books.repository.BooksRepository;
import com.gederin.books.service.client.ApiClientService;
import com.gederin.books.service.mapper.MapperService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class BooksApiService {

    private final BooksRepository booksRepository;
    private final MapperService mapperService;
    private final ApiClientService apiClientService;

    public BookListDto getBooks() {
        BookListDto bookListDto = new BookListDto();

        bookListDto.setBooks(booksRepository.getBooks()
                .stream()
                .map(mapperService::mapToBookDto)
                .collect(Collectors.toList()));

        return bookListDto;
    }

    public ResponseEntity<Boolean> addBook(BookWithAuthorDto bookWithAuthorDto) {
        Book book = mapperService.mapFromBookWithAuthorDto(bookWithAuthorDto);

        boolean isBookAdded = booksRepository.addBook(book);

        if (!isBookAdded) {
            throw new BookNotAddedException("internal exception while adding new book");
        }

        AuthorDto authorDto = new AuthorDto();

        authorDto.setId(bookWithAuthorDto.getAuthorId());
        authorDto.setFirstName(bookWithAuthorDto.getFirstName());
        authorDto.setLastName(bookWithAuthorDto.getLastName());

        ResponseEntity<Boolean> authorResponse;

        try {
            authorResponse = apiClientService.callAddAuthor(authorDto);
            log.info("author service response: {}", authorResponse);
        } catch (Exception ex) {
            booksRepository.removeBook(book.getId());
            throw new AuthorServiceInvocationFailedException("exception happens during call to author service");
        }

        if (HttpStatus.OK != authorResponse.getStatusCode()) {
            booksRepository.removeBook(book.getId());
            throw new BookNotAddedException("internal exception while adding new book");
        }

        return authorResponse;
    }

    public BookDto updateBook(int id, BookDto bookDto) {
        return mapperService.mapToBookDto(
                booksRepository.updateBook(id, mapperService.mapFromBookDto(bookDto))
        );
    }
}
