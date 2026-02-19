package com.example.UberProject_AuthService.repositries;

import com.example.UberProject_AuthService.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface passengerRepositries extends JpaRepository<Passenger,Long>
{

    Optional<Passenger> findPassengerByEmail(String email);
}
