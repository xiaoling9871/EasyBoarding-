package com.example.employeeservice;

import com.example.employeeservice.entity.Contact;
import com.example.employeeservice.repo.ContactRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ContactTests {
    @Autowired
    private ContactRepo contactRepo;


    @Test
    public void testCreateContact() {
        contactRepo.save(Contact.builder()
                        .cellPhone("1231231")
                        .email("dfadfa@sdfad.com")
                        .firstName("fad")
                        .lastName("dfa")
                        .type("emergency")
                        .relationship("father")
                .build());
    }

    @Test
    public void testGetById() {
        Optional<Contact> contact = contactRepo.findById("63cdae716b09e252572834eb");
        contact.ifPresent(System.out::println);
    }
}
