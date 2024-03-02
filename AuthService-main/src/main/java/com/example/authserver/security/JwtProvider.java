package com.example.authserver.security;

import com.example.authserver.service.remote.RemoteEmployeeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class JwtProvider {
    @Value("${security.jwt.token.key}")
    private String key;

    private final RemoteEmployeeService employeeService;

    public JwtProvider(RemoteEmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    public String createToken(UserDetails userDetails, Integer userId) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("permissions", userDetails.getAuthorities());
        claims.put("userId", userId);
        claims.put("employeeId", employeeService.getEmployeeIdByUserId(userId));
//        claims.put("employeeId", )
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
