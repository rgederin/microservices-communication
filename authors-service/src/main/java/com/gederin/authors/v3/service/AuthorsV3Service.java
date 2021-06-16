package com.gederin.authors.v3.service;

import com.gederin.authors.v3.dto.AuthorDto;
import com.gederin.authors.v3.dto.AuthorsListDto;
import com.gederin.authors.v3.exceptions.AuthorDatabaseException;
import com.gederin.authors.v3.model.Author;
import com.gederin.authors.v3.repository.AuthorsV3Repository;
import com.gederin.authors.v3.service.amqp.AmqpSenderService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthorsV3Service {

    private final AuthorsV3Repository authorsRepository;
    private final AmqpSenderService senderService;

    public boolean addAuthor(Author author) {
        try {
            authorsRepository.addAuthor(author);

            log.info("information about author: {} was successfully added in authors database", author);
            log.info("sending author.created event...");

            senderService.sendAuthorCreatedEvent(author);
            return true;
        } catch (Exception ex) {
            log.error("Exception while saving author info: " + ex);
            return false;
        }
    }

    public boolean updateAuthor(Author author) {
        try {
            authorsRepository.updateAuthor(author);

            log.info("information about author: {} was successfully updated in authors database", author);
            log.info("sending author.updated event...");

            senderService.sendAuthorUpdatedEvent(author);
            return true;
        } catch (Exception ex) {
            log.error("Exception while updating author info: " + ex);
            return false;
        }
    }

    public AuthorsListDto getAuthors() {
        try {
            List<AuthorDto> authorDtos = authorsRepository.getAuthors()
                    .stream()
                    .map(author -> AuthorDto.builder().id(author.getId())
                            .firstName(author.getFirstName())
                            .lastName(author.getLastName())
                            .numberOfBooks(author.getNumberOfBooks()).build())
                    .collect(Collectors.toList());

            log.info("getting all authors from data resposoty: {}", authorDtos);

            return AuthorsListDto.builder().authors(authorDtos).build();
        } catch (Exception ex) {
            log.error("Exception while getting all authors: " + ex);
            throw new AuthorDatabaseException(ex.getMessage());
        }
    }
}
