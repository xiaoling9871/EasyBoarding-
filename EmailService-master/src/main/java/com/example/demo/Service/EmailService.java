package com.example.demo.Service;


import com.example.demo.Domain.EmailDetail;
import com.example.demo.Domain.Response;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    Response sendSimpleEmail(EmailDetail emailDetail);
}
