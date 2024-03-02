package com.example.mongodb.service.remote;


import com.example.mongodb.entity.Response.ResponseEmployeeStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("application-core-service")
public interface RemoteApplicationService {

    @GetMapping("application-core-service/boarding/viewApplication/{userid}")
    ResponseEmployeeStatus viewApplication(@PathVariable String userid);


    @PostMapping("application-core-service/HRManage/determine/{id}")
    void HRupdateStatus (@PathVariable String id, @RequestParam String determine);

    @PostMapping("application-core-service/HRManage/auto-update/{id}")
    void autoUpdate(@PathVariable String id);

}
