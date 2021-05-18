package com.gederin.bff.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class BackendForFrontendController {

    @GetMapping("health")
    public String health(){
        return "bff service is healthy";
    }
}
