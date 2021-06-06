package com.gederin.bff.service;

import com.gederin.bff.dto.AuthorDto;
import com.gederin.bff.dto.BookDto;

import org.springframework.stereotype.Service;

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
}
