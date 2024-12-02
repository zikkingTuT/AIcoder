package com.china.powerms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin  // 启用跨域
public class PowermsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowermsApplication.class, args);
    }

}
