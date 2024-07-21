package com.springbootpractice.identity_service.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;
@Service
public class ImageService implements StorageService{
    private final Path storageFolder = Paths.get("uploads");

    public ImageService() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    @Override
    public String storageFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file");
            }
            // check file is image
            if (!isImageFile(file)) {
                throw new RuntimeException("Plsease check file is not image");
            }

            // file must be <= 5Mb
            float fileSize = file.getSize() /1_000_000.0f;
            if (fileSize > 5.0f) {
                throw new RuntimeException("File size must be <= 5Mb");
            }

            // rename file
            String fileName = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(fileName);
            fileName = String.format("%d.%s", System.currentTimeMillis(), fileExtension);

            // save file
            Path targetLocation = storageFolder.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"jpg", "jpeg", "png", "bmp"}).contains(fileExtension);
    }

    @Override
    public Stream<Path> loadAll() {
        return Stream.empty();
    }

    @Override
    public byte[] loadFile(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new RuntimeException("Could not read file: " + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + fileName, e);
        }
    }

    @Override
    public void deleteAll() {

    }
}
