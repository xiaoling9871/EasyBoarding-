package com.example.employeeservice.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "s3-service")
public interface RemoteS3Service {
    @PostMapping(value = "/s3/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    String uploadFile(@RequestPart MultipartFile file);

    @DeleteMapping(value = "/s3/delete/{fileName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    String deleteFile(@PathVariable String fileName);
}
