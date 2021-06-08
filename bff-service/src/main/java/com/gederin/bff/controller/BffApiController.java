package com.gederin.bff.controller;

import com.gederin.bff.dto.BookWithAuthorDto;
import com.gederin.bff.dto.DashboardDto;
import com.gederin.bff.dto.HealthDto;
import com.gederin.bff.service.BffApiService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
@Slf4j
public class BffApiController {

    private final BffApiService backendForFrontendService;

    @GetMapping("self/health")
    @ResponseStatus(HttpStatus.OK)
    public String selfHealth() throws ExecutionException, InterruptedException {
        return "bff service is ok";
    }


    @GetMapping("health")
    @ResponseStatus(HttpStatus.OK)
    public HealthDto health() throws ExecutionException, InterruptedException {
        return backendForFrontendService.health();
    }

    @GetMapping("dashboard")
    @ResponseStatus(HttpStatus.OK)
    public DashboardDto dashboard() throws ExecutionException, InterruptedException {
        return backendForFrontendService.getBooksAndAuthors();
    }

    @GetMapping("dashboard/combained")
    @ResponseStatus(HttpStatus.OK)
    public List<BookWithAuthorDto> combainedDashboard() throws ExecutionException, InterruptedException {
        return backendForFrontendService.getBooksWithAuthors();
    }


    @PostMapping("book")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> addBook(@RequestBody BookWithAuthorDto bookWithAuthorDto){
        log.info("addBook controller in bff service: {}", bookWithAuthorDto);
        return backendForFrontendService.addBook(bookWithAuthorDto);
    }
}
