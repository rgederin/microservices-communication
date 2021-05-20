package com.gederin.authors.repository;

import com.gederin.authors.exceptions.AuthorDatabaseException;
import com.gederin.authors.exceptions.AuthorNotFoundException;
import com.gederin.authors.model.Author;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.annotation.PostConstruct;

@Component
public class AuthorsRepository {
    private final Map<Integer, Author> authors = new HashMap<>();

    @PostConstruct
    public void init() {
        authors.put(1, new Author(1, "Loreth Anne", "White", 2));
        authors.put(2, new Author(2, "Lisa", "Regan", 1));
        authors.put(3, new Author(3, "Ty", "Patterson", 2));
    }

    public Collection<Author> getAuthors() {
        return authors.values();
    }

    public Author updateAuthor(int id, Author author) {
        Author result = authors.computeIfPresent(id, (key, value) -> {
                    value.setFirstName(author.getFirstName());
                    value.setLastName(author.getLastName());
                    return value;
                }
        );

        if (Objects.isNull(result)) {
            throw new AuthorNotFoundException("author with given id not exists");
        }

        return result;
    }

    public Author addAuthor(int id, Author author) {
        if (new Random().nextInt(3) == 2) {
            throw new AuthorDatabaseException("failed to save the author");
        }

        Author result = authors.computeIfPresent(id, (key, value) -> {
                    value.setId(value.getId());
                    value.setNumberOfBooks(value.getNumberOfBooks() + 1);
                    return value;
                }
        );

        if (result == null) {
            author.setId(id);
            author.setNumberOfBooks(1);
            authors.put(id, author);
            result = author;
        }

        return result;
    }

    public Author findById(int id) {
        return authors.get(id);
    }
}
