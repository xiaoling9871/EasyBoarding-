package com.example.mongodb.service;
import com.example.mongodb.entity.EmployeeEntity.PersonalDocument;
//import com.example.employeeservice.entity.PersonalDocument;
import com.example.mongodb.entity.EmployeeEntity.Employee;
import com.example.mongodb.entity.Response.ResponseEmployeeStatus;
import com.example.mongodb.repository.EmployeeRepo;
import com.example.mongodb.repository.PersonalDocumentRepo;
import com.example.mongodb.service.remote.RemoteEmployeeService;
import com.example.mongodb.service.remote.RemoteS3Service;
import com.example.mongodb.service.remote.RemoteApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;
import java.util.Optional;

@Service
public class CompositeService {

    private RemoteS3Service s3Service;

    private RemoteApplicationService remoteApplicationService;

    private RemoteEmployeeService remoteEmployeeService;

    //private RemoteApplicationService remoteApplicationService;
    public final String[] step = {"OPT Receipt","OPT EAD","i-983", "i-20","OPT STEM Receipt", "OPT STEM EAD","Completed"};
    public final String[] curr = {"I","OE","i983","i20","OSR","OSE","C"};

    private EmployeeRepo repo;
//    @Value("${s3.file-path-prefix}")
//    private String filePathPrefix;

    private PersonalDocumentRepo personalDocumentRepo;
    @Autowired
    public void setS3Service(RemoteS3Service s3Service, RemoteEmployeeService remoteEmployeeService, EmployeeRepo repo, PersonalDocumentRepo personalDocumentRepo, RemoteApplicationService remoteApplicationService){
        this.s3Service = s3Service;
        this.remoteEmployeeService = remoteEmployeeService;
        this.repo = repo;
        this.personalDocumentRepo = personalDocumentRepo;
        this.remoteApplicationService = remoteApplicationService;
    }

    public ResponseEntity<ByteArrayResource> downloadfile(String path){
        ResponseEntity<ByteArrayResource> byteArrayResourceResponseEntity  = s3Service.downloadFile(path);
        return byteArrayResourceResponseEntity;
    }

    //Can be use in token
    public ResponseEntity UploadDoc(MultipartFile doc, String id){
        Optional<Employee> employee = repo.findEmployeeById(id);
        //Optional<Employee> employee = repo.findById(id);

        //System.out.println(employee.get().getPersonalDocumentList().size());
        //System.out.println("successful");
        employee.get().getPersonalDocumentList().add(uploadFileToS3(doc));
//        employee.get().getPersonalDocumentList().add(uploadFileToS3(doc));
////        int size = employee.get().getPersonalDocumentList().size(); //2,3,4...
        //System.out.println(employee.get().getPersonalDocumentList().size());

        repo.save(employee.get());

        //上传了已经！
        int size = employee.get().getPersonalDocumentList().size(); //第二个就是ead

        System.out.println("successful");
        return ResponseEntity.ok().build();
    }

    public ResponseEntity removeLastestDoc(PersonalDocument personalDocument, String id){
        //ResponseEntity<String> stringResponseEntity = new ResponseEntity<>();
        Optional<Employee> employee = repo.findEmployeeById(id);
        employee.get().getPersonalDocumentList().remove(personalDocument);
        //personalDocumentRepo.save(personalDocument);
        repo.save(employee.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Employee viewApplication(String id){
       return remoteEmployeeService.getById(id);
    }


    private PersonalDocument uploadFileToS3(MultipartFile file) {
        String filePath = s3Service.uploadFile(file);
        System.out.println(filePath + "-------");
        PersonalDocument document = PersonalDocument.builder()
                .createDate(new Date())
                .path(filePath)
                .build();
        return personalDocumentRepo.save(document);
    }

    public ResponseEmployeeStatus checkStatus(String id){
        System.out.println(id);
        return remoteApplicationService.viewApplication(id);
    }

    public void updateStatus(String id, String decision){
        remoteApplicationService.HRupdateStatus(id, decision);
    }

    public void updateCurrStatus(String id){
        remoteApplicationService.autoUpdate(id);
    }
}




