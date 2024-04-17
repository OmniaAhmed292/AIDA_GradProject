package com.example.aida.Utils;
import java.io.File;
import java.io.IOException;


public class FileUtils {
    private FileUtils() {}

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean createFile(String path) {
        File file = new File(path);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    public static void writeFile(String filePath, String content) throws IOException {
        File file = new File(filePath);
        java.io.FileWriter fileWriter = new java.io.FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }

    public static void copyFile(String sourceFilePath, String destinationFilePath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);
        java.nio.file.Files.copy(sourceFile.toPath(), destinationFile.toPath());
    }

    public static void moveFile(String sourceFilePath, String destinationFilePath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);
        java.nio.file.Files.move(sourceFile.toPath(), destinationFile.toPath());
    }

    public static void listFiles(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }


}
