package org.example.bookmarkmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BookMarkManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookMarkManagerApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the BookMark Manager Application!";
    }

}
