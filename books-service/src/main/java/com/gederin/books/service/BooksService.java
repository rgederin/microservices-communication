package com.gederin.books.service;

import com.gederin.books.dto.BookDto;
import com.gederin.books.model.Book;
import com.gederin.books.repository.BooksRepository;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;

    public Collection<BookDto> getBooks() {
        return booksRepository.getBooks()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BookDto addBook(int id, BookDto bookDto) {
        return mapToDto(booksRepository.addBook(id, mapFromDto(bookDto)));
    }

    public BookDto updateBook(int id, BookDto bookDto) {
        return mapToDto(booksRepository.updateBook(id, mapFromDto(bookDto)));
    }

    private Book mapFromDto(BookDto bookDto) {
        Book book = new Book();

        book.setTitle(bookDto.getTitle());
        book.setPages(bookDto.getPages());

        return book;
    }

    private BookDto mapToDto(Book book) {
        return new BookDto(book.getId(),
                book.getTitle(),
                book.getPages(),
                book.getAuthorId());
    }
}
