package com.example.aida.service.ProductService;

import com.example.aida.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class FileProcessingServiceImpl implements FileProcessingService{
   @Autowired
    private ProductRepository productRepository;
    private final String basePath="E:/JN/";

    @Override
    public List<String> fileList() {
        File dir = new File(basePath);
        File[] files = dir.listFiles();

        return files != null ? Arrays.stream(files).map(File::getName).collect(Collectors.toList()) : null;
    }


    @Override
    public String uploadFile(MultipartFile file) {
        // Find product by ID
//        Product product = productRepository.findById(productId).orElse(null);
//        if (product == null) {
//            throw new RuntimeException("Product not found with id: " + productId);
//        }

        // Check if the file is not empty
        if (file != null && !file.isEmpty()) {
            // Normalize file name
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            try {
                // Copy file to the target location (replace existing file with the same name)
                Path targetLocation = Paths.get(basePath + fileName);
                System.out.println(fileName);
                Files.copy(file.getInputStream(), targetLocation, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                // Update product with the image URL
                //product.setImageUrl(fileName); // You might want to store the full path or URL

                // Save updated product with image URL
                //return productRepository.save(product);
                return fileName;
            } catch (IOException ex) {
                throw new RuntimeException("Failed to store file " + fileName, ex);
            }
        } else {
            throw new RuntimeException("File is empty or null");
        }
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {

        String filePath="E://JN//"+fileName;
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}
