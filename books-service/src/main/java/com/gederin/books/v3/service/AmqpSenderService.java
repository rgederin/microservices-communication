package com.gederin.books.v3.service;

import com.gederin.books.v3.model.BookWithAuthor;

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

    public void sendBookCreatedEvent(BookWithAuthor bookWithAutho) throws IOException {
        String routingKey = "book_created";

//        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
//        packer.packInt(1);
//        packer.close();

        rabbitTemplate.convertAndSend(BOOK_TOPIC_EXCHANGE_NAME, routingKey, packBookCreatedEvent(bookWithAutho).toByteArray());
    }

    private MessageBufferPacker packBookCreatedEvent(BookWithAuthor bookWithAuthor) throws IOException {
        try (MessageBufferPacker packer = MessagePack.newDefaultBufferPacker()) {
            packer.packInt(bookWithAuthor.getAuthorId())
                    .packString(bookWithAuthor.getFirstName())
                    .packString(bookWithAuthor.getLastName());

            return packer;
        }
    }
}
