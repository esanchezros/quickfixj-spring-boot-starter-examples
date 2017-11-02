package com.allune.quickfixj.spring.boot.starter.examples.client;

import com.allune.quickfixj.spring.boot.starter.EnableQuickFixJClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import quickfix.*;

@EnableQuickFixJClient
@SpringBootApplication
public class AppClient implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AppClient.class);

    public static void main(String[] args) {
        SpringApplication.run(AppClient.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Joining thread, you can press Ctrl+C to shutdown application");
        Thread.currentThread().join();
    }

    @Bean
    public Application clientApplication() {
        return new ClientApplicationAdapter();
    }

    @Bean
    public Acceptor clientAcceptor(quickfix.Application clientApplication, MessageStoreFactory clientMessageStoreFactory,
                                   SessionSettings clientSessionSettings, LogFactory clientLogFactory,
                                   MessageFactory clientMessageFactory) throws ConfigError {

        return new ThreadedSocketAcceptor(clientApplication, clientMessageStoreFactory, clientSessionSettings,
                clientLogFactory, clientMessageFactory);

    }
}
