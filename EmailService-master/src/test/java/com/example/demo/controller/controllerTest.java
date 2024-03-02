package com.example.demo.controller;


import com.example.demo.Controller.EmailController;
import com.example.demo.Domain.EmailDetail;
import com.example.demo.Domain.Response;
import com.example.demo.Service.Impl.EmailServiceImpl;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = EmailController.class)
public class controllerTest {

    @MockBean
    EmailServiceImpl emailService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void test_sendEmail() throws Exception {

        EmailDetail email = new EmailDetail();
        email.setRecipient("tom14@test.com");
        Gson gson = new Gson();
        String expectedJson = gson.toJson(email);

        Mockito.when(this.emailService.sendSimpleEmail(Mockito.isA(EmailDetail.class)))
                .thenReturn( Response.builder().Info("done").build() );

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/sendEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedJson)
        ).andReturn();

        JSONObject actual = new JSONObject(result.getResponse().getContentAsString());
        assertEquals("done",actual.getString("info"));

    }


}
