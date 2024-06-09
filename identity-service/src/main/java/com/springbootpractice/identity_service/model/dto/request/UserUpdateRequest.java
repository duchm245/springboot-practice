package com.springbootpractice.identity_service.model.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String dob;
    private int status;
}
