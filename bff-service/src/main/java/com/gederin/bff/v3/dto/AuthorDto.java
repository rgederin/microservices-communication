package com.gederin.bff.v3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {
    private int id;
    private String firstName;
    private String lastName;
    private int numberOfBooks;
}
