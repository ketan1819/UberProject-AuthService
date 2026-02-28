package com.example.UberProject_AuthService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    // this method creates a brand new JWT token
    public String createToken(Map<String, Object> payload, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry * 1000L);

        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setSubject(username)
                .signWith(getSignKey())
                .compact();
    }

    // converts the encoded token string to actual payload
    private <T> T extractPayload(String token, Function<Claims, T> resolverFunction) {
        final Claims claims = Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return resolverFunction.apply(claims);
    }

    // extract expiration date
    private Date extractExpiration(String token) {
        return extractPayload(token, Claims::getExpiration);
    }

    // extract email from token
    public String extractEmail(String token) {
        return extractPayload(token, claims -> claims.get("email", String.class));
    }

    // check if token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // create signing key
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // validate token
    public Boolean validateToken(String token, String email) {
        final String userEmailFetchedFromToken = extractEmail(token);
        return userEmailFetchedFromToken.equals(email) && !isTokenExpired(token);
    }

    private String extractPhoneNumber(String token)
    {
        return extractPayload(token, claims -> claims.get("phoneNumber", String.class));
    }


    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> mp = new HashMap<>();
        mp.put("email", "a@bgmail");
        mp.put("phoneNumber", "6854254135");
        String result = createToken(mp, "Ketan");


        System.out.println("Generated token is : " + result);
        System.out.println(extractPhoneNumber(result));
    }
}
