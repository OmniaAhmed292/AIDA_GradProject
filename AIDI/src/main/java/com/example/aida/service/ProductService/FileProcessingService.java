package com.example.aida.service.ProductService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileProcessingService {
    List<String> fileList();

    String uploadFile(MultipartFile file);
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException;
}
