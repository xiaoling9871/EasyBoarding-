package com.example.demo.Util;

import com.example.demo.Domain.EmailDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DeSerializeUtil {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static EmailDetail readObject(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, EmailDetail.class);
    }
}






 /*   public static EmailDetail deserializeToMail(String message){
        String[] messageParts = message.split("#");
        if (messageParts.length == 3){
            return EmailDetail.builder()
                    .Recipient(messageParts[0])
                    .Subject("Registration link")
                    .MsgBody( "Dear Applicant: \n" +
                            "Here is your registration link: \n" +
                            messageParts[1] +  "\n"  +
                            "And your registration token\n" + messageParts[2] + "\n")
                    .build();
        }
        else if (messageParts[1].equals("rejected")){
            return EmailDetail.builder()
                    .Recipient(messageParts[0])
                    .Subject("Your application is not approved")
                    .MsgBody( "Dear Applicant: \n" +
                            "Unfortunately your application is not approved, please log in to see the reason\n")
                    .build();
        }
        else{
            return EmailDetail.builder()
                    .Recipient(messageParts[0])
                    .Subject("Your application is accepted")
                    .MsgBody( "Dear Applicant: \n" +
                            "Congradulation your application is approved\n")
                    .build();
        }
    }

  */

