package com.example.employeeservice;

import com.example.employeeservice.entity.Address;
import com.example.employeeservice.repo.AddressRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class AddressTests {
    @Autowired
    private AddressRepo addressRepo;

    @Test
    void testCreateAddress() {
        addressRepo.save(Address.builder()
                        .addressLine1("xx street")
                        .addressLine2("apt 01")
                        .state("MI")
                        .city("AA")
                        .zipCode("34019")
                .build());
    }
    @Test
    void testGetAllAddresses() {
        addressRepo.findAll().forEach(System.out::println);
    }

    @Test
    void testGetById() {
        Optional<Address> address = addressRepo.findById("63cd9110a41fb776a35be9ec");
        address.ifPresent(System.out::println);
    }

}
