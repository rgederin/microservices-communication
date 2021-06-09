package com.gederin.books.v3.repository;

import com.gederin.books.exception.BookAlreadyExistsException;
import com.gederin.books.v3.model.BookWithAuthor;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

@Repository
public class BooksV3Repository {
    private final Map<Integer, BookWithAuthor> books = new HashMap<>();

    @PostConstruct
    public void init() {
        books.put(1, BookWithAuthor.builder().id(1).title("Harry Potter and the Chamber of Secrets")
                .pages(674).authorId(1).firstName("Joanne").lastName("Rowling").build());

        books.put(2, BookWithAuthor.builder().id(2).title("The Lord of the Ringss")
                .pages(893).authorId(2).firstName("John").lastName("Tolkien").build());
    }

    public boolean addBook(BookWithAuthor bookWithAuthor) {
        if (books.containsKey(bookWithAuthor.getId())) {
            throw new BookAlreadyExistsException("book with given id already exists");
        }

        books.put(bookWithAuthor.getId(), bookWithAuthor);
        return true;
    }


    public Collection<BookWithAuthor> getBooks() {
        return books.values();
    }

}
