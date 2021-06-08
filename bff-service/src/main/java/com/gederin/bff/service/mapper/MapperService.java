package com.gederin.bff.service.mapper;

import com.gederin.bff.dto.AuthorDto;
import com.gederin.bff.dto.BookDto;
import com.gederin.bff.dto.BookWithAuthorDto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapperService {

    public AuthorDto mapFromGrpc(com.proto.author.Author grpcAuthor) {
        AuthorDto authorDto = new AuthorDto();

        authorDto.setId(grpcAuthor.getId());
        authorDto.setFirstName(grpcAuthor.getFirstName());
        authorDto.setLastName(grpcAuthor.getLastName());
        authorDto.setNumberOfBooks(grpcAuthor.getNumberOfBooks());

        return authorDto;
    }

    public BookDto mapFromGrpc(com.proto.book.Book grpcBook) {
        BookDto bookDto = new BookDto();

        bookDto.setId(grpcBook.getId());
        bookDto.setTitle(grpcBook.getTitle());
        bookDto.setPages(grpcBook.getPages());
        bookDto.setAuthorId(grpcBook.getAuthorId());

        return bookDto;
    }

    public com.proto.book.AddBookRequest mapFromDto(BookWithAuthorDto bookWithAuthorDto) {
        return com.proto.book.AddBookRequest.newBuilder()
                .setId(bookWithAuthorDto.getId())
                .setTitle(bookWithAuthorDto.getTitle())
                .setPages(bookWithAuthorDto.getPages())
                .setAuthorId(bookWithAuthorDto.getAuthorId())
                .setFirstName(bookWithAuthorDto.getFirstName())
                .setLastName(bookWithAuthorDto.getLastName())
                .build();
    }

    public BookWithAuthorDto mergeAuthorInBook(BookDto book, List<AuthorDto> authors) {
        BookWithAuthorDto bookWithAuthorDto = new BookWithAuthorDto();

        bookWithAuthorDto.setId(book.getId());
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

    public com.proto.author.UpdateAuthorRequest mapToAuthorGrpc(AuthorDto authorDto) {
        return com.proto.author.UpdateAuthorRequest.newBuilder()
                .setId(authorDto.getId())
                .setFirstName(authorDto.getFirstName())
                .setLastName(authorDto.getLastName())
                .build();
    }
}
