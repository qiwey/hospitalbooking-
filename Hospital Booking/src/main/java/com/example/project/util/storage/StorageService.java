package com.example.project.util.storage;

import com.example.project.base.exception.FileException;
import com.example.project.base.exception.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file) throws StorageException, FileException;

    List<String> storeAll(MultipartFile[] files);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void delete(String filename);

    void deleteAll();
}
