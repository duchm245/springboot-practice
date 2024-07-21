package com.springbootpractice.identity_service.model.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String path;
    private String error;
    private String message;
}