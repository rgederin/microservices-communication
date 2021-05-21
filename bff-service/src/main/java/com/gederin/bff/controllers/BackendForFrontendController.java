package com.gederin.bff.controllers;

import com.gederin.bff.dto.BookWithAuthorDto;
import com.gederin.bff.dto.DashboardDto;
import com.gederin.bff.dto.HealthDto;
import com.gederin.bff.service.BackendForFrontendService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class BackendForFrontendController {

    private final BackendForFrontendService backendForFrontendService;

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
}
