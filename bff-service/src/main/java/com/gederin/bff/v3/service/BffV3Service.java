package com.gederin.bff.v3.service;

import com.gederin.bff.dto.AuthorDto;
import com.gederin.bff.dto.BookWithAuthorDto;
import com.gederin.bff.exception.AuthorServiceException;
import com.gederin.bff.exception.BookServiceException;
import com.gederin.bff.v3.dto.AuthorsListDto;
import com.gederin.bff.v3.dto.BooksListDto;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class BffV3Service {

    private final ApiClientV3Service apiClientService;

    public BooksListDto getBooks() {
        try {
            log.info("getting all books from books v3 service");
            return apiClientService.getBooksApiCall();
        } catch (Exception ex) {
            throw new BookServiceException("error while calling book v3 service + " + ex);
        }
    }

    public ResponseEntity<Boolean> addBook(BookWithAuthorDto bookWithAuthorDto) {
        try {
            log.info("adding book using v3 api");
            return apiClientService.addBookApiCall(bookWithAuthorDto);
        } catch (Exception ex) {
            throw new BookServiceException("error while calling book v3 service + " + ex);
        }
    }

    public AuthorsListDto getAuthors() {
        try {
            log.info("getting all authors from authors v3 service");
            return apiClientService.getAuthorsApiCall();
        } catch (Exception ex) {
            throw new AuthorServiceException("error while calling authors v3 service + " + ex);
        }
    }

    public ResponseEntity<Boolean> updateAuthor(AuthorDto authorDto) {
        try {
            log.info("updating author using authors v3 service");
            return apiClientService.getAuthorsApiCall(authorDto);
        } catch (Exception ex) {
            throw new AuthorServiceException("error while calling authors v3 service + " + ex);
        }
    }
}
