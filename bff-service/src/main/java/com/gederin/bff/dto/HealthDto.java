package com.gederin.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthDto {
    private String backendForFrontendServiceHealth;
    private String booksServiceHealth;
    private String authorsServiceHealth;
}
