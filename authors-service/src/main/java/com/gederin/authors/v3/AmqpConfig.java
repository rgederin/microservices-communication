package com.gederin.authors.v3;

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
    public static final String AUTHOR_CREATED_TOPIC_EXCHANGE_NAME = "author_created_topic_exchange";
    public static final String AUTHOR_CREATED_BINDING_PATTERN = ".created";

    @Bean
    public Declarables authorTopicBindings() {
        Queue authorsQueue = new Queue(AUTHORS_CREATED_QUEUE, NON_DURABLE);

        TopicExchange topicExchange = new TopicExchange(AUTHOR_CREATED_TOPIC_EXCHANGE_NAME, NON_DURABLE, false);

        return new Declarables(authorsQueue, topicExchange, BindingBuilder
                .bind(authorsQueue)
                .to(topicExchange)
                .with(AUTHOR_CREATED_BINDING_PATTERN));
    }
}
