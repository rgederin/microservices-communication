package com.gederin.books.v3.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Book {
    private int id;
    private String title;
    private int pages;
    private int authorId;
}
