package com.gederin.authors.v3.repository;


import com.gederin.authors.v3.exceptions.AuthorNotFoundException;
import com.gederin.authors.v3.model.Author;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public void updateAuthor(Author author) {
        if (!authors.containsKey(author.getId())) {
            throw new AuthorNotFoundException("failed to update not existed author with id: " + author.getId());
        }

        authors.compute(author.getId(), (key, value) -> {
                    value.setFirstName(author.getFirstName());
                    value.setLastName(author.getLastName());
                    return value;
                }
        );
    }

    public Collection<Author> getAuthors(){
        return authors.values();
    }
}
