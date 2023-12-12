package com.example.emos.wx;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan //让设置的XssFilter过滤器生效
public class EmosWxApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmosWxApiApplication.class, args);
    }
}
