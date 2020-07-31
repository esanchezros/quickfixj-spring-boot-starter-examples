DROP TABLE messages IF EXISTS;
DROP TABLE messages_log IF EXISTS;
DROP TABLE event_log IF EXISTS;
DROP TABLE sessions IF EXISTS;

CREATE TABLE messages
(
    beginstring       CHAR(8)       NOT NULL,
    sendercompid      VARCHAR(64)   NOT NULL,
    sendersubid       VARCHAR(64)   NOT NULL,
    senderlocid       VARCHAR(64)   NOT NULL,
    targetcompid      VARCHAR(64)   NOT NULL,
    targetsubid       VARCHAR(64)   NOT NULL,
    targetlocid       VARCHAR(64)   NOT NULL,
    session_qualifier VARCHAR(64)   NOT NULL,
    msgseqnum         INT           NOT NULL,
    message           VARCHAR(2048) NOT NULL,
    PRIMARY KEY (beginstring, sendercompid, sendersubid, senderlocid,
                 targetcompid, targetsubid, targetlocid, session_qualifier,
                 msgseqnum)
);

CREATE TABLE messages_log
(
    id                INT IDENTITY PRIMARY KEY,
    time              DATETIME      NOT NULL,
    beginstring       CHAR(8)       NOT NULL,
    sendercompid      VARCHAR(64)   NOT NULL,
    sendersubid       VARCHAR(64)   NOT NULL,
    senderlocid       VARCHAR(64)   NOT NULL,
    targetcompid      VARCHAR(64)   NOT NULL,
    targetsubid       VARCHAR(64)   NOT NULL,
    targetlocid       VARCHAR(64)   NOT NULL,
    session_qualifier VARCHAR(64)   NOT NULL,
    text              VARCHAR(2048) NOT NULL
);

CREATE TABLE event_log
(
    id                INT IDENTITY PRIMARY KEY,
    time              DATETIME      NOT NULL,
    beginstring       CHAR(8)       NOT NULL,
    sendercompid      VARCHAR(64)   NOT NULL,
    sendersubid       VARCHAR(64)   NOT NULL,
    senderlocid       VARCHAR(64)   NOT NULL,
    targetcompid      VARCHAR(64)   NOT NULL,
    targetsubid       VARCHAR(64)   NOT NULL,
    targetlocid       VARCHAR(64)   NOT NULL,
    session_qualifier VARCHAR(64),
    text              VARCHAR(2048) NOT NULL
);

CREATE TABLE sessions
(
    beginstring       CHAR(8)     NOT NULL,
    sendercompid      VARCHAR(64) NOT NULL,
    sendersubid       VARCHAR(64) NOT NULL,
    senderlocid       VARCHAR(64) NOT NULL,
    targetcompid      VARCHAR(64) NOT NULL,
    targetsubid       VARCHAR(64) NOT NULL,
    targetlocid       VARCHAR(64) NOT NULL,
    session_qualifier VARCHAR(64) NOT NULL,
    creation_time     DATETIME    NOT NULL,
    incoming_seqnum   INT         NOT NULL,
    outgoing_seqnum   INT         NOT NULL,
    PRIMARY KEY (beginstring, sendercompid, sendersubid, senderlocid,
                 targetcompid, targetsubid, targetlocid, session_qualifier)
);
