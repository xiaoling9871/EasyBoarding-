package com.example.demo.Controller;

import com.example.demo.Domain.EmailDetail;
import com.example.demo.Domain.Response;
import com.example.demo.Service.Impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EmailController {

    EmailServiceImpl emailService;

    @Autowired
    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/sendEmail")
    public Response sendEmail(@RequestBody EmailDetail emailDetail) {
        return this.emailService.sendSimpleEmail(emailDetail);
    }


}
