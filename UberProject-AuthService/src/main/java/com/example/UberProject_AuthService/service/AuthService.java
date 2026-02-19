package com.example.UberProject_AuthService.service;

import com.example.UberProject_AuthService.dtos.PassengerResponseDto;
import com.example.UberProject_AuthService.dtos.PassengerSignUpResuestDto;
import com.example.UberProject_AuthService.models.Passenger;
import com.example.UberProject_AuthService.repositries.passengerRepositries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import static jdk.internal.classfile.Classfile.build;

@Service
public class AuthService
{
    @Autowired
    private passengerRepositries passengerRepositries;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PassengerResponseDto signupPassenger(PassengerSignUpResuestDto dto) {
        Passenger passenger = Passenger.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phone_number(dto.getPhoneNumber())
                .build();

        Passenger newPassenger = passengerRepositries.save(passenger);

        return PassengerResponseDto.from(newPassenger);
    }



}
