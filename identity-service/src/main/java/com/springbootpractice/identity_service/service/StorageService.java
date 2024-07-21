package com.springbootpractice.identity_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    public String storageFile(MultipartFile file);
    public Stream<Path> loadAll(); // load all file inside folder
    public byte[] loadFile(String fileName);
    public void deleteAll();
}
