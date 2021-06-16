package com.gederin.books.v3.service.amqp;

import com.gederin.books.v3.model.Author;
import com.gederin.books.v3.service.BooksV3Service;

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.gederin.books.v3.config.AmqpConfig.AUTHORS_CREATED_QUEUE;
import static com.gederin.books.v3.config.AmqpConfig.AUTHOR_UPDATED_QUEUE;

@Service
@AllArgsConstructor
@Slf4j
public class AmqpListenerService {

    private final BooksV3Service booksService;

    @RabbitListener(queues = AUTHORS_CREATED_QUEUE)
    public void receiveMessageFromAuthorCreatedQueue(byte[] authorEvent) throws IOException {
        log.info("received author.created event");
        Author author = unpackAuthorEvent(authorEvent);

        booksService.addAuthor(author);
    }

    @RabbitListener(queues = AUTHOR_UPDATED_QUEUE)
    public void receiveMessageFromAuthorUpdatedQueue(byte[] authorEvent) throws IOException {
        Author author = unpackAuthorEvent(authorEvent);

        booksService.updateAuthor(author);
    }

    private Author unpackAuthorEvent(byte[] authorEvent) throws IOException {
        try (MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(authorEvent)) {
            return Author.builder()
                    .id(unpacker.unpackInt())
                    .firstName(unpacker.unpackString())
                    .lastName(unpacker.unpackString())
                    .build();
        }
    }
}
