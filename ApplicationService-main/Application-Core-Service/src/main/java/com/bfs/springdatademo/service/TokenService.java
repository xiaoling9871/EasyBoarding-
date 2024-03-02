package com.bfs.springdatademo.service;

import com.bfs.springdatademo.domain.RegistrationToken;
import com.bfs.springdatademo.repository.RegistrationTokenRepo;
import com.bfs.springdatademo.security.JwtProvider;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    private final JwtProvider jwtProvider;
    private final RegistrationTokenRepo registrationTokenRepo;

    public TokenService(JwtProvider jwtProvider, RegistrationTokenRepo registrationTokenRepo){
        this.jwtProvider = jwtProvider;
        this.registrationTokenRepo = registrationTokenRepo;
    }


    public RegistrationToken generateToken(String emailAddress){
        long now = (new Date()).getTime();
        //int id = registrationTokenRepo.findById(2);
        RegistrationToken registrationToken = RegistrationToken.builder()
                        .token(jwtProvider.createToken(emailAddress))
                                .Email(emailAddress)
                                        .date(new Date(now + 10800000))
                                                .createBy(4).build();
        //jwtProvider.createToken(emailAddress);
        //System.out.println(registrationToken.getEmail());

        return registrationTokenRepo.save(registrationToken);
    }
}
