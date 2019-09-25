package io.allune.quickfixj.spring.boot.starter.examples.server;

import io.allune.quickfixj.spring.boot.starter.EnableQuickFixJServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import quickfix.*;

@EnableQuickFixJServer
@SpringBootApplication
public class AppServer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AppServer.class);

    public static void main(String[] args) {
        SpringApplication.run(AppServer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Joining thread, you can press Ctrl+C to shutdown application");
        Thread.currentThread().join();
    }

    @Bean
    public Application serverApplication() {
        return new ServerApplicationAdapter();
    }

    @Bean
    public Acceptor serverAcceptor(quickfix.Application serverApplication, MessageStoreFactory serverMessageStoreFactory,
                                   SessionSettings serverSessionSettings, LogFactory serverLogFactory,
                                   MessageFactory serverMessageFactory) throws ConfigError {

        return new ThreadedSocketAcceptor(serverApplication, serverMessageStoreFactory, serverSessionSettings,
                serverLogFactory, serverMessageFactory);

    }

    @Bean
    public LogFactory serverLogFactory(SessionSettings serverSessionSettings) {
        return new FileLogFactory(serverSessionSettings);
    }
}
