package com.example.UberProject_AuthService.helpers;

import com.example.UberProject_AuthService.models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

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
        return Collections.emptyList();

    }

    @Override
    public String getUsername() {
        return this.Username;
    }

    @Override
    public String getPassword()
    {
        return this.password;

    }


}
