package com.example.demo.Service.Impl;

import com.example.demo.Domain.EmailDetail;
import com.example.demo.Domain.Response;
import com.example.demo.Service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;


    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public Response sendSimpleEmail(EmailDetail emailDetail) {

        SimpleMailMessage mail = new SimpleMailMessage();
        System.out.println(emailDetail.getRecipient());

        mail.setTo(emailDetail.getRecipient());
        mail.setSubject(emailDetail.getSubject());
        mail.setText(emailDetail.getMsgBody());


        try {
            System.out.println(mail);
            this.javaMailSender.send(mail);
            return Response.builder().Flag(true).Info("Email send successfully").build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.builder().Flag(false).Info("Email send failed").build();
        }
    }


}
