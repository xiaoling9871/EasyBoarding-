package com.example.demo.Configuration;

import com.example.demo.RabbitListener.RabbitListener;
import com.example.demo.Service.Impl.EmailServiceImpl;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {


    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    Queue queue1() {
        return QueueBuilder.durable("registration").build();
    }

    Queue queue2() {
        return QueueBuilder.durable("onboarding").build();
    }

    Queue queue3() {
        return QueueBuilder.durable("visa").build();
    }

    @Bean
    MessageListenerContainer messageListenerContainer(EmailServiceImpl emailService) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory());
        messageListenerContainer.setQueues(queue1(), queue2(), queue3());
        messageListenerContainer.setMessageListener(new RabbitListener(emailService));
        return messageListenerContainer;
    }


}
