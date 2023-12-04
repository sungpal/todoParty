package com.sparta.todoparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TodoPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoPartyApplication.class, args);
    }

}