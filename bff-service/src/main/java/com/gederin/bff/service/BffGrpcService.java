package com.gederin.bff.service;

import com.gederin.bff.dto.AuthorDto;
import com.gederin.bff.dto.AuthorsListDto;
import com.gederin.bff.dto.BookDto;
import com.gederin.bff.dto.BookWithAuthorDto;
import com.gederin.bff.dto.BooksListDto;
import com.gederin.bff.dto.DashboardDto;
import com.gederin.bff.service.client.GrpcClientService;
import com.gederin.bff.service.mapper.MapperService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BffGrpcService {

    private final GrpcClientService grpcClient;

    private final MapperService mapperService;

    public DashboardDto getBooksAndAuthors() throws ExecutionException, InterruptedException {
        CompletableFuture<AuthorsListDto> authors = grpcClient.getAuthors();
        CompletableFuture<BooksListDto> books = grpcClient.getBooks();
        CompletableFuture.allOf(authors, books).join();

        DashboardDto dashboardDto = new DashboardDto();

        dashboardDto.setAuthors(authors.get().getAuthors());
        dashboardDto.setBooks(books.get().getBooks());

        return dashboardDto;
    }

    public List<BookWithAuthorDto> getBooksWithAuthors() throws ExecutionException, InterruptedException {
        CompletableFuture<AuthorsListDto> authors = grpcClient.getAuthors();
        CompletableFuture<BooksListDto> books = grpcClient.getBooks();
        CompletableFuture.allOf(authors, books).join();

        List<AuthorDto> authorsList = authors.get().getAuthors();
        List<BookDto> booksList = books.get().getBooks();

        return booksList.stream()
                .map(book -> mapperService.mergeAuthorInBook(book, authorsList))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Boolean> addBook(BookWithAuthorDto bookWithAuthorDto){
        return ResponseEntity.ok(grpcClient.addBook(mapperService.mapFromDto(bookWithAuthorDto)));
    }

    public ResponseEntity<Boolean> updateAuthor(AuthorDto authorDto) {
        return ResponseEntity.ok(grpcClient.updateAuthor(mapperService.mapToAuthorGrpc(authorDto)));
    }
}
