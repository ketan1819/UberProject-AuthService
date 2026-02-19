package com.example.UberProject_AuthService.dtos;

import lombok.*;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto
{
    private String email;

    private String password;
}
