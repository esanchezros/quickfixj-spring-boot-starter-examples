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

import io.allune.quickfixj.spring.boot.starter.model.Create;
import io.allune.quickfixj.spring.boot.starter.model.FromAdmin;
import io.allune.quickfixj.spring.boot.starter.model.FromApp;
import io.allune.quickfixj.spring.boot.starter.model.Logon;
import io.allune.quickfixj.spring.boot.starter.model.Logout;
import io.allune.quickfixj.spring.boot.starter.model.ToAdmin;
import io.allune.quickfixj.spring.boot.starter.model.ToApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationListener {

	@EventListener
	public void handleFromAdmin1(FromAdmin fromAdmin) {
		log.info("fromAdmin: Message={}, SessionId={}", fromAdmin.getMessage(), fromAdmin.getSessionId());
	}

	@EventListener
	public void handleFromAdmin2(FromAdmin fromAdmin) {
		log.info("fromAdmin: Message={}, SessionId={}", fromAdmin.getMessage(), fromAdmin.getSessionId());
	}

	@EventListener
	public void handleFromApp(FromApp fromApp) {
		log.info("fromAdmin: Message={}, SessionId={}", fromApp.getMessage(), fromApp.getSessionId());
	}

	@EventListener
	public void handleCreate(Create create) {
		log.info("onCreate: SessionId={}", create.getSessionId());
	}

	@EventListener
	public void handleLogon(Logon logon) {
		log.info("onLogon: SessionId={}", logon.getSessionId());
	}

	@EventListener
	public void handleLogout(Logout logout) {
		log.info("onLogout: SessionId={}", logout.getSessionId());
	}

	@EventListener
	public void handleToAdmin(ToAdmin toAdmin) {
		log.info("toAdmin: SessionId={}", toAdmin.getSessionId());
	}

	@EventListener
	public void handleToApp(ToApp toApp) {
		log.info("toApp: SessionId={}", toApp.getSessionId());
	}
}
