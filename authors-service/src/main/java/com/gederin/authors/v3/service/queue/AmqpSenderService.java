package com.gederin.authors.v3.service.queue;


import com.gederin.authors.v3.model.Author;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

import lombok.AllArgsConstructor;

import static com.gederin.authors.v3.AmqpConfig.AUTHOR_CREATED_TOPIC_EXCHANGE_NAME;

@Service
@AllArgsConstructor
public class AmqpSenderService {

    private final RabbitTemplate rabbitTemplate;

    public void sendAuthorCreatedEvent(Author author) throws IOException {
        String routingKey = "author.created";

        rabbitTemplate.convertAndSend(AUTHOR_CREATED_TOPIC_EXCHANGE_NAME, routingKey, packAuthorCreatedEvent(author).toByteArray());
    }

    private MessageBufferPacker packAuthorCreatedEvent(Author author) throws IOException {
        try (MessageBufferPacker packer = MessagePack.newDefaultBufferPacker()) {
            packer.packInt(author.getId())
                    .packString(author.getFirstName())
                    .packString(author.getLastName());

            return packer;
        }
    }
}
