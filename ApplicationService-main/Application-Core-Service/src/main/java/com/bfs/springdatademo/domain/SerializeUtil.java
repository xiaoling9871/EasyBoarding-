package com.bfs.springdatademo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializeUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String serialize(EmailDetail email){
        String result = null;
        try {
            result = objectMapper.writeValueAsString(email);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
