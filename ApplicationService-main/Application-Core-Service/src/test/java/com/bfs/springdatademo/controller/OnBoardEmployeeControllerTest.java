package com.bfs.springdatademo.controller;

import com.bfs.springdatademo.domain.ApplicationWorkFlow;
import com.bfs.springdatademo.domain.DigitalDocument;
import com.bfs.springdatademo.entity.response.ResponseEmployeeStatus;
import com.bfs.springdatademo.service.ApplicationService;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@WebMvcTest(controllers = OnBoardEmployeeController.class)
public class OnBoardEmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ApplicationService applicationService;

    @Autowired
    private OnBoardEmployeeController onBoardEmployeeController;

//    @Test
//    public void viewApplicationTest() throws Exception {
//        MvcResult result = mockMvc
//                .perform(MockMvcRequestBuilders.get("/boarding/viewApplication/123")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//        // use Gson to convert the JSON response to a Product object
////        Gson gson = new Gson();
//        Mockito.when(applicationService.checkStatus("123")).thenReturn("Pending");
//        ApplicationWorkFlow applicationWorkFlow = ApplicationWorkFlow.builder().comment("haha").build();
//        Mockito.when(applicationService.findCommentbyId("123")).thenReturn(Optional.of(applicationWorkFlow));
//        JSONObject actual = new JSONObject(result.getResponse().getContentAsString());
//
//
//        assertEquals("Pending", actual.getString("status"));
////        Product actual = gson.fromJson(result.getResponse().getContentAsString(), Product.class);
////        assertEquals(expected.toString(), actual.toString());
//    }

    @Test
    public void viewDigitalDocLinkTest(){
        List<DigitalDocument> digitalDocuments = new ArrayList<>();
        onBoardEmployeeController.viewDigitalDocLink();
        Mockito.when(applicationService.listDigitalDocument()).thenReturn(digitalDocuments);
        assertEquals(0, digitalDocuments.size());
    }
}
