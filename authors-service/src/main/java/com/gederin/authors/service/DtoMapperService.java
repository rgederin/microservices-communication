package com.gederin.authors.service;

import com.gederin.authors.dto.AuthorDto;
import com.gederin.authors.model.Author;

import org.springframework.stereotype.Service;

@Service
public class DtoMapperService {

    public Author mapFromAuthorDto(AuthorDto authorDto) {
        Author author = new Author();

        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());

        return author;
    }

    public AuthorDto mapToAuthorDto(Author author) {
        return new AuthorDto(author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getNumberOfBooks());
    }
}
