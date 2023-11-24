package com.example.project.util.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.util.Set;

@ConfigurationProperties(prefix = "storage")
@Data
public class StorageProperties {
    private final String location;
    private final DataSize maxFileSize;
    private final Set<String> allowedExtensions;
}
