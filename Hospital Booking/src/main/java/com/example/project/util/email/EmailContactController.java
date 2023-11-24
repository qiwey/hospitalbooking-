package com.example.project.util.email;

import com.example.project.admin.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class EmailContactController {
    @Autowired
    private EmailService emailService;


    @Value("${spring.mail.username}")
    private String emailsend = null;

    @GetMapping("/lien-he")
    public String ShowContactForm(Model model) {
        ContactForm contactForm = new ContactForm();
        contactForm.setFullName("test");
        model.addAttribute("newContactForm", contactForm);
        return "homepage/contact_form";
    }

    @PostMapping("/lien-he")
    public String ShowContactForm(
//          @RequestParam("file_upload") MultipartFile multipartFile,
            @Valid ContactForm newContactForm,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            log.info("Error in contact form" + result.getAllErrors());
            model.addAttribute("newContactForm", newContactForm);
            return "homepage/contact_form";
        }
//        String fullname = request.getParameter("name");
//        String emailUserContact = request.getParameter("email");
//        String subject = request.getParameter("subject");
//        String content = request.getParameter("content");
//
//        String[] emailfrom = {emailsend, "Hospital Booking"};
//        String mailSubject = fullname + "has sent a message";
//        String mailContent = "<p><b>Sender Name: " + fullname + "</p>";
//        mailContent += "<p><b>Sender E-mail:</b> " + emailUserContact + "</p>";
//        mailContent += "<p><b>Subject: </b>" + subject + "</p>";
//        mailContent += "<p><b>Content: </b>" + content + "</p>";
//
//        // gửi thông tin contact của người dùng về kho email
//        emailService.send(subject, mailContent, emailfrom, emailsend, true, multipartFile);
//
//        //gửi thông báo nhận đc email tới email của khách hàng
//        emailService.send("Request Received", "<p><b>Cảm ơn bạn đã liên hệ, chúng tôi sẽ phản hồi trong thời gian sớm nhất</b></p>", emailfrom, emailUserContact, true);

        return "homepage/noti_contact";
    }
}
