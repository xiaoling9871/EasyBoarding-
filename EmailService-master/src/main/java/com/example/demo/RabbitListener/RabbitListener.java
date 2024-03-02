package com.example.demo.RabbitListener;

import com.example.demo.Domain.EmailDetail;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.Impl.EmailServiceImpl;
import com.example.demo.Util.DeSerializeUtil;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;


public class RabbitListener implements MessageListener {
    EmailService emailService;

    @Autowired
    public RabbitListener(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onMessage(Message message) {

        try {
            EmailDetail email = DeSerializeUtil.readObject(new String(message.getBody()));
            System.out.println(email);
            emailService.sendSimpleEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
