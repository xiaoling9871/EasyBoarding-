package com.bfs.springdatademo.service;


import com.bfs.springdatademo.domain.RegistrationToken;
import com.bfs.springdatademo.repository.ApplicationRepo;
import com.bfs.springdatademo.repository.RegistrationTokenRepo;
import com.bfs.springdatademo.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TokenServiceServiceTest {

    @InjectMocks
    private TokenService tokenService;
    @Mock
    private RegistrationTokenRepo registrationTokenRepo;

    @Mock
    private JwtProvider jwtProvider;

//    @Test
//    public void generateToken(){
//        RegistrationToken registrationToken = RegistrationToken.builder().token("123").build();
//        Mockito.verify(registrationTokenRepo).save(registrationToken);
//    }
}
