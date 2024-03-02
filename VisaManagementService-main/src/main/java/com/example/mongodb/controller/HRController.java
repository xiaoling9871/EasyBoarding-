package com.example.mongodb.controller;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.example.mongodb.entity.EmployeeEntity.Employee;
import com.example.mongodb.entity.EmployeeEntity.PersonalDocument;
import com.example.mongodb.entity.Response.ResponseEmployeeApplication;
import com.example.mongodb.entity.Response.ResponseEmployeeStatus;
import com.example.mongodb.entity.Response.ResponseViewApplication;
import com.example.mongodb.repository.EmployeeRepo;
import com.example.mongodb.service.CompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("HC")
public class HRController {

    private final CompositeService compositeService;
    private final EmployeeRepo employeeRepo;

    public final String[] step = {"OPT Receipt","OPT EAD","i-983", "i-20","OPT STEM Receipt", "OPT STEM EAD","Completed"};
    public final String[] curr = {"I","OE","i983","i20","OSR","OSE","C"};

    public HRController(CompositeService compositeService,EmployeeRepo employeeRepo){
        this.compositeService = compositeService;
        this.employeeRepo = employeeRepo;
    }

    @GetMapping("/downloadLatestDoc")
    public ResponseEntity<ByteArrayResource> downloadLatestDoc(@RequestParam String id){
        //gain the filename of the employee
        System.out.println(id);
        Optional<Employee> employee = employeeRepo.findEmployeeById(id);
        //Latest
        //String file_id = employee.get().getPersonalDocumentList().get(employee.get().getPersonalDocumentList().size()-1).getId();
        //using S3 download

        System.out.println(employee.get().getPersonalDocumentList().get(0));

        List<PersonalDocument> personalDocuments = employee.get().getPersonalDocumentList();
        int size = personalDocuments.size();
        String actualpath;
        String path = personalDocuments.get(size-1).getPath();
//        if(size == 1){
            actualpath = path.split("/")[path.split("/").length - 1];
//        }
//        else{
//            actualpath = path.substring(15, path.length());
//        }
        //System.out.println(path.substring(15,path.length()));
        //String actualpath = path.split("/")[path.split("/").length - 1];
        ResponseEntity<ByteArrayResource> bytes = compositeService.downloadfile(actualpath);
        return bytes;
    }

    //HR Views intial application
    @GetMapping("/viewApplication")
    public ResponseEmployeeApplication viewApplication(@RequestParam String id){
        Employee employee = compositeService.viewApplication(id);
        return ResponseEmployeeApplication.builder().status("success").employee(employee).build();
    }

    @PostMapping("updateStatus/{id}")
    public ResponseEmployeeStatus updateStatus(@PathVariable String id, @RequestParam String decision){
        //System.out.println("taesttestse");
        //已经submit
        String message = "Employee haven uploaded yet";
        String status = compositeService.checkStatus(id).getStatus();
        String state = status.split(",")[0];
        String process = status.split(",")[1];
        System.out.println(process);
        //必须知道fileName叫什么
        Optional<Employee> employee = employeeRepo.findEmployeeById(id);

        List<PersonalDocument> personalDocuments = employee.get().getPersonalDocumentList();
        int size = personalDocuments.size();
        String filename = personalDocuments.get(size-1).getPath();
        if(size != 1){
            filename = filename.substring(15, filename.length());
        }
        //Remove from the List
        //personalDocuments.remove(personalDocuments.get(size-1));
        PersonalDocument personalDocument =  personalDocuments.get(personalDocuments.size()-1);

        System.out.println(personalDocument.getPath());
        message = "Updated!";
        //被拒绝
        if(!state.equals("NS")){ //说明已经submit了
            if(decision.equals("R")){
                compositeService.removeLastestDoc(personalDocument, id);
                message = "Reject!";
            }
            compositeService.updateStatus(id, decision);
            //compositeService.updateCurrStatus(id);
        }
//        if(compositeService.checkStatus(id).getStatus().split(",")[0] != "NS"){
//            System.out.println(compositeService.checkStatus(id).getStatus());
//            compositeService.updateStatus(id, decision);
//            message = "Updated!";
//        }

        return ResponseEmployeeStatus.builder().employeeID(id).status(message).build();

    }
//    @GetMapping("nmsl/{id}")
//    public void getCurrentStatus(@PathVariable String id){
//        System.out.println(compositeService.checkStatus(id).getStatus());
//    }
}
