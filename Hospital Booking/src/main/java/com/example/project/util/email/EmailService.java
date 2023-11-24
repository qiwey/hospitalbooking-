package com.example.project.util.email;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;

public interface EmailService {
    void send(String subject, String content, String to, boolean isHtmlFormat);

    void send(String subject, String content, String to);

    void send(String subject, String content, String to, boolean isHtmlFormat, String attachmentName, Path attachmentPath);

    void send(String subject, String content, String to, String attachmentName, Path attachmentPath);

    void sendInvoiceAsAttachment(String subject, String body, String recipientEmail, String attachmentName, InputStream attachment);

    void send(String subject, String content, String[] from, String to, boolean isHtmlFormat, MultipartFile multipartFile);

    void send(String subject, String content, String[] from, String to, boolean isHtmlFormat);
}