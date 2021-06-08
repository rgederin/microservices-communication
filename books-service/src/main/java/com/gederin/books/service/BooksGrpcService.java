package com.gederin.books.service;

import com.gederin.books.exception.AuthorServiceInvocationFailedException;
import com.gederin.books.exception.BookNotAddedException;
import com.gederin.books.service.client.GrpcClientService;
import com.gederin.books.model.Book;
import com.gederin.books.repository.BooksRepository;
import com.gederin.books.service.mapper.MapperService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BooksGrpcService {

    private final BooksRepository booksRepository;
    private final GrpcClientService grpcClientService;
    private final MapperService mapperService;

    public List<com.proto.book.Book> getGrpcBooks() {
        return booksRepository.getBooks()
                .stream()
                .map(mapperService::mapToBookGrpc)
                .collect(Collectors.toList());
    }

    public com.proto.book.AddBookResponse addBook(com.proto.book.AddBookRequest addBookRequest) {
        Book book = mapperService.mapFromGrpcRequest(addBookRequest);
        boolean isBookAdded = booksRepository.addBook(book);

        if (!isBookAdded) {
            throw new BookNotAddedException("internal exception while adding new book");
        }

        boolean result;

        try {
            result = grpcClientService.addAuthor(mapperService.mapToAddAuthorRequest(addBookRequest));
        } catch (Exception ex) {
            booksRepository.removeBook(book.getId());
            throw new AuthorServiceInvocationFailedException("exception happens during call to author service");
        }

        if (!result) {
            booksRepository.removeBook(book.getId());
            throw new BookNotAddedException("internal exception while adding new book");
        }

        return com.proto.book.AddBookResponse.newBuilder().setAdded(result).build();
    }
}
