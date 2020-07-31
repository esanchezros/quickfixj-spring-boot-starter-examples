CREATE USER IF NOT EXISTS 'testuser'@'%' IDENTIFIED BY 'testuser';
GRANT ALL PRIVILEGES ON quickfix.* TO 'testuser'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

USE quickfix;

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


CREATE TABLE messages
(
    beginstring       CHAR(8)     NOT NULL,
    sendercompid      VARCHAR(64) NOT NULL,
    sendersubid       VARCHAR(64) NOT NULL,
    senderlocid       VARCHAR(64) NOT NULL,
    targetcompid      VARCHAR(64) NOT NULL,
    targetsubid       VARCHAR(64) NOT NULL,
    targetlocid       VARCHAR(64) NOT NULL,
    session_qualifier VARCHAR(64) NOT NULL,
    msgseqnum         INT         NOT NULL,
    message           TEXT        NOT NULL,
    PRIMARY KEY (beginstring, sendercompid, sendersubid, senderlocid,
                 targetcompid, targetsubid, targetlocid, session_qualifier,
                 msgseqnum)
);


CREATE TABLE messages_log
(
    id                INT UNSIGNED NOT NULL AUTO_INCREMENT,
    time              DATETIME     NOT NULL,
    beginstring       CHAR(8)      NOT NULL,
    sendercompid      VARCHAR(64)  NOT NULL,
    sendersubid       VARCHAR(64)  NOT NULL,
    senderlocid       VARCHAR(64)  NOT NULL,
    targetcompid      VARCHAR(64)  NOT NULL,
    targetsubid       VARCHAR(64)  NOT NULL,
    targetlocid       VARCHAR(64)  NOT NULL,
    session_qualifier VARCHAR(64)  NOT NULL,
    text              TEXT         NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE event_log
(
    id                INT UNSIGNED NOT NULL AUTO_INCREMENT,
    time              DATETIME     NOT NULL,
    beginstring       CHAR(8)      NOT NULL,
    sendercompid      VARCHAR(64)  NOT NULL,
    sendersubid       VARCHAR(64)  NOT NULL,
    senderlocid       VARCHAR(64)  NOT NULL,
    targetcompid      VARCHAR(64)  NOT NULL,
    targetsubid       VARCHAR(64)  NOT NULL,
    targetlocid       VARCHAR(64)  NOT NULL,
    session_qualifier VARCHAR(64),
    text              TEXT         NOT NULL,
    PRIMARY KEY (id)
);
