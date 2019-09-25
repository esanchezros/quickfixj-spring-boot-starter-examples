package io.allune.quickfixj.spring.boot.starter.examples.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.allune.quickfixj.spring.boot.starter.EnableQuickFixJServer;
import lombok.extern.slf4j.Slf4j;

@EnableQuickFixJServer
@SpringBootApplication
@Slf4j
public class AppServer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppServer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Joining thread, you can press Ctrl+C to shutdown application");
        Thread.currentThread().join();
    }
}
