package com.gederin.books.service.mapper;

import com.gederin.books.dto.BookDto;
import com.gederin.books.dto.BookWithAuthorDto;
import com.gederin.books.model.Book;

import org.springframework.stereotype.Service;

@Service
public class MapperService {
    public Book mapFromBookDto(BookDto bookDto) {
        Book book = new Book();

        book.setTitle(bookDto.getTitle());
        book.setPages(bookDto.getPages());

        return book;
    }

    public BookDto mapToBookDto(Book book) {
        return new BookDto(book.getId(),
                book.getTitle(),
                book.getPages(),
                book.getAuthorId());
    }

    public Book mapFromGrpcRequest(com.proto.book.AddBookRequest addBookRequest) {
        Book book = new Book();

        book.setId(addBookRequest.getId());
        book.setTitle(addBookRequest.getTitle());
        book.setPages(addBookRequest.getPages());
        book.setAuthorId(addBookRequest.getAuthorId());

        return book;
    }

    public com.proto.author.AddAuthorRequest mapToAddAuthorRequest(com.proto.book.AddBookRequest addBookRequest) {
        return com.proto.author.AddAuthorRequest.newBuilder()
                .setId(addBookRequest.getAuthorId())
                .setFirstName(addBookRequest.getFirstName())
                .setLastName(addBookRequest.getLastName())
                .build();
    }

    public Book mapFromBookWithAuthorDto(BookWithAuthorDto bookWithAuthorDto) {
        return new Book(bookWithAuthorDto.getId(),
                bookWithAuthorDto.getTitle(),
                bookWithAuthorDto.getPages(),
                bookWithAuthorDto.getAuthorId()
        );
    }

    public com.proto.book.Book mapToBookGrpc (Book book){
        return com.proto.book.Book.newBuilder()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setPages(book.getPages())
                .setAuthorId(book.getAuthorId())
                .build();
    }
}
