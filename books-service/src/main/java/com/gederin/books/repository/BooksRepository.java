package com.gederin.books.repository;

import com.gederin.books.exception.BookNotFoundException;
import com.gederin.books.model.Book;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

@Component
public class BooksRepository {
    private final Map<Integer, Book> books = new HashMap<>();

    @PostConstruct
    public void init() {
        books.put(1, new Book(1, "Semiosis: A Novel", 326, 1));
        books.put(2, new Book(2, "The Loosening Skin", 132, 1));
        books.put(3, new Book(3, "Ninefox Gambit", 384, 2));
        books.put(4, new Book(4, "Raven Stratagem", 400, 3));
        books.put(5, new Book(5, "Revenant Gun", 466, 3));
    }

    public Collection<Book> getBooks() {
        return books.values();
    }

    public Book addBook(int id, Book book) {
        book.setId(id);
        books.put(id, book);

        return book;
    }

    public Book updateBook(int id, Book book) {
        book.setId(id);

        Book updated = books.computeIfPresent(id, (key, value) -> book);

        if (updated == null) {
            throw new BookNotFoundException("could not update not existed book");
        }

        return updated;
    }
}
