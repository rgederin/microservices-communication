package com.gederin.authors.v3;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
    public static final String BOOK_QUEUE = "book_created_queue";
    public static final String BOOK_BINDING_PATTERN = "book_created";

}
