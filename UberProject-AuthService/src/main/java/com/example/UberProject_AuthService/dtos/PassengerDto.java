package com.example.UberProject_AuthService.dtos;


import lombok.*;

// this is kind of response dto
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto
{
    private String id;
    private String name;
    private String email;
    private String password; // encrypted password

    private Data createdAt;



}
