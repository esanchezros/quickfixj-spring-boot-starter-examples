package io.allune.quickfixj.spring.boot.starter.examples.client;

import lombok.extern.slf4j.Slf4j;
import quickfix.FieldNotFound;
import quickfix.IncorrectTagValue;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.fix41.MessageCracker;

@Slf4j
public class ApplicationMessageCracker extends MessageCracker {

    @Override
    public void onMessage(quickfix.fix41.OrderCancelRequest orderCancelRequest, SessionID sessionID)
            throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {

        // Handle the message here
        log.info("*****************");
        log.info("Message received for sessionID={}: {}", sessionID, orderCancelRequest);
        log.info("*****************");
    }
}