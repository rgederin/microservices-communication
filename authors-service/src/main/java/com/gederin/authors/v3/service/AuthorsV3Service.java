package com.gederin.authors.v3.service;

import com.gederin.authors.v3.model.Author;
import com.gederin.authors.v3.repository.AuthorsV3Repository;
import com.gederin.authors.v3.service.amqp.AmqpSenderService;

import org.springframework.stereotype.Service;

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
}
