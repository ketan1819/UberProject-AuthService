package com.example.UberProject_AuthService.helpers;

import com.example.UberProject_AuthService.models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// why we need this class
// Because spring security works on userdetails polymorphic type for auth

public class AuthPassengerDetails extends Passenger implements UserDetails
{
    private String Username; // email , name , id

    private String password;

    public AuthPassengerDetails(Passenger passenger)
    {
        this.Username = passenger.getEmail();
        this.password = passenger.getPassword();

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.Username;
    }


}
