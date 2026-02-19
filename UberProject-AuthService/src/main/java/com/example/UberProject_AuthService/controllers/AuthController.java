package com.example.UberProject_AuthService.controllers;

import com.example.UberProject_AuthService.dtos.AuthRequestDto;
import com.example.UberProject_AuthService.dtos.AuthResponseDto;
import com.example.UberProject_AuthService.dtos.PassengerResponseDto;
import com.example.UberProject_AuthService.dtos.PassengerSignUpResuestDto;
import com.example.UberProject_AuthService.service.AuthService;
import com.example.UberProject_AuthService.service.JWTService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController
{
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerResponseDto>signUp(@RequestBody PassengerSignUpResuestDto passengerSignUpResuestDto)
    {
        PassengerResponseDto response = authService.signupPassenger(passengerSignUpResuestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?>signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),authRequestDto.getPassword()));
        if(authentication.isAuthenticated())
        {
            ResponseCookie cookie = ResponseCookie.from("JwtToken")
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .build();


            Map<String,Object> payload = new HashMap<>();
            payload.put("email",authRequestDto.getEmail());

            response.setHeader(HttpHeaders.SET_COOKIE,cookie.toString());
            String jwtToken = jwtService.createToken(payload,authentication.getPrincipal().toString());
//            return new ResponseEntity<>("Successfull auth",HttpStatus.OK);
            return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(),HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Auth not successfull",HttpStatus.OK);
        }


    }

}
