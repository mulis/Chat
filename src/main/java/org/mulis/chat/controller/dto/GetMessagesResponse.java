package org.mulis.chat.controller.dto;

import org.mulis.chat.model.ChatPostedMessage;

import java.util.LinkedList;

public class GetMessagesResponse extends ChatResponse {

    private LinkedList<ChatPostedMessage> messages;

    public LinkedList<ChatPostedMessage> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<ChatPostedMessage> messages) {
        this.messages = messages;
    }

}
