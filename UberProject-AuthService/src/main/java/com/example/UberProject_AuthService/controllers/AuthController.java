package com.example.UberProject_AuthService.controllers;

import com.example.UberProject_AuthService.dtos.PassengerResponseDto;
import com.example.UberProject_AuthService.dtos.PassengerSignUpResuestDto;
import com.example.UberProject_AuthService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController
{
    @Autowired
    private AuthService authService;

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerResponseDto>signUp(@RequestBody PassengerSignUpResuestDto passengerSignUpResuestDto)
    {
        PassengerResponseDto response = authService.signupPassenger(passengerSignUpResuestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/signin")
    public ResponseEntity<?>signIn()
    {

        return new ResponseEntity<>(10,HttpStatus.ACCEPTED);

    }

}
