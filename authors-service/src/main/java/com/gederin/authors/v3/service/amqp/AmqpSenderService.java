package com.gederin.authors.v3.service.amqp;


import com.gederin.authors.v3.model.Author;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

import lombok.AllArgsConstructor;

import static com.gederin.authors.v3.config.AmqpConfig.AUTHOR_TOPIC_EXCHANGE_NAME;

@Service
@AllArgsConstructor
public class AmqpSenderService {

    private final RabbitTemplate rabbitTemplate;

    public void sendAuthorCreatedEvent(Author author) throws IOException {
        String routingKey = "author_created";

        rabbitTemplate.convertAndSend(AUTHOR_TOPIC_EXCHANGE_NAME, routingKey, packEvent(author).toByteArray());
    }

    public void sendAuthorUpdatedEvent(Author author) throws IOException {
        String routingKey = "author_updated";

        rabbitTemplate.convertAndSend(AUTHOR_TOPIC_EXCHANGE_NAME, routingKey, packEvent(author).toByteArray());
    }

    private MessageBufferPacker packEvent(Author author) throws IOException {
        try (MessageBufferPacker packer = MessagePack.newDefaultBufferPacker()) {
            packer.packInt(author.getId())
                    .packString(author.getFirstName())
                    .packString(author.getLastName());

            return packer;
        }
    }
}
