package io.allune.quickfixj.spring.boot.starter.examples.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.allune.quickfixj.spring.boot.starter.EnableQuickFixJClient;
import lombok.extern.slf4j.Slf4j;

@EnableQuickFixJClient
@SpringBootApplication
@Slf4j
public class AppClient implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppClient.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Joining thread, you can press Ctrl+C to shutdown application");
        Thread.currentThread().join();
    }
}
