package io.allune.quickfixj.spring.boot.starter.examples.sender;

import io.allune.quickfixj.spring.boot.starter.template.QuickFixJTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import quickfix.Acceptor;
import quickfix.Message;
import quickfix.SessionID;
import quickfix.StringField;
import quickfix.field.ClOrdID;
import quickfix.field.OrigClOrdID;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.Text;

import java.util.HashMap;
import java.util.Map;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class SenderController {

    private static final Map<String, Map<String, Message>> messageMap = createMessageMap();

    private static HashMap<String, Map<String, Message>> createMessageMap() {
        HashMap<String, Map<String, Message>> stringMapHashMap = new HashMap<>();
        stringMapHashMap.put("FIX.4.1", new HashMap<String, Message>() {
            {
                put("OrderCancelRequest", new quickfix.fix41.OrderCancelRequest(
                        new OrigClOrdID("123"),
                        new ClOrdID("321"),
                        new Symbol("LNUX"),
                        new Side(Side.BUY)));
            }
        });
        return stringMapHashMap;
    }

    private final QuickFixJTemplate quickFixJTemplate;
    private final Acceptor serverAcceptor;

    public SenderController(QuickFixJTemplate quickFixJTemplate, Acceptor serverAcceptor) {
        this.quickFixJTemplate = quickFixJTemplate;
        this.serverAcceptor = serverAcceptor;
    }

    @RequestMapping("/send-message")
    @ResponseStatus(OK)
    public void sendMessage(@RequestParam String beginString, @RequestParam String messageType) {

        Map<String, Message> stringMessageMap = messageMap.get(beginString);
        Message message = stringMessageMap.get(messageType);
        message.setField(new StringField(Text.FIELD, "Text: " + randomUUID().toString()));

        // TODO: Create autoconfigurable rest controllers to get messages from message store
        // TODO: Create session lookup service bean
        SessionID sessionID = serverAcceptor.getSessions().stream()
                .filter(id -> id.getBeginString().equals(beginString))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        quickFixJTemplate.send(message, sessionID);
    }
}
