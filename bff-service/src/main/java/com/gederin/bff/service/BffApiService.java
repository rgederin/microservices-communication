package com.gederin.bff.service;

import com.gederin.bff.dto.AuthorDto;
import com.gederin.bff.dto.AuthorsListDto;
import com.gederin.bff.dto.BookDto;
import com.gederin.bff.dto.BookWithAuthorDto;
import com.gederin.bff.dto.BooksListDto;
import com.gederin.bff.dto.DashboardDto;
import com.gederin.bff.dto.HealthDto;
import com.gederin.bff.service.client.ApiClientService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class BackendForFrontendService {

    private final ApiClientService apiClientService;

    private final MapperService mapperService;

    public HealthDto health() throws ExecutionException, InterruptedException {
        CompletableFuture<String> authorsHealth = apiClientService.callAuthorsHealth();
        CompletableFuture<String> booksHealth = apiClientService.callBooksHealth();
        CompletableFuture.allOf(authorsHealth, booksHealth).join();

        HealthDto healthDto = new HealthDto();

        healthDto.setBackendForFrontendServiceHealth("bff service is healthy");
        healthDto.setAuthorsServiceHealth(authorsHealth.get());
        healthDto.setBooksServiceHealth(booksHealth.get());

        return healthDto;
    }

    public DashboardDto getBooksAndAuthors() throws ExecutionException, InterruptedException {
        CompletableFuture<AuthorsListDto> authors = apiClientService.callAuthors();
        CompletableFuture<BooksListDto> books = apiClientService.callBooks();
        CompletableFuture.allOf(authors, books).join();

        DashboardDto dashboardDto = new DashboardDto();

        dashboardDto.setAuthors(authors.get().getAuthors());
        dashboardDto.setBooks(books.get().getBooks());

        return dashboardDto;
    }

    public List<BookWithAuthorDto> getBooksWithAuthors() throws ExecutionException, InterruptedException {
        CompletableFuture<AuthorsListDto> authors = apiClientService.callAuthors();
        CompletableFuture<BooksListDto> books = apiClientService.callBooks();
        CompletableFuture.allOf(authors, books).join();

        List<AuthorDto> authorsList = authors.get().getAuthors();
        List<BookDto> booksList = books.get().getBooks();

        return booksList.stream()
                .map(book -> mapperService.mergeAuthorInBook(book, authorsList))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Boolean> addBook( BookWithAuthorDto bookWithAuthorDto) {
        ResponseEntity<Boolean> result = apiClientService.callAddBook(bookWithAuthorDto);

        log.info(String.valueOf(result));

        return result;
    }
}
