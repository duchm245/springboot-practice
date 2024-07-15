package com.springbootpractice.identity_service.model.dto.request;

import com.springbootpractice.identity_service.util.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserCreateRequest {
    @NotBlank(message = "firstName must be not blank")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 8 characters long")
    private String password;

    @Pattern(regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$", message = "email invalid format")
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String dob;

    @PhoneNumber(message = "phone invalid format")
    private String phone;
}
