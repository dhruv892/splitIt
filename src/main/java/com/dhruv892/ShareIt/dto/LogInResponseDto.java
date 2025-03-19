package com.dhruv892.ShareIt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogInResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;
}
