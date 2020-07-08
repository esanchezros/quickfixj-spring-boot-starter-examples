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
package io.allune.quickfixj.spring.boot.starter.examples.sender;

import io.allune.quickfixj.spring.boot.starter.EnableQuickFixJServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import quickfix.Application;
import quickfix.ApplicationAdapter;
import quickfix.ConfigError;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionSettings;
import quickfix.ThreadedSocketInitiator;

@EnableQuickFixJServer
@SpringBootApplication
public class AppServer {

	public static void main(String[] args) {
		SpringApplication.run(AppServer.class, args);
	}

	@Bean
	public Application clientApplication() {
		return new ApplicationAdapter();
	}

	@Bean
	public Initiator clientInitiator(
			quickfix.Application clientApplication,
			MessageStoreFactory clientMessageStoreFactory,
			SessionSettings clientSessionSettings,
			LogFactory clientLogFactory,
			MessageFactory clientMessageFactory
	) throws ConfigError {

		return new ThreadedSocketInitiator(
				clientApplication,
				clientMessageStoreFactory,
				clientSessionSettings,
				clientLogFactory,
				clientMessageFactory
		);
	}
}
