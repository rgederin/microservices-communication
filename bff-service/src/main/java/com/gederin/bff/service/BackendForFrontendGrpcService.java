package com.gederin.bff.service;

import com.gederin.bff.dto.AuthorsListDto;
import com.gederin.bff.dto.BooksListDto;
import com.gederin.bff.dto.DashboardDto;
import com.gederin.bff.grpc.GrpcClientService;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BackendForFrontendGrpcService {

    private final GrpcClientService grpcClient;

    public DashboardDto getBooksAndAuthors() throws ExecutionException, InterruptedException {
        CompletableFuture<AuthorsListDto> authors = grpcClient.getAuthors();
        CompletableFuture<BooksListDto> books = grpcClient.getBooks();
        CompletableFuture.allOf(authors, books).join();

        DashboardDto dashboardDto = new DashboardDto();

        dashboardDto.setAuthors(authors.get().getAuthors());
        dashboardDto.setBooks(books.get().getBooks());

        return dashboardDto;
    }
}
