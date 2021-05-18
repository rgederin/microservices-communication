package com.gederin.books.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class BooksController {

    @GetMapping("health")
    public String health(){
        return "books service is healthy";
    }
}