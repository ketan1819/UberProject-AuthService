package com.example.UberProject_AuthService.service;

import com.example.UberProject_AuthService.helpers.AuthPassengerDetails;
import com.example.UberProject_AuthService.repositries.passengerRepositries;
import com.example.UberProject_EntityService.models.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// this class is responsible for loading the user in the form of userdetails object for auth
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private passengerRepositries passengerRepositries;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Optional<Passenger> passenger = passengerRepositries.findPassengerByEmail(email);

        if(passenger.isPresent())
        {
            return new AuthPassengerDetails(passenger.get());

        }
        else
        {
            throw new UsernameNotFoundException("Can not find the passenger by given email");

        }
    }
}
