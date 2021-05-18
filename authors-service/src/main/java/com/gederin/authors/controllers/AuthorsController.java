package com.gederin.authors.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthorsController {

    @GetMapping("health")
    public String health(){
        return "authors service is healthy";
    }
}
