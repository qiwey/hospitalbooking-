package com.example.project.util.storage;

import com.example.project.base.exception.FileException;
import com.example.project.base.exception.NotFoundException;
import com.example.project.base.exception.StorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;
    private final long maxFileSize;
    private final Set<String> allowedExtensions;

    public StorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.maxFileSize = properties.getMaxFileSize().toBytes();
        this.allowedExtensions = properties.getAllowedExtensions();
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new FileException("Could not initialize storage");
        }
    }

    @Override
    public String store(MultipartFile file) throws StorageException, FileException {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new StorageException("File has no valid name.");
            }
            String fileExtension = FilenameUtils.getExtension(originalFilename);
            if (!allowedExtensions.contains(fileExtension)) {
                throw new StorageException(String.format("File extension %s is not allowed", fileExtension));
            }

            if (file.getSize() > this.maxFileSize) {
                throw new StorageException("File must be <= " + this.maxFileSize / 1_000_000L + "Mb");
            }

            String generatedFilename = UUID.randomUUID().toString().replace("-", "")
                    + "-" + originalFilename;

            Path destinationFile = this.rootLocation.resolve(Paths.get(generatedFilename))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new FileException(
                        "Cannot store file outside current directory.");
            }
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            return generatedFilename;
        } catch (IOException e) {
            throw new FileException("Failed to store file." + e.getMessage());
        }
    }

    @Override
    public List<String> storeAll(MultipartFile[] files) {
        List<String> generatedFileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            generatedFileNames.add(store(file));
        }
        return generatedFileNames;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new FileException("Failed to read stored files");
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new NotFoundException("Could not read file: " + filename);
        }
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new NotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new FileException("Could not read file: " + filename);
        }
    }

    @Override
    public void delete(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return;
        }
        Path file = rootLocation.resolve(fileName);
        if (!Files.exists(file)) {
            throw new NotFoundException("Could not find file: " + fileName);
        }
        if (!FileSystemUtils.deleteRecursively(file.toFile())) {
            throw new FileException("Failed to delete file: " + fileName);
        }
    }

    @Override
    public void deleteAll() {
        if (!FileSystemUtils.deleteRecursively(rootLocation.toFile())) {
            throw new FileException("Failed to delete all files");
        }
    }
}
