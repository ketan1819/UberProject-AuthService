package com.example.UberProject_AuthService.controllers;

import com.example.UberProject_AuthService.dtos.AuthRequestDto;
import com.example.UberProject_AuthService.dtos.PassengerResponseDto;
import com.example.UberProject_AuthService.dtos.PassengerSignUpResuestDto;
import com.example.UberProject_AuthService.service.AuthService;
import com.example.UberProject_AuthService.service.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto,
                                    HttpServletResponse response)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getEmail(),
                        authRequestDto.getPassword()
                )
        );

        if(authentication.isAuthenticated())
        {
            Map<String,Object> payload = new HashMap<>();
            payload.put("email", authRequestDto.getEmail());

            String jwtToken = jwtService.createToken(
                    payload,
                    authentication.getPrincipal().toString()
            );

            ResponseCookie cookie = ResponseCookie.from("JwtToken", jwtToken)
                    .httpOnly(true)                // prevents JS access
                    .secure(false)                 // true in production (HTTPS)
                    .path("/")
                    .maxAge(60 * 60 * 24)          // 1 day expiry
                    .sameSite("Strict")            // CSRF protection
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok("Login Successful");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Authentication Failed");
    }

    // write a new /validate api that atleat fetches the jwt token

    @GetMapping("/validate")
    public ResponseEntity<?>validateJwtToken(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();

        if (request.getCookies() == null) {
            return null;
        }
        String jwtToken = " ";

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JwtToken")) {
                jwtToken = cookie.getValue();
            }
        }

        System.out.println("Jwt token fetched from httpCookie object is :"+jwtToken);



        return ResponseEntity.ok("Token sucessfully validated");

    }

}
