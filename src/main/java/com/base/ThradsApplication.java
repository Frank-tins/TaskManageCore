package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "com.base.config"
        ,"com.base.support.task"
        ,"com.base.support.thread"
})
@EnableScheduling
@EnableAsync
public class ThradsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThradsApplication.class, args);
    }
}
