package com.example.mongodb;

import com.example.mongodb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongoDbApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Test
    void contextLoads() {
        userRepository.findAll().forEach(System.out::println);
    }
}
