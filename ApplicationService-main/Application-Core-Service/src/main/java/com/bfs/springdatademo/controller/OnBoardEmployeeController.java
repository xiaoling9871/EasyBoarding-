package com.bfs.springdatademo.controller;

import com.bfs.springdatademo.domain.DigitalDocument;
import com.bfs.springdatademo.entity.response.ResponseEmployeeStatus;
import com.bfs.springdatademo.entity.response.responseAll.ResponseAllDigitalDocs;
import com.bfs.springdatademo.service.ApplicationService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/boarding")
public class OnBoardEmployeeController {
    private final ApplicationService applicationService;

    public OnBoardEmployeeController(ApplicationService applicationService) {
        this.applicationService = applicationService;

    }

    // 应该是compostive service
//    @PostMapping()
//    public void submitForm(@RequestParam String id){
//        applicationService.firstSubmit(id);
//    }

    //Composited Service to see the preview of submitted documents (the endpoint should not have {user_id}), This endpoint just for database test connectivity
    @GetMapping("/viewApplication/{userid}")
    public ResponseEmployeeStatus viewApplication(@PathVariable String userid){
        String status = applicationService.checkStatus(userid);
        String comment = applicationService.findCommentbyId(userid).get().getComment();
        return ResponseEmployeeStatus.builder().employeeID(userid).status(status).comment(comment).build();
    }

    @GetMapping("/viewDigitalDocLink")
    public ResponseAllDigitalDocs viewDigitalDocLink(){
        //System.out.println(applicationService.listDigitalDocument().get().get(0));
        List<String> list = new ArrayList<>();
        List<DigitalDocument> digitalDocuments = applicationService.listDigitalDocument();
        for(int i = 0; i < digitalDocuments.size(); i++){
            list.add(digitalDocuments.get(i).getPath());
        }
        return ResponseAllDigitalDocs.builder().status("Here is the linked of support documents, please download them").supportDocs(list).build();

    }

}
