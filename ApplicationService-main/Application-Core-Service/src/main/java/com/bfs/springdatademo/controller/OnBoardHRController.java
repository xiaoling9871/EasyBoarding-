package com.bfs.springdatademo.controller;
import com.bfs.springdatademo.domain.EmailDetail;
import com.bfs.springdatademo.domain.SerializeUtil;
import com.bfs.springdatademo.entity.request.WorkFlowRequest;
import com.bfs.springdatademo.entity.response.ResponseWorkFlow;
import com.bfs.springdatademo.repository.ApplicationRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.bfs.springdatademo.domain.ApplicationWorkFlow;
import com.bfs.springdatademo.domain.RegistrationToken;
import com.bfs.springdatademo.entity.response.responseAll.ResponseAll;
import com.bfs.springdatademo.entity.response.ResponseApplicationFilter;
import com.bfs.springdatademo.entity.response.ResponseRegistrationToken;
import com.bfs.springdatademo.service.ApplicationService;
import com.bfs.springdatademo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("HRManage")
public class OnBoardHRController {

    private final TokenService tokenService;

    private final ApplicationService applicationService;

    public ApplicationRepo applicationRepo;

    public RabbitTemplate rabbitTemplate;

    public final String[] step = {"OPT Receipt","OPT EAD","i-983", "i-20","OPT STEM Receipt", "OPT STEM EAD","Completed"};
    public final String[] curr = {"I","OE","i983","i20","OSR","OSE","C"};


    //Hard code
    public int index = 0;

    public OnBoardHRController(TokenService tokenService, ApplicationService applicationService){
        this.tokenService = tokenService;
        this.applicationService = applicationService;
    }

    @Autowired
    public void setApplicationRepo(ApplicationRepo applicationRepo){
        this.applicationRepo = applicationRepo;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    //创建 Work Flow
    @PostMapping("/CreatingWorkFlow")
    public ResponseWorkFlow CreateWorkFlow(@RequestBody WorkFlowRequest workFlowRequest){
        ApplicationWorkFlow applicationWorkFlow = ApplicationWorkFlow.builder().employeeID(workFlowRequest.getEmployeeid()).createDate(workFlowRequest.getCreateDate()).lastmodificationDate(workFlowRequest.getLastmodificationdate()).status(workFlowRequest.getStatus())
                        .comment(workFlowRequest.getComment()).build();
        applicationRepo.save(applicationWorkFlow);
        return ResponseWorkFlow.builder().status("success").build();
    }
    //Generating token (Expiration 3 hours)
    @PostMapping("/RegistrationToken")
    public ResponseRegistrationToken generating_token(@RequestParam String emailAddress){
        RegistrationToken registrationToken = tokenService.generateToken(emailAddress);
        return ResponseRegistrationToken.builder().message("Update in the database").data(registrationToken).build();
    }

    //Check application progress HR check status
    @GetMapping("application/{id}")
    public String HRcheckStatus(@PathVariable String id){
        return applicationService.checkStatus(id);
    }

    //Processing the application
    //加一个filter去确保只有新上传正确文件以后才能process，否则这个admin无法执行这个函数。
    @PostMapping("/determine/{id}")
    public void HRupdateStatus (@PathVariable String id, @RequestParam String determine, @RequestParam String emailaddress, @RequestParam String rejmessage){
        int index = 0;
        String updated = "";
        String message = "";
        String status = applicationService.checkStatus(id).split(",")[1];

        //找到第几步
        for(int i = 0; i < curr.length; i++){
            if(status.equals(curr[i])){
                index = i;
                break;
            }
        }
        //变成已经完成申请，全部通过
        if(status.equals("C")){
            updated = "Completed";
        } //Submitted
        else {
            //Approve
            if (determine.equals("A")) {
                index += 1;
                message = "Please upload a copy of your" + step[index];
            }
            //Reject Email Service
            else if (determine.equals("R")) {
                message = "Please re-submit your documentation of " + step[index];
                //Sending Email
                EmailDetail emailDetail = EmailDetail.builder().recipient(emailaddress).msgBody(rejmessage).subject("Reject notification").build();
                String jsonMessage = SerializeUtil.serialize(emailDetail);
                rabbitTemplate.convertAndSend("demo.proj", "onboarding", jsonMessage);
            }
            updated = "NS," + curr[index];
        }
        applicationService.updatestatus(id, updated, message);
    }

    @GetMapping("/viewAllApplicationByStatus/{status}")
    public ResponseAll viewAllApplicationByStatus(@PathVariable String status){
        List<ApplicationWorkFlow> applicationWorkFlowList = applicationService.findByStatus(status);
        String employeeid = applicationWorkFlowList.get(0).getEmployeeID();
        List<ResponseApplicationFilter> responseApplicationFilters = new ArrayList<>();
        for(int i = 0; i < applicationWorkFlowList.size(); i++){
            responseApplicationFilters.add(ResponseApplicationFilter.builder().employeeid(employeeid).status(status).build());
        }
        return ResponseAll.builder().status("Filter by Status").responseApplicationFilters(responseApplicationFilters).build();
    }

    @PostMapping("/auto-update/{id}")
    public void autoUpdate(@PathVariable String id){
        int index = 0;
        String status = applicationService.checkStatus(id).split(",")[1];
        System.out.println(status);
        for(int i = 0; i < curr.length; i++){
            if(status.equals(curr[i])){
                index = i;
                break;
            }
        }
        String message = "Waiting for HR to approve your " + step[index];
        String updated = "P," + curr[index];

        applicationService.updatestatus(id,updated,message);
    }
}
