package com.example.s3service.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class StorageService {
    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public StorageService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) {
        File fileObj = multipartFile2File(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return fileName;
    }

    public byte[] downloadFile(String fileName) {

        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        try {
            return IOUtils.toByteArray(s3Object.getObjectContent());
        } catch (IOException e) {
            log.error("Fail to download");
        }
        return null;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName;
    }

    private File multipartFile2File(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try {
            FileOutputStream fos = new FileOutputStream(convertedFile);
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error when converting file: " + file.getOriginalFilename());
            throw new RuntimeException(e);
        }
        return convertedFile;
    }

}
