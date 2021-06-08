package com.gederin.authors.service;

import com.gederin.authors.dto.AuthorDto;
import com.gederin.authors.model.Author;

import org.springframework.stereotype.Service;

@Service
public class MapperService {

    public Author mapFromAuthorDto(AuthorDto authorDto) {
        Author author = new Author();

        author.setId(authorDto.getId());
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

    public Author mapFromGrpc(com.proto.author.AddAuthorRequest addAuthorRequest) {
        Author author = new Author();

        author.setId(addAuthorRequest.getId());
        author.setFirstName(addAuthorRequest.getFirstName());
        author.setLastName(addAuthorRequest.getLastName());

        return author;
    }

    public com.proto.author.Author mapToGrpc(Author author) {
        return com.proto.author.Author.newBuilder()
                .setId(author.getId())
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName())
                .setNumberOfBooks(author.getNumberOfBooks())
                .build();
    }

    public Author mapFromGrpc(com.proto.author.UpdateAuthorRequest updateAuthorRequest) {
        Author author = new Author();

        author.setFirstName(updateAuthorRequest.getFirstName());
        author.setLastName(updateAuthorRequest.getLastName());

        return author;
    }
}
