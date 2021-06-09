package com.gederin.authors.v3.service;

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import static com.gederin.authors.v3.AmqpConfig.BOOK_QUEUE;

@Service
@Slf4j
public class AmqpListenerService {

    @RabbitListener(queues = BOOK_QUEUE)
    public void receiveMessageFromTopic1(byte[] message) throws IOException {
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(message);

        int id1 = unpacker.unpackInt();
        String firstName = unpacker.unpackString();
        String thirdName = unpacker.unpackString();

        unpacker.close();

        log.info("id1 = {}", id1);
        log.info("firstName = {}", id1);log.info("firstName = {}", thirdName);

        // log.info("Received topic 1 (" + BOOK_BINDING_PATTERN + ") message: " + message);
    }
}
