package com.example.UberProject_AuthService.controllers;

import com.example.UberProject_AuthService.dtos.PassengerDto;
import com.example.UberProject_AuthService.dtos.PassengerSignUpResuestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController
{
    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto>signUp(@RequestBody PassengerSignUpResuestDto passengerSignUpResuestDto)
    {
        return null;
    }

}
