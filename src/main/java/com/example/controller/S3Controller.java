package com.example.controller;

import com.example.service.S3Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        System.out.println("Filename : " + file.getOriginalFilename());
        s3Service.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String filename){
        byte[] data = s3Service.downloadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:")
                .body(data);
    }
}