package com.gederin.authors.service;

import com.gederin.authors.dto.AuthorDto;
import com.gederin.authors.model.Author;
import com.gederin.authors.repository.AuthorsRepository;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorsService {
    private final AuthorsRepository authorsRepository;

    public Collection<AuthorDto> getAuthors() {
        return authorsRepository.getAuthors()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public AuthorDto updateAuthor(int id, AuthorDto authorDto) {
        return mapToDto(authorsRepository.updateAuthor(id, mapFromDto(authorDto)));
    }

    public AuthorDto addAuthor(int id, AuthorDto authorDto) {
        return mapToDto(authorsRepository.addAuthor(id, mapFromDto(authorDto)));
    }

    private Author mapFromDto(AuthorDto authorDto) {
        Author author = new Author();

        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());

        return author;
    }

    private AuthorDto mapToDto(Author author) {
        return new AuthorDto(author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getNumberOfBooks());
    }
}
