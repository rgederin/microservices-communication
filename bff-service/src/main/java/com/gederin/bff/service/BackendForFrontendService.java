package com.gederin.bff.service;

import com.gederin.bff.dto.AuthorDto;
import com.gederin.bff.dto.AuthorsListDto;
import com.gederin.bff.dto.BookDto;
import com.gederin.bff.dto.BookWithAuthorDto;
import com.gederin.bff.dto.BooksListDto;
import com.gederin.bff.dto.DashboardDto;
import com.gederin.bff.dto.HealthDto;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BackendForFrontendService {

    private final ApiClientService apiClientService;

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
                .map(book -> mergeAuthorInBook(book, authorsList))
                .collect(Collectors.toList());
    }

    private BookWithAuthorDto mergeAuthorInBook(BookDto book, List<AuthorDto> authors) {
        BookWithAuthorDto bookWithAuthorDto = new BookWithAuthorDto();

        bookWithAuthorDto.setTitle(book.getTitle());
        bookWithAuthorDto.setPages(book.getPages());
        bookWithAuthorDto.setAuthorId(book.getAuthorId());

        AuthorDto authorDto = authors.stream()
                .filter(author -> author.getId() == book.getAuthorId())
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        bookWithAuthorDto.setFirstName(authorDto.getFirstName());
        bookWithAuthorDto.setLastName(authorDto.getLastName());

        return bookWithAuthorDto;
    }
}
