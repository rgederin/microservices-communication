package com.gederin.books.v3.service;

import com.gederin.books.v3.dto.BookWithAuthorDto;
import com.gederin.books.v3.model.Author;
import com.gederin.books.v3.model.Book;
import com.gederin.books.v3.repository.BooksV3Repository;
import com.gederin.books.v3.service.amqp.AmqpSenderService;
import com.gederin.books.v3.service.mapper.MapperV3Service;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class BooksV3Service {
    private final AmqpSenderService amqpSender;
    private final BooksV3Repository booksRepository;
    private final MapperV3Service mapper;

    public boolean addBook(BookWithAuthorDto bookWithAuthorDto) {
        Book book = mapper.mapBookWithAuthorDtoToBookModel(bookWithAuthorDto);
        try {
            booksRepository.addBook(book);

            log.info("information about book: {} was successfully added in books database", book);
            log.info("sending book.created event...");

            amqpSender.sendBookCreatedEvent(mapper.mapBookWithAuthorDtoToAuthorModel(bookWithAuthorDto));

            return true;
        } catch (Exception ex) {
            log.error("Exception while saving book info: " + ex);

            return false;
        }
    }

    public boolean addAuthor(Author author) {
        try {
            booksRepository.addAuthor(author);
            log.info("information about author: {} was successfully added in authors database", author);
            return true;
        } catch (Exception ex) {
            log.error("Exception while saving author info: " + ex);

            return false;
        }
    }

    public boolean updateAuthor(Author author) {
        try {
            booksRepository.updateAuthor(author);
            log.info("information about author: {} was successfully updated in authors database", author);
            return true;
        } catch (Exception ex) {
            log.error("Exception while updating author info: " + ex);

            return false;
        }
    }

    public List<BookWithAuthorDto> getBooksWithAuthors() {
        Collection<Book> books = booksRepository.getBooks();
        Collection<Author> authors = booksRepository.getAuthors();

        return books.stream()
                .map(book -> {
                    Author bookAuthor = authors.stream()
                            .filter(author -> author.getId() == book.getAuthorId())
                            .findFirst()
                            .orElseThrow(IllegalArgumentException::new);

                    return BookWithAuthorDto.builder()
                            .id(book.getId())
                            .title(book.getTitle())
                            .pages(book.getPages())
                            .authorId(book.getAuthorId())
                            .firstName(bookAuthor.getFirstName())
                            .lastName(bookAuthor.getLastName())
                            .build();
                }).collect(Collectors.toList());
    }
}
