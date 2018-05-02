package com.utils;

public class PeerFakeRequestResponse {
    private final PeerMessageType sendMessageType;
    private final PeerMessageType receiveMessageType;
    private final ErrorSignalType errorSignalType;

    public PeerFakeRequestResponse(PeerMessageType sendMessageType, PeerMessageType receiveMessageType, ErrorSignalType errorSignalType) {
        this.sendMessageType = sendMessageType;
        this.receiveMessageType = receiveMessageType;
        this.errorSignalType = errorSignalType;
    }

    public ErrorSignalType getErrorSignalType() {
        return errorSignalType;
    }

    public PeerMessageType getSendMessageType() {
        return sendMessageType;
    }

    public PeerMessageType getReceiveMessageType() {
        return receiveMessageType;
    }
}
