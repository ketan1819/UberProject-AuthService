package com.example.UberProject_AuthService.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    // this method creates a brand new JWT token
    public String createToken(Map<String, Object> payload, String username) {
        Date now = new Date();
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        Date expiryDate = new Date(now.getTime() + expiry * 1000L);

        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setSubject(username)
                .signWith(key)
                .compact();
    }

    @Override
    public void run(String... args) throws Exception
    {
        Map<String,Object> mp = new HashMap<>();
        mp.put("email","a@bgmail");
        mp.put("phoneNumber","6854254135");
        String result = createToken(mp,"Ketan");

        System.out.println("Generated token is : "+result);
    }
}
