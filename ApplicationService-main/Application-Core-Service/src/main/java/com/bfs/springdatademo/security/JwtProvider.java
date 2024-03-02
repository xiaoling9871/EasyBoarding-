package com.bfs.springdatademo.security;


import com.amazonaws.services.dynamodbv2.xspec.S;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("EmailToken")
    private String key;


    public String createToken(String emailAddress) {
        Claims claims = Jwts.claims().setSubject(emailAddress);
        Date date = new Date();
        System.out.println(date.getTime());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + 10800000))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

    }
}
