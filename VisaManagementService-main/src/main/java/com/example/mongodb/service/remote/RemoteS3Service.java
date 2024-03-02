package com.example.mongodb.service.remote;


import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("s3-service")
public interface RemoteS3Service {

    @GetMapping("/s3/download/{fileName}")
    ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName);

    @PostMapping(value = "/s3/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    String uploadFile(@RequestPart MultipartFile file);

    @DeleteMapping("/s3/delete/{fileName}")
    ResponseEntity<String> deleteFile(@PathVariable String fileName);
}
