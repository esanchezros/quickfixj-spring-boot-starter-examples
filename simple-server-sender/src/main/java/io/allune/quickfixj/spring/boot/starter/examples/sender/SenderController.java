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

import io.allune.quickfixj.spring.boot.starter.template.QuickFixJTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import quickfix.Acceptor;
import quickfix.Field;
import quickfix.Message;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.StringField;
import quickfix.field.AvgPx;
import quickfix.field.ClOrdID;
import quickfix.field.CumQty;
import quickfix.field.ExecID;
import quickfix.field.ExecType;
import quickfix.field.LeavesQty;
import quickfix.field.MarketSegmentID;
import quickfix.field.OfferPx;
import quickfix.field.OnBehalfOfCompID;
import quickfix.field.OrdStatus;
import quickfix.field.OrdType;
import quickfix.field.OrderID;
import quickfix.field.OrderQty;
import quickfix.field.OrigClOrdID;
import quickfix.field.Product;
import quickfix.field.QuoteID;
import quickfix.field.QuoteReqID;
import quickfix.field.SettlDate;
import quickfix.field.SettlType;
import quickfix.field.Side;
import quickfix.field.Spread;
import quickfix.field.Symbol;
import quickfix.field.Text;
import quickfix.field.TradePublishIndicator;
import quickfix.field.TransactTime;
import quickfix.fix42.QuoteRequest;
import quickfix.fix44.ExecutionReport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.OK;
import static quickfix.FixVersions.BEGINSTRING_FIX41;
import static quickfix.FixVersions.BEGINSTRING_FIXT11;

@RestController
public class SenderController {

	private static final Map<String, Map<String, Message>> messageMap = createMessageMap();
	private final QuickFixJTemplate quickFixJTemplate;
	private final Acceptor serverAcceptor;

	public SenderController(QuickFixJTemplate serverQuickFixJTemplate, Acceptor serverAcceptor) {
		this.quickFixJTemplate = serverQuickFixJTemplate;
		this.serverAcceptor = serverAcceptor;
	}

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
	
	@GetMapping(path = "/execution-report")
	public ResponseEntity<?> executionReport() throws SessionNotFound{
		ExecutionReport executionReport = new ExecutionReport(new OrderID("3-2-805331618T-0-0"), new ExecID("3-2-805331618T-0-0"), new ExecType(ExecType.TRADE), 
				new OrdStatus(OrdStatus.FILLED), new Side(Side.BUY), new LeavesQty(0), new CumQty(1000000), new AvgPx(5.57765));
		SessionID sessionID = new SessionID("FIX.4.4", "EXEC", "BANZAI");
		OnBehalfOfCompID onBehalfOfCompId = new OnBehalfOfCompID("FX");
		executionReport.set(new TransactTime());
		executionReport.set(new SettlType(SettlType.REGULAR_FX_SPOT_SETTLEMENT));
		executionReport.set(new SettlDate("20201016"));
		executionReport.set(new OrderQty(1000000));
		executionReport.set(new Spread(0));
		executionReport.set(new OrdType(OrdType.MARKET));
		executionReport.set(new Product(Product.CURRENCY));
//		executionReport.set(new TradePublishIndicator(TradePublishIndicator.DO_NOT_PUBLISH_TRADE));
		executionReport.set(new ClOrdID("3-2-805331618T-0-0"));
//		executionReport.set(new OfferPx(5.57765));
		executionReport.set(new Symbol("USD/BRL"));
		
		
		Session.sendToTarget(executionReport, sessionID);
		return ResponseEntity.ok("OK");
	}
//	AllocationInstruction

	private QuoteRequest createQuoteRequest(UUID operationId) {
		return new QuoteRequest(new QuoteReqID(operationId.toString()));
	}
}
