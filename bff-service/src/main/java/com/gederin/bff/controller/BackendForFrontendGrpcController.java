package com.gederin.bff.controller;

import com.gederin.bff.dto.DashboardDto;
import com.gederin.bff.service.BackendForFrontendGrpcService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v2")
@AllArgsConstructor
@Slf4j
public class BackendForFrontendGrpcController {

    private final BackendForFrontendGrpcService grpcService;

    @GetMapping("dashboard")
    @ResponseStatus(HttpStatus.OK)
    public DashboardDto dashboard() throws ExecutionException, InterruptedException {
        return grpcService.getBooksAndAuthors();
    }
}
