package com.bfs.springdatademo.controller;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.bfs.springdatademo.domain.ApplicationWorkFlow;
import com.bfs.springdatademo.entity.request.WorkFlowRequest;
import com.bfs.springdatademo.entity.response.ResponseRegistrationToken;
import com.bfs.springdatademo.entity.response.ResponseWorkFlow;
import com.bfs.springdatademo.repository.ApplicationRepo;
import com.bfs.springdatademo.service.ApplicationService;
import com.bfs.springdatademo.service.TokenService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = OnBoardHRController.class)
public class OnBoardHRControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenService tokenService;
    @MockBean
    private ApplicationService applicationService;

    @MockBean
    private ApplicationRepo applicationRepo;

    @MockBean
    private RabbitTemplate rabbitTemplate;


//    @Test
//    public void CreateWorkFlowTest() throws Exception {
//                MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.get("/", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        // use Gson to convert the JSON response to a Product object
//        Gson gson = new Gson();
//        Product actual = gson.fromJson(result.getResponse().getContentAsString(), Product.class);
//        assertEquals(expected.toString(), actual.toString());
//    }

    @Test
    public void generating_tokenTest() {
        OnBoardHRController onBoardHRController = new OnBoardHRController(tokenService,applicationService);
        ResponseRegistrationToken registrationToken = ResponseRegistrationToken.builder().message("Update in the database").build();
        onBoardHRController.generating_token("yao00116@umn.edu");

        assertEquals(registrationToken.getMessage(), onBoardHRController.generating_token("yao00116@umn.edu").getMessage());
    }

    @Test
    public void HRcheckStatusTest(){

        OnBoardHRController onBoardHRController = new OnBoardHRController(tokenService,applicationService);
        Mockito.when(applicationService.checkStatus("63d344f48fd868495a990f19")).thenReturn("NS,C");
        String res = onBoardHRController.HRcheckStatus("63d344f48fd868495a990f19");
        assertEquals("NS,C", res);
    }
//    @Test
//    public void HRupdateStatusTest(){
//        onBoardHRController.HRupdateStatus("63d344f48fd868495a990f19","A","yao00116@umn.edu", "please re-submit");
//        //Mockito.when(applicationService.checkStatus("63d344f48fd868495a990f19")).thenReturn("NS,C");
//
//        //assertEquals("NS", "NS");
//        doNothing().when(applicationService).updatestatus("123","A","please re-submit");
//
//    }



}
