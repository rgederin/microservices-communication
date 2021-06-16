package com.gederin.books.v3.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {
    private int id;
    private String firstName;
    private String lastName;
    private int numberOfBooks;
}
