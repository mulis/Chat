package org.mulis.chat.model;

import java.util.Date;

public class ChatMessage {

    private final int index;
    private final Date date;
    private final ChatUser sender;
    private final ChatUser receiver;
    private final String text;

    public ChatMessage(int index, ChatUser sender, ChatUser receiver, String text) {
        this.index = index;
        this.date = new Date();
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public Date getDate() {
        return date;
    }

    public ChatUser getSender() {
        return sender;
    }

    public ChatUser getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "index=" + index + ", " +
                "date=" + date + ", " +
                "sender=" + sender + ", " +
                "receiver=" + receiver + ", " +
                "text='" + text + "'" +
                "}";
    }

}
