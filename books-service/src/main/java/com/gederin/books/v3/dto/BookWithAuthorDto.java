package com.gederin.books.v3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookWithAuthorDto {
    private int id;
    private String title;
    private int pages;
    private int authorId;
    private String firstName;
    private String lastName;
}
