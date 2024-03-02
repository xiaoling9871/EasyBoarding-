package com.example.employeeservice;

import com.example.employeeservice.entity.PersonalDocument;
import com.example.employeeservice.repo.PersonalDocumentRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class PersonalDocumentTests {
    @Autowired
    private PersonalDocumentRepo personalDocumentRepo;

    @Test
    public void testSave() {
        personalDocumentRepo.save(PersonalDocument.builder()
                .comment("this is not correct...")
                .createDate(new Date())
                .build());
    }

    @Test
    public void testGetById() {
        personalDocumentRepo.findById("63cdaff1b35c23286a0e7b35").ifPresent(System.out::println);
    }
}
