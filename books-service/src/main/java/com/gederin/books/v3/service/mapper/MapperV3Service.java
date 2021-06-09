package com.gederin.books.v3.service.mapper;

import com.gederin.books.v3.dto.BookWithAuthorDto;
import com.gederin.books.v3.model.Author;
import com.gederin.books.v3.model.Book;

import org.springframework.stereotype.Service;

@Service
public class MapperV3Service {
    public Author mapBookWithAuthorDtoToAuthorModel(BookWithAuthorDto bookWithAuthorDto) {
        return Author.builder()
                .id(bookWithAuthorDto.getAuthorId())
                .firstName(bookWithAuthorDto.getFirstName())
                .lastName(bookWithAuthorDto.getLastName())
                .build();
    }


    public Book mapBookWithAuthorDtoToBookModel(BookWithAuthorDto bookWithAuthorDto) {
        return Book.builder()
                .id(bookWithAuthorDto.getId())
                .title(bookWithAuthorDto.getTitle())
                .pages(bookWithAuthorDto.getPages())
                .authorId(bookWithAuthorDto.getAuthorId())
                .build();
    }
}
