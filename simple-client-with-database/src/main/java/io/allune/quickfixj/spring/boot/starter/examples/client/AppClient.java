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
package io.allune.quickfixj.spring.boot.starter.examples.client;

import io.allune.quickfixj.spring.boot.starter.EnableQuickFixJClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import quickfix.Application;
import quickfix.ConfigError;
import quickfix.Initiator;
import quickfix.JdbcLogFactory;
import quickfix.JdbcStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionSettings;
import quickfix.ThreadedSocketInitiator;

@Slf4j
@EnableQuickFixJClient
@SpringBootApplication
public class AppClient implements CommandLineRunner {

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
	public Initiator clientInitiator(quickfix.Application clientApplication, MessageStoreFactory clientMessageStoreFactory,
	                                 SessionSettings clientSessionSettings, LogFactory clientLogFactory,
	                                 MessageFactory clientMessageFactory) throws ConfigError {

		return new ThreadedSocketInitiator(clientApplication, clientMessageStoreFactory, clientSessionSettings,
				clientLogFactory, clientMessageFactory);
	}

	@Bean
	public MessageStoreFactory clientMessageStoreFactory(SessionSettings clientSessionSettings) {
		return new JdbcStoreFactory(clientSessionSettings);
	}

	@Bean
	public LogFactory clientLogFactory(SessionSettings clientSessionSettings) {
		return new JdbcLogFactory(clientSessionSettings);
	}
}
