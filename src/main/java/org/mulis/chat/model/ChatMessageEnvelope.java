package org.mulis.chat.model;

import java.util.Date;

public class ChatMessageEnvelope {

    private final int index;
    private final Date date;
    private final ChatMessage message;

    public ChatMessageEnvelope(int index, Date date, ChatMessage message) {
        this.index = index;
        this.date = date;
        this.message = message;
    }

    public int getIndex() {
        return index;
    }

    public Date getDate() {
        return date;
    }

    public ChatMessage getMessage() {
        return message;
    }

}
