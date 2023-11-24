package com.example.project.util.storage;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
public class StorageController {

    private final StorageService storageService;

    @GetMapping("/i/{filename:.+}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            Resource file = storageService.loadAsResource(filename);
            byte[] image = StreamUtils.copyToByteArray(file.getInputStream());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(image);
        } catch (Exception e) {
            log.error("Error while loading image");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename) {
        try {
            Resource resource = storageService.loadAsResource(filename);
            byte[] file = StreamUtils.copyToByteArray(resource.getInputStream());
            if (resource.getFile().getName().endsWith(".pdf")) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(file);
            }
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + filename)
                    .body(file);
        } catch (Exception e) {
            log.error("Error while loading file");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/i/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("upload") MultipartFile file, HttpServletRequest request) {
        String filename = storageService.store(file);
        String saved = request.getScheme()
                + "://"
                + request.getServerName()
                + ":" + request.getServerPort()
                + "/i/"
                + filename;
        Map<String, String> response = Map.of("url", saved, "fileName", filename, "uploaded", "1");
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/view/{filename:.+}")
    public ResponseEntity<Resource> viewFile(@PathVariable String filename) {
        Resource resource = storageService.loadAsResource(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
