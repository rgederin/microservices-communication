package com.gederin.authors.service;

import com.gederin.authors.dto.AuthorDto;
import com.gederin.authors.dto.AuthorsListDto;
import com.gederin.authors.repository.AuthorsRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorsService {
    private final AuthorsRepository authorsRepository;
    private final DtoMapperService dtoMapperService;

    public AuthorsListDto getAuthors() {
        AuthorsListDto authorsListDto = new AuthorsListDto();

        authorsListDto.setAuthors(authorsRepository.getAuthors()
                .stream()
                .map(dtoMapperService::mapToAuthorDto)
                .collect(Collectors.toList()));

        return authorsListDto;
    }

    public List<com.proto.author.Author> getGrpcAuthors() {
       return authorsRepository.getAuthors().stream().map(author -> com.proto.author.Author.newBuilder()
                .setId(author.getId())
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName())
                .setNumberOfBooks(author.getNumberOfBooks())
                .build())
                .collect(Collectors.toList());
    }

    public AuthorDto updateAuthor(int id, AuthorDto authorDto) {
        return dtoMapperService.mapToAuthorDto(
                authorsRepository.updateAuthor(id, dtoMapperService.mapFromAuthorDto(authorDto))
        );
    }

    public boolean addAuthor(AuthorDto authorDto) {
        return authorsRepository.addAuthor(dtoMapperService.mapFromAuthorDto(authorDto));
    }
}
