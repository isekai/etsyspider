package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author doctor
 * @date 2019/9/21
 **/
@SpringBootApplication
@EnableScheduling
public class EtsySpiderApplication {
    public static void main(String[] args) {
        SpringApplication.run(EtsySpiderApplication.class, args);
    }
}
