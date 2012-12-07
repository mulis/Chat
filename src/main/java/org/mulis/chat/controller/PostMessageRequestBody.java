package org.mulis.chat.controller;

public class PostMessageRequestBody {

    private final String senderNickname;
    private final String receiverNickname;
    private final String text;

    public PostMessageRequestBody(String senderNickname, String receiverNickname, String text) {
        this.senderNickname = senderNickname;
        this.receiverNickname = receiverNickname;
        this.text = text;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public String getText() {
        return text;
    }

}
