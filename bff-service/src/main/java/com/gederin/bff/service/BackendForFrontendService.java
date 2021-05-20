package com.gederin.bff.service;

import com.gederin.bff.dto.AuthorDto;
import com.gederin.bff.dto.BookDto;
import com.gederin.bff.dto.DashboardDto;
import com.gederin.bff.dto.HealthDto;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        CompletableFuture<List<AuthorDto>> authors = apiClientService.callAuthors();
        CompletableFuture<List<BookDto>> books = apiClientService.callBooks();
        CompletableFuture.allOf(authors, books).join();

        DashboardDto dashboardDto = new DashboardDto();

        dashboardDto.setAuthors(authors.get());
        dashboardDto.setBooks(books.get());

        return dashboardDto;
    }
}
