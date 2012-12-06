package org.mulis.chat.model;

import java.util.Date;

public class ChatMessage {

    private final Date date;
    private final ChatUser userFrom;
    private final ChatUser userTo;
    private final String text;

    public ChatMessage(ChatUser userFrom, ChatUser userTo, String text) {
        this.date = new Date();
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public ChatUser getUserFrom() {
        return userFrom;
    }

    public ChatUser getUserTo() {
        return userTo;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "date=" + date + ", " +
                "userFrom=" + userFrom + ", " +
                "userTo=" + userTo + ", " +
                "text='" + text + "'" +
                "}";
    }

}
