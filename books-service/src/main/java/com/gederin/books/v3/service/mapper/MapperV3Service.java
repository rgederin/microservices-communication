package com.gederin.books.v3.service.mapper;

import com.gederin.books.v3.dto.BookWithAuthorDto;
import com.gederin.books.v3.model.BookWithAuthor;

import org.springframework.stereotype.Service;

@Service
public class MapperV3Service {
    public BookWithAuthorDto mapBookWithAuthorToDto(BookWithAuthor bookWithAuthor) {
        return BookWithAuthorDto.builder()
                .id(bookWithAuthor.getId())
                .title(bookWithAuthor.getTitle())
                .pages(bookWithAuthor.getPages())
                .authorId(bookWithAuthor.getAuthorId())
                .firstName(bookWithAuthor.getFirstName())
                .lastName(bookWithAuthor.getLastName())
                .build();
    }

    public BookWithAuthor mapBookWithAuthorDtoToModel(BookWithAuthorDto bookWithAuthorDto) {
        return BookWithAuthor.builder()
                .id(bookWithAuthorDto.getId())
                .title(bookWithAuthorDto.getTitle())
                .pages(bookWithAuthorDto.getPages())
                .authorId(bookWithAuthorDto.getAuthorId())
                .firstName(bookWithAuthorDto.getFirstName())
                .lastName(bookWithAuthorDto.getLastName())
                .build();
    }
}
