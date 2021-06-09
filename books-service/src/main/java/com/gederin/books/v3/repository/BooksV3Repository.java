package com.gederin.books.v3.repository;

import com.gederin.books.v3.exception.BookAlreadyExistsException;
import com.gederin.books.v3.model.Author;
import com.gederin.books.v3.model.Book;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

@Repository
public class BooksV3Repository {
    private final Map<Integer, Book> books = new HashMap<>();

    private final Map<Integer, Author> authors = new HashMap<>();

    @PostConstruct
    public void init() {
        books.put(1, Book.builder().id(1).title("Harry Potter and the Chamber of Secrets").pages(674).authorId(1).build());
        books.put(2, Book.builder().id(2).title("The Lord of the Rings").pages(893).authorId(2).build());

        authors.put(1, Author.builder().id(1).firstName("Joanne").lastName("Rowling").build());
        authors.put(2, Author.builder().id(2).firstName("John").lastName("Tolkien").build());
    }

    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new BookAlreadyExistsException("book with given id already exists");
        }

        books.put(book.getId(), book);
    }

    public void addAuthor(Author author) {
        authors.putIfAbsent(author.getId(), author);
    }

    public Collection<Book> getBooks() {
        return books.values();
    }

    public Collection<Author> getAuthors() {
        return authors.values();
    }
}
