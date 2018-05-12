package com.tangly;

import com.tangly.base.BaseMybatisMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * date: 2018/5/10 11:05 <br/>
 *
 * @author Administrator
 * @since JDK 1.7
 */
@SpringBootApplication
@MapperScan(basePackages = "com.tangly.mapper", markerInterface = BaseMybatisMapper.class)
public class CommonWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonWebApplication.class, args);
    }
}
