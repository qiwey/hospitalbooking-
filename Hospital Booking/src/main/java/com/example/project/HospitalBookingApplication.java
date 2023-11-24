package com.example.project;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
@RequiredArgsConstructor
public class HospitalBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalBookingApplication.class, args);
    }

}
