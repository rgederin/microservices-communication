package com.gederin.books.v3.service.amqp;

import com.gederin.books.v3.model.Author;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

import lombok.AllArgsConstructor;

import static com.gederin.books.v3.config.AmqpConfig.BOOK_TOPIC_EXCHANGE_NAME;

@Service
@AllArgsConstructor
public class AmqpSenderService {

    private final RabbitTemplate rabbitTemplate;

    public void sendBookCreatedEvent(Author author) throws IOException {
        String routingKey = "book_created";
        rabbitTemplate.convertAndSend(BOOK_TOPIC_EXCHANGE_NAME, routingKey, packBookCreatedEvent(author).toByteArray());
    }

    private MessageBufferPacker packBookCreatedEvent(Author author) throws IOException {
        try (MessageBufferPacker packer = MessagePack.newDefaultBufferPacker()) {
            packer.packInt(author.getId())
                    .packString(author.getFirstName())
                    .packString(author.getLastName());

            return packer;
        }
    }
}
