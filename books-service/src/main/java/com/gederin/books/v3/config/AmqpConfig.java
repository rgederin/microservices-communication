package com.gederin.books.v3.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
    private static final boolean NON_DURABLE = false;

    public static final String BOOK_QUEUE = "book_created_queue";
    public static final String BOOK_TOPIC_EXCHANGE_NAME = "book_topic_exchange";
    public static final String BOOK_BINDING_PATTERN = "book_created";
    public static final String AUTHORS_CREATED_QUEUE = "author_created_queue";
    public static final String AUTHOR_UPDATED_QUEUE = "author_updated_queue";

    @Bean
    public Declarables bookTopicBindings() {
        Queue bookQueue = new Queue(BOOK_QUEUE, NON_DURABLE);

        TopicExchange topicExchange = new TopicExchange(BOOK_TOPIC_EXCHANGE_NAME, NON_DURABLE, false);

        return new Declarables(bookQueue, topicExchange, BindingBuilder
                .bind(bookQueue)
                .to(topicExchange)
                .with(BOOK_BINDING_PATTERN));
    }
}
