/*
 * Copyright 2019 the original author or authors.
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

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.OK;
import static quickfix.FixVersions.BEGINSTRING_FIX41;
import static quickfix.FixVersions.BEGINSTRING_FIXT11;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.allune.quickfixj.spring.boot.starter.template.QuickFixJTemplate;
import quickfix.Acceptor;
import quickfix.Message;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.StringField;
import quickfix.field.ClOrdID;
import quickfix.field.OrigClOrdID;
import quickfix.field.QuoteID;
import quickfix.field.QuoteReqID;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.Text;
import quickfix.fix42.QuoteRequest;

@RestController
public class SenderController {

	private static final Map<String, Map<String, Message>> messageMap = createMessageMap();

	private static HashMap<String, Map<String, Message>> createMessageMap() {
		HashMap<String, Map<String, Message>> stringMapHashMap = new HashMap<>();
		stringMapHashMap.put(BEGINSTRING_FIX41, initialiseFix41MessageMap());
		stringMapHashMap.put(BEGINSTRING_FIXT11, initialiseFix50MessageMap());
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

	private final QuickFixJTemplate quickFixJTemplate;

	private final Acceptor serverAcceptor;

	public SenderController(QuickFixJTemplate serverQuickFixJTemplate, Acceptor serverAcceptor) {
		this.quickFixJTemplate = serverQuickFixJTemplate;
		this.serverAcceptor = serverAcceptor;
	}

	@RequestMapping("/send-message")
	@ResponseStatus(OK)
	public void sendMessage(@RequestParam String fixVersion, @RequestParam String messageType) {

		Map<String, Message> stringMessageMap = messageMap.get(fixVersion);
		Message message = stringMessageMap.get(messageType);
		message.setField(new StringField(Text.FIELD, "Text: " + randomUUID().toString()));

		SessionID sessionID = serverAcceptor.getSessions().stream()
				.filter(id -> id.getBeginString().equals(fixVersion))
				.findFirst()
				.orElseThrow(RuntimeException::new);
		quickFixJTemplate.send(message, sessionID);
	}

	@GetMapping(path = "/path1")
	public ResponseEntity<?> getPath1() throws SessionNotFound {
		QuoteRequest quoteRequest = createQuoteRequest(UUID.randomUUID());
		SessionID sessionID = new SessionID("FIX.4.2", "EXEC", "BANZAI");
		Session.sendToTarget(quoteRequest, sessionID);

		return ResponseEntity.ok("OK");
	}

	@GetMapping(path = "/path2")
	public ResponseEntity<?> getPath2() throws SessionNotFound {
		QuoteRequest quoteRequest = createQuoteRequest(UUID.randomUUID());
		SessionID sessionID = new SessionID("FIX.4.2", "EXEC", "BANZAI");
		Session.sendToTarget(quoteRequest, sessionID);

		return ResponseEntity.ok("OK");

	}

	private QuoteRequest createQuoteRequest(UUID operationId) {
		return new QuoteRequest(new QuoteReqID(operationId.toString()));
	}
}
