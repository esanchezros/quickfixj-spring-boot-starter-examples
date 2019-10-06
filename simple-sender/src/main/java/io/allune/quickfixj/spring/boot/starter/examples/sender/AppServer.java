package io.allune.quickfixj.spring.boot.starter.examples.sender;

import io.allune.quickfixj.spring.boot.starter.EnableQuickFixJServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableQuickFixJServer
@SpringBootApplication
public class AppServer {

    public static void main(String[] args) {
        SpringApplication.run(AppServer.class, args);
    }
}
