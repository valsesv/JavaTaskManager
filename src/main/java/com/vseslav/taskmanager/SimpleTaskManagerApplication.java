package com.vseslav.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SimpleTaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleTaskManagerApplication.class, args);
    }

}
