package com.example.authserver;

import com.example.authserver.entity.User;
import com.example.authserver.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class AuthServerApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateUser() {
        User jack = userRepository.save(User.builder()
                .username("jack")
                .email("jack@test.com")
                .password("12345")
                .activeFlag(true)
                .build());
        System.out.println(jack);
    }

    @Test
    void testGetAllUser() {
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void testUserRoles() {
        Optional<User> user = userRepository.findById(1);
        user.get().getRoles().forEach(System.out::println);
    }

}
