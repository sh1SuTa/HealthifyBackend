package com.putileaf.healthify;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.putileaf.healthify.mapper")
public class HealthifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthifyApplication.class, args);
    }

}
