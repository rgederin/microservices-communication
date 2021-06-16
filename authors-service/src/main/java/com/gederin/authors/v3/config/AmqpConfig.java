package com.gederin.authors.v3.config;

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
    public static final String AUTHORS_CREATED_QUEUE = "author_created_queue";
    public static final String AUTHOR_UPDATED_QUEUE = "author_updated_queue";
    public static final String AUTHOR_TOPIC_EXCHANGE_NAME = "author_topic_exchange";

    public static final String AUTHOR_CREATED_BINDING_PATTERN = "author_created";
    public static final String AUTHOR_UPDATED_BINDING_PATTERN = "author_updated";

    @Bean
    public Declarables authorTopicBindings() {
        Queue authorsCreatedQueue = new Queue(AUTHORS_CREATED_QUEUE, NON_DURABLE);
        Queue authorsUpdatedQueue = new Queue(AUTHOR_UPDATED_QUEUE, NON_DURABLE);

        TopicExchange topicExchange = new TopicExchange(AUTHOR_TOPIC_EXCHANGE_NAME, NON_DURABLE, false);

        return new Declarables(authorsCreatedQueue, authorsUpdatedQueue, topicExchange, BindingBuilder
                .bind(authorsCreatedQueue)
                .to(topicExchange)
                .with(AUTHOR_CREATED_BINDING_PATTERN), BindingBuilder
                .bind(authorsUpdatedQueue)
                .to(topicExchange)
                .with(AUTHOR_UPDATED_BINDING_PATTERN));
    }
}
