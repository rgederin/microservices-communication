package com.gederin.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookWithAuthorDto {
    private int id;
    private String title;
    private int pages;
    private int authorId;
    private String firstName;
    private String lastName;
}
