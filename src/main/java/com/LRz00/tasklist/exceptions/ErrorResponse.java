/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.LRz00.tasklist.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author lara
 */
@Data
@RequiredArgsConstructor
public class ErrorResponse {

    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> errors;

    @Data
    @RequiredArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class ValidationError {
        private final String field;
        private final String message;
    }
    
        public void addValidationError(String field, String message) {
        if (Objects.isNull(errors)) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(new ValidationError(field, message));
    }
}
