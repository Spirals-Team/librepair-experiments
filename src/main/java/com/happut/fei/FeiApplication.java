package com.happut.fei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.happut.fei.mapper")
public class FeiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeiApplication.class, args);
    }
}
