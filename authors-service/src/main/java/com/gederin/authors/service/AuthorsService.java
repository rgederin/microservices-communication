package com.gederin.authors.service;

import com.gederin.authors.dto.AuthorDto;
import com.gederin.authors.dto.AuthorsListDto;
import com.gederin.authors.model.Author;
import com.gederin.authors.repository.AuthorsRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorsService {
    private final AuthorsRepository authorsRepository;
    private final MapperService mapperService;

    public AuthorsListDto getAuthors() {
        AuthorsListDto authorsListDto = new AuthorsListDto();

        authorsListDto.setAuthors(authorsRepository.getAuthors()
                .stream()
                .map(mapperService::mapToAuthorDto)
                .collect(Collectors.toList()));

        return authorsListDto;
    }

    public List<com.proto.author.Author> getGrpcAuthors() {
        return authorsRepository.getAuthors()
                .stream()
                .map(mapperService::mapToGrpc)
                .collect(Collectors.toList());
    }

    public AuthorDto updateAuthor(int id, AuthorDto authorDto) {
        return mapperService.mapToAuthorDto(
                authorsRepository.updateAuthor(id, mapperService.mapFromAuthorDto(authorDto))
        );
    }

    public boolean updateAuthor(com.proto.author.UpdateAuthorRequest updateAuthorRequest) {
        Author author = authorsRepository.updateAuthor(updateAuthorRequest.getId(), mapperService.mapFromGrpc(updateAuthorRequest));
        return null != author;
    }

    public boolean addAuthor(AuthorDto authorDto) {
        return authorsRepository.addAuthor(mapperService.mapFromAuthorDto(authorDto));
    }

    public boolean addAuthor(com.proto.author.AddAuthorRequest addAuthorRequest) {
        return authorsRepository.addAuthor(mapperService.mapFromGrpc(addAuthorRequest));
    }
}
