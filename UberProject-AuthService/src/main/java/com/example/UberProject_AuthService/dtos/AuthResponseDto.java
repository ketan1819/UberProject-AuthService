package com.example.UberProject_AuthService.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AuthResponseDto {

    private String token;
    private String message;
}