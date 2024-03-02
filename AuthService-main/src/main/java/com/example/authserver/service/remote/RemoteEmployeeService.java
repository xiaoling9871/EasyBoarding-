package com.example.authserver.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("employee-service")
public interface RemoteEmployeeService {
    @GetMapping("/employee/user/{userId}")
    String getEmployeeIdByUserId(@PathVariable Integer userId);
}
