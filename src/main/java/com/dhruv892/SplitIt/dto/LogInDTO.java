package com.dhruv892.SplitIt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogInDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
