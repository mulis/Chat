package org.mulis.chat.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class ChatMessage {

    private final String senderNickname;
    private final String receiverNickname;
    private final String text;

    @JsonCreator
    public ChatMessage(
            @JsonProperty("senderNickname") String senderNickname,
            @JsonProperty("receiverNickname") String receiverNickname,
            @JsonProperty("text") String text) {
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

    @Override
    public String toString() {
        return "ChatGetMessage{" +
                "senderNickname=" + senderNickname + ", " +
                "receiverNickname=" + receiverNickname + ", " +
                "text='" + text + "'" +
                "}";
    }

}
