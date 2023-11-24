package com.example.project.admin.payment.config;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class TransactionDTO implements Serializable {
    private String status;
    private String message;
}
