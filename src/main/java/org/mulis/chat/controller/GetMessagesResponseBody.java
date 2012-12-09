package org.mulis.chat.controller;

import org.mulis.chat.model.ChatMessage;

import java.util.LinkedList;

public class GetMessagesResponseBody {

    LinkedList<ChatMessage> messages;

    public LinkedList<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<ChatMessage> messages) {
        this.messages = messages;
    }

}
