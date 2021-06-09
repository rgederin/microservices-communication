package com.gederin.books.v3.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookWithAuthor {
    private int id;
    private String title;
    private int pages;
    private int authorId;
    private String firstName;
    private String lastName;
}
