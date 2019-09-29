package io.allune.quickfixj.spring.boot.starter.examples.sender;

import io.allune.quickfixj.spring.boot.starter.autoconfigure.template.QuickFixJTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import quickfix.Acceptor;
import quickfix.SessionID;
import quickfix.field.ClOrdID;
import quickfix.field.OrigClOrdID;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.Text;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class SenderController {

    private final QuickFixJTemplate quickFixJTemplate;
    private final Acceptor serverAcceptor;

    public SenderController(QuickFixJTemplate quickFixJTemplate, Acceptor serverAcceptor) {
        this.quickFixJTemplate = quickFixJTemplate;
        this.serverAcceptor = serverAcceptor;
    }

    @RequestMapping("/sender")
    @ResponseStatus(OK)
    public void sendMessage() {
        quickfix.fix41.OrderCancelRequest message = new quickfix.fix41.OrderCancelRequest(
                new OrigClOrdID("123"),
                new ClOrdID("321"),
                new Symbol("LNUX"),
                new Side(Side.BUY));

        message.set(new Text("Cancel My Order!"));

        SessionID sessionID = serverAcceptor.getSessions().stream()
                .filter(id -> id.getBeginString().equals("FIX.4.1"))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        quickFixJTemplate.send(message, sessionID);
    }
}
