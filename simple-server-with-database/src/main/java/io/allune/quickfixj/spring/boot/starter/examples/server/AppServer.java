/*
 * Copyright 2017-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.allune.quickfixj.spring.boot.starter.examples.server;

import io.allune.quickfixj.spring.boot.starter.EnableQuickFixJServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import quickfix.Acceptor;
import quickfix.Application;
import quickfix.ConfigError;
import quickfix.JdbcLogFactory;
import quickfix.JdbcStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionSettings;
import quickfix.ThreadedSocketAcceptor;

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
	public MessageStoreFactory serverMessageStoreFactory(SessionSettings serverSessionSettings) {
		return new JdbcStoreFactory(serverSessionSettings);
	}

	@Bean
	public LogFactory serverLogFactory(SessionSettings serverSessionSettings) {
		return new JdbcLogFactory(serverSessionSettings);
	}
}
