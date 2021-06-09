package com.gederin.authors.v3.service.queue;

import com.gederin.authors.v3.model.Author;
import com.gederin.authors.v3.service.AuthorsV3Service;

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.gederin.authors.v3.AmqpConfig.BOOK_QUEUE;

@Service
@AllArgsConstructor
@Slf4j
public class AmqpListenerService {

    private final AuthorsV3Service authorsService;

    @RabbitListener(queues = BOOK_QUEUE)
    public void receiveMessageFromBookQueue(byte[] bookCreatedEvent) throws IOException {
        Author author = unpackBookCreatedEvent(bookCreatedEvent);

        authorsService.addAuthor(author);
    }

    private Author unpackBookCreatedEvent(byte[] bookCreatedEvent) throws IOException {
        try (MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bookCreatedEvent);) {
            return Author.builder()
                    .id(unpacker.unpackInt())
                    .firstName(unpacker.unpackString())
                    .lastName(unpacker.unpackString())
                    .build();
        }
    }
}
