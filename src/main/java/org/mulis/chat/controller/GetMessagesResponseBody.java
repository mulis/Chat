package org.mulis.chat.controller;

import org.mulis.chat.model.ChatMessage;

import java.util.LinkedList;

public class GetMessagesResponseBody {

    LinkedList<ChatMessage> messages;

    public GetMessagesResponseBody() {
        this.messages = new LinkedList<ChatMessage>();
    }

    public GetMessagesResponseBody(LinkedList<ChatMessage> messagesAfter) {
        this.messages = messagesAfter;
    }

}
