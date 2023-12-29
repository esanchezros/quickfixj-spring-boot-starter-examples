/*
 * Copyright 2017-2023 the original author or authors.
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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import quickfix.Acceptor;
import quickfix.Application;
import quickfix.ConfigError;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.ThreadedSocketAcceptor;
import quickfix.mina.acceptor.DynamicAcceptorSessionProvider;

import java.net.InetSocketAddress;

import static quickfix.Acceptor.SETTING_ACCEPTOR_TEMPLATE;
import static quickfix.FixVersions.BEGINSTRING_FIX40;
import static quickfix.FixVersions.BEGINSTRING_FIX44;
import static quickfix.SessionSettings.BEGINSTRING;
import static quickfix.mina.acceptor.DynamicAcceptorSessionProvider.WILDCARD;

@EnableQuickFixJServer
@SpringBootApplication
public class SimpleServerDynamicSessions {

	public static void main(String[] args) {
		SpringApplication.run(SimpleServerDynamicSessions.class, args);
	}

	@Bean
	public Application serverApplication() {
		return new ServerApplicationAdapter();
	}

	@Bean
	public Acceptor serverAcceptor(quickfix.Application serverApplication, MessageStoreFactory serverMessageStoreFactory,
	                               SessionSettings serverSessionSettings, LogFactory serverLogFactory,
	                               MessageFactory serverMessageFactory) throws ConfigError {

		ThreadedSocketAcceptor threadedSocketAcceptor = new ThreadedSocketAcceptor(serverApplication, serverMessageStoreFactory, serverSessionSettings,
				serverLogFactory, serverMessageFactory);

		final SessionID anySession = new SessionID(BEGINSTRING_FIX40, WILDCARD, WILDCARD);
		serverSessionSettings.setBool(anySession, SETTING_ACCEPTOR_TEMPLATE, true);
		serverSessionSettings.setString(anySession, BEGINSTRING, BEGINSTRING_FIX44);
		threadedSocketAcceptor.setSessionProvider(
				new InetSocketAddress("0.0.0.0", 9876),
				new DynamicAcceptorSessionProvider(serverSessionSettings, anySession, serverApplication, serverMessageStoreFactory,
						serverLogFactory, serverMessageFactory)
		);
		return threadedSocketAcceptor;
	}

	@Bean
	public MessageStoreFactory serverMessageStoreFactory(SessionSettings serverSessionSettings) {
		return new FileStoreFactory(serverSessionSettings);
	}

	@Bean
	public LogFactory serverLogFactory(SessionSettings serverSessionSettings) {
		return new FileLogFactory(serverSessionSettings);
	}
}
