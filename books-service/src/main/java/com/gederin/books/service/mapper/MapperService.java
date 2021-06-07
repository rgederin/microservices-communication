package com.gederin.books.service;

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

    public Book mapFromBookWithAuthorDto(BookWithAuthorDto bookWithAuthorDto) {
        return new Book(bookWithAuthorDto.getId(),
                bookWithAuthorDto.getTitle(),
                bookWithAuthorDto.getPages(),
                bookWithAuthorDto.getAuthorId()
        );
    }
}
