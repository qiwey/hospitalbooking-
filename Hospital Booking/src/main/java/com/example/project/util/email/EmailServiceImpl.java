package com.example.project.util.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Async
    @Override
    public void send(String subject, String content, String to, boolean isHtmlFormat) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

            message.setSubject(subject);
            message.setText(content, isHtmlFormat);
            message.setTo(to);

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email to " + to);
        }
    }

    @Async
    @Override
    public void send(String subject, String content, String to) {
        send(subject, content, to, false);
    }

    @Async
    @Override
    public void send(String subject, String content, String to, boolean isHtmlFormat, String attachmentName, Path attachmentPath) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

            message.setTo(to);
            message.setSubject(subject);
            message.setText(content, isHtmlFormat);

            FileSystemResource file = new FileSystemResource(attachmentPath);
            message.addAttachment(attachmentName, file);

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email to " + to);
        }
    }

    @Async
    @Override
    public void send(String subject, String content, String to, String attachmentName, Path attachmentPath) {
        send(subject, content, to, false, attachmentName, attachmentPath);
    }

//    @Override
//    public void send(String subject, String content, String[] from, String to, boolean isHtmlFormat, MultipartFile multipartFile) {
//        try {
//            MimeMessage mimeMessage = emailSender.createMimeMessage();
//            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "utf-8");
//
//            // from[0]: email gửi, from[1] : tên alias ng gửi thay thế cho email
//            message.setFrom(from[0], from[1]);
//            message.setSubject(subject);
//            message.setTo(to);
//            message.setText(content, isHtmlFormat);
//
//
//            if (!multipartFile.isEmpty()) {
//                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//                InputStreamSource source = new InputStreamSource() {
//                    @Override
//                    public InputStream getInputStream() throws IOException {
//                        return multipartFile.getInputStream();
//                    }
//                };
//                message.addAttachment(fileName, source);
//            }
//
//
//            emailSender.send(mimeMessage);
//        } catch (MessagingException ex) {
//            throw new IllegalStateException("Failed to send email to " + to);
//        } catch (UnsupportedEncodingException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

//    @Override
//    public void send(String subject, String content, String[] from, String to, boolean isHtmlFormat) {
//        try {
//            MimeMessage mimeMessage = emailSender.createMimeMessage();
//            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "utf-8");
//
//            // from[0]: email gửi, from[1] : tên alias ng gửi thay thế cho email
//            message.setFrom(from[0], from[1]);
//            message.setSubject(subject);
//            message.setTo(to);
//            content += "<hr><img src='cid:logoImg' />";
//            message.setText(content, isHtmlFormat);
//
//            ClassPathResource resourceImg = new ClassPathResource("/static/assets/homepage/images/Asset 4.png");
//            message.addInline("logoImg", resourceImg);
//
//            emailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            throw new IllegalStateException("Failed to send email to " + to);
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void sendInvoiceAsAttachment(String subject, String body, String recipientEmail, String attachmentName, InputStream attachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("your-email@example.com");
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(body);

            helper.addAttachment(attachmentName, new ByteArrayResource(inputStreamToByteArray(attachment)));

            emailSender.send(message);
        } catch (MessagingException | IOException e) {
            // Xử lý lỗi khi gửi email
            e.printStackTrace(); // Thay bằng xử lý lỗi cụ thể hoặc log lỗi
        }
    }

    private byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    @Override
    public void send(String subject, String content, String[] from, String to, boolean isHtmlFormat, MultipartFile multipartFile) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "utf-8");

            // from[0]: email gửi, from[1] : tên alias ng gửi thay thế cho email
            message.setFrom(from[0], from[1]);
            message.setSubject(subject);
            message.setTo(to);
            message.setText(content, isHtmlFormat);


            if (!multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                InputStreamSource source = new InputStreamSource() {
                    @Override
                    public InputStream getInputStream() throws IOException {
                        return multipartFile.getInputStream();
                    }
                };
                message.addAttachment(fileName, source);
            }


            emailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            throw new IllegalStateException("Failed to send email to " + to);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void send(String subject, String content, String[] from, String to, boolean isHtmlFormat) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "utf-8");

            // from[0]: email gửi, from[1] : tên alias ng gửi thay thế cho email
            message.setFrom(from[0], from[1]);
            message.setSubject(subject);
            message.setTo(to);
            content += "<hr><img src='cid:logoImg' />";
            message.setText(content, isHtmlFormat);

            ClassPathResource resourceImg = new ClassPathResource("/static/assets/homepage/images/Asset 4.png");
            message.addInline("logoImg", resourceImg);

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email to " + to);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
