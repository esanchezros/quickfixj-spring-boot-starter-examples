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
package io.allune.quickfixj.spring.boot.starter.examples.sender.controller;

import io.allune.quickfixj.spring.boot.starter.template.QuickFixJTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import quickfix.Message;
import quickfix.SessionID;
import quickfix.StringField;
import quickfix.field.ClOrdID;
import quickfix.field.OrigClOrdID;
import quickfix.field.QuoteID;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.Text;

import java.util.HashMap;
import java.util.Map;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.OK;
import static quickfix.FixVersions.BEGINSTRING_FIX41;
import static quickfix.FixVersions.BEGINSTRING_FIXT11;

@RequestMapping
public class SenderController implements IController {

	private static final Map<String, Map<String, Message>> messageMap = createMessageMap();
	private static final Map<String, SessionID> serverSessionIDMap = createServerSessionIDMap();
	private static final Map<String, SessionID> clientSessionIDMap = createClientSessionIDMap();

	private final QuickFixJTemplate quickFixJTemplate;

	public SenderController(QuickFixJTemplate quickFixJTemplate) {
		this.quickFixJTemplate = quickFixJTemplate;
	}

	private static Map<String, Map<String, Message>> createMessageMap() {
		HashMap<String, Map<String, Message>> stringMapHashMap = new HashMap<>();
		stringMapHashMap.put(BEGINSTRING_FIX41, initialiseFix41MessageMap());
		stringMapHashMap.put(BEGINSTRING_FIXT11, initialiseFix50MessageMap());
		return stringMapHashMap;
	}

	private static Map<String, SessionID> createServerSessionIDMap() {
		HashMap<String, SessionID> stringMapHashMap = new HashMap<>();
		stringMapHashMap.put(BEGINSTRING_FIX41, new SessionID(BEGINSTRING_FIX41, "EXEC", "BANZAI"));
		stringMapHashMap.put(BEGINSTRING_FIXT11, new SessionID(BEGINSTRING_FIXT11, "EXEC", "BANZAI"));
		return stringMapHashMap;
	}

	private static Map<String, SessionID> createClientSessionIDMap() {
		HashMap<String, SessionID> stringMapHashMap = new HashMap<>();
		stringMapHashMap.put(BEGINSTRING_FIX41, new SessionID(BEGINSTRING_FIX41, "BANZAI", "EXEC"));
		stringMapHashMap.put(BEGINSTRING_FIXT11, new SessionID(BEGINSTRING_FIXT11, "BANZAI", "EXEC"));
		return stringMapHashMap;
	}

	private static Map<String, Message> initialiseFix41MessageMap() {
		Map<String, Message> messageMap = new HashMap<>();
		messageMap.put("OrderCancelRequest", new quickfix.fix41.OrderCancelRequest(
				new OrigClOrdID("123"),
				new ClOrdID("321"),
				new Symbol("LNUX"),
				new Side(Side.BUY)));
		return messageMap;
	}

	private static Map<String, Message> initialiseFix50MessageMap() {
		Map<String, Message> messageMap = new HashMap<>();
		messageMap.put("Quote", new quickfix.fix50.Quote(new QuoteID("123")));
		return messageMap;
	}

	@RequestMapping("/send-client-message")
	@ResponseStatus(OK)
	public void sendMessageToClient(
			@RequestParam(name = "fixVersion") String fixVersion,
			@RequestParam(name = "messageType") String messageType
	) {
		Map<String, Message> stringMessageMap = messageMap.get(fixVersion);
		Message message = stringMessageMap.get(messageType);
		message.setField(new StringField(Text.FIELD, "Text: " + randomUUID()));

		SessionID sessionID = clientSessionIDMap.get(fixVersion);
		quickFixJTemplate.send(message, sessionID);
	}

	@RequestMapping("/send-server-message")
	@ResponseStatus(OK)
	public void sendMessageToServer(
			@RequestParam(name = "fixVersion") String fixVersion,
			@RequestParam(name = "messageType") String messageType
	) {

		Map<String, Message> stringMessageMap = messageMap.get(fixVersion);
		Message message = stringMessageMap.get(messageType);
		message.setField(new StringField(Text.FIELD, "Text: " + randomUUID().toString()));

		SessionID sessionID = serverSessionIDMap.get(fixVersion);
		quickFixJTemplate.send(message, sessionID);
	}
}
