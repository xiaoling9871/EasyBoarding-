package com.example.demo.service;

import com.example.demo.Domain.EmailDetail;
import com.example.demo.Service.Impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class ServiceTest {


    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailServiceImpl emailService;

    @Test
    void test_sendmail() throws Exception{

        String address1 = "123";
        String address2 = "tom@gmail.com";

        SimpleMailMessage msg1 = new SimpleMailMessage();
        SimpleMailMessage msg2 = new SimpleMailMessage();

        msg1.setTo(address1);
        msg2.setTo(address2);






        EmailDetail email1 = EmailDetail.builder().recipient(address1).build();
        EmailDetail email2 = EmailDetail.builder().recipient(address2).build();



        Mockito.doNothing().when(this.javaMailSender).send(msg2);




    //    doThrow(new Exception()).when(this.javaMailSender).send(msg1);
    //    doNothing().when(this.javaMailSender).send(msg2);




    //    assertEquals("Email send failed",this.emailService.sendSimpleEmail(email1).getInfo());
        assertEquals("Email send successfully",this.emailService.sendSimpleEmail(email2).getInfo());








    }


}
