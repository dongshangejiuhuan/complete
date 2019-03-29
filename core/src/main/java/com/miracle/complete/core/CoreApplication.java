package com.miracle.complete.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author 徐宏坤
 */
@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        System.out.println(CoreApplication.class.getName() + " starting...");
        SpringApplication.run(CoreApplication.class, args);
    }

}
