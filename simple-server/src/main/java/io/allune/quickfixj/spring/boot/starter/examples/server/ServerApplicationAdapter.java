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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.Application;
import quickfix.Message;
import quickfix.SessionID;

public class ServerApplicationAdapter implements Application {

	private static final Logger log = LoggerFactory.getLogger(ServerApplicationAdapter.class);

	@Override
	public void fromAdmin(Message message, SessionID sessionId) {
		log.info("fromAdmin: Message={}, SessionId={}", message, sessionId);
	}

	@Override
	public void fromApp(Message message, SessionID sessionId) {
		log.info("fromApp: Message={}, SessionId={}", message, sessionId);
	}

	@Override
	public void onCreate(SessionID sessionId) {
		log.info("onCreate: SessionId={}", sessionId);
	}

	@Override
	public void onLogon(SessionID sessionId) {
		log.info("onLogon: SessionId={}", sessionId);
	}

	@Override
	public void onLogout(SessionID sessionId) {
		log.info("onLogout: SessionId={}", sessionId);
	}

	@Override
	public void toAdmin(Message message, SessionID sessionId) {
		log.info("toAdmin: Message={}, SessionId={}", message, sessionId);
	}

	@Override
	public void toApp(Message message, SessionID sessionId) {
		log.info("toApp: Message={}, SessionId={}", message, sessionId);
	}
}