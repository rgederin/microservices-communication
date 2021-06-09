package com.gederin.books.v3.service;

import com.gederin.books.v3.model.BookWithAuthor;
import com.gederin.books.v3.repository.BooksV3Repository;
import com.gederin.books.v3.service.mapper.MapperV3Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BooksV3Service {
    private final AmqpSenderService amqpSender;
    private final BooksV3Repository booksRepository;
    private final MapperV3Service mapper;

    public boolean addBook(BookWithAuthor bookWithAuthor) throws IOException {
        boolean added = booksRepository.addBook(bookWithAuthor);

        amqpSender.sendBookCreatedEvent(bookWithAuthor);

        return added;
    }

    public Collection<BookWithAuthor> getBooks() {
        return booksRepository.getBooks();
    }
}
