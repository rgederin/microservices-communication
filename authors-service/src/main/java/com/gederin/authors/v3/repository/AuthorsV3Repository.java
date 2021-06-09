package com.gederin.authors.v3.repository;


import com.gederin.authors.exceptions.AuthorDatabaseException;
import com.gederin.authors.v3.model.Author;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

@Repository
public class AuthorsV3Repository {
    private final Map<Integer, Author> authors = new HashMap<>();

    @PostConstruct
    public void init() {
        authors.put(1, Author.builder().id(1).firstName("Joanne").lastName("Rowling").numberOfBooks(1).build());
        authors.put(2, Author.builder().id(1).firstName("John").lastName("Tolkien").numberOfBooks(1).build());
    }

    public void addAuthor(Author author) {
        if (new Random().nextInt(5) == 4) {
            throw new AuthorDatabaseException("failed to save the author");
        }

        if (authors.containsKey(author.getId())) {
            authors.compute(author.getId(), (key, value) -> {
                        value.setNumberOfBooks(value.getNumberOfBooks() + 1);
                        return value;
                    }
            );
        } else {
            author.setNumberOfBooks(1);
            authors.put(author.getId(), author);
        }
    }
}
