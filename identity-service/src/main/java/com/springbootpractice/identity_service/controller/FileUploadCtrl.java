package com.springbootpractice.identity_service.controller;

import com.springbootpractice.identity_service.model.dto.response.ResponseObject;
import com.springbootpractice.identity_service.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping(value = "/api/v1/upload")
public class FileUploadCtrl {
    @Autowired
    private StorageService storageService;

    @PostMapping(value = "")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = storageService.storageFile(file);
            return ResponseEntity.ok().body(new ResponseObject("File uploaded successfully", 200, fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject(e.getMessage(), 200, ""));
        }
    }


    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storageService.loadFile(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new byte[0]);
        }
    }
}
