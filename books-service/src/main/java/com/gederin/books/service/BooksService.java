package com.gederin.books.service;

import com.gederin.books.dto.BookDto;
import com.gederin.books.dto.BookListDto;
import com.gederin.books.repository.BooksRepository;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;
    private final DtoMapperService dtoMapperService;

    public BookListDto getBooks() {
        BookListDto bookListDto = new BookListDto();

        bookListDto.setBooks(booksRepository.getBooks()
                .stream()
                .map(dtoMapperService::mapToBookDto)
                .collect(Collectors.toList()));

        return bookListDto;
    }

    public BookDto addBook(int id, BookDto bookDto) {
        return dtoMapperService.mapToBookDto(
                booksRepository.addBook(id, dtoMapperService.mapFromBookDto(bookDto))
        );
    }

    public BookDto updateBook(int id, BookDto bookDto) {
        return dtoMapperService.mapToBookDto(
                booksRepository.updateBook(id, dtoMapperService.mapFromBookDto(bookDto))
        );
    }
}
