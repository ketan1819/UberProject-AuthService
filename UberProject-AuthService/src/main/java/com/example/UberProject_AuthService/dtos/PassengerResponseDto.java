package com.example.UberProject_AuthService.dtos;

import com.example.UberProject_EntityService.models.Passenger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerResponseDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Date createdAt;

    public static PassengerResponseDto from(Passenger p) {
        return PassengerResponseDto.builder()
                .id(p.getId())
                .createdAt(p.getCreatedAt())
                .email(p.getEmail())
                .password(p.getPassword())
                .phoneNumber(p.getPhone_number())
                .name(p.getName())
                .build();
    }
}
