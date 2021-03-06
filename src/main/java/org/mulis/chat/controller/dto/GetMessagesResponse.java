package org.mulis.chat.controller.dto;

import org.mulis.chat.model.ChatPostedMessage;

import java.util.Collection;

public class GetMessagesResponse extends ChatResponse {

    private Collection<ChatPostedMessage> messages;

    public Collection<ChatPostedMessage> getMessages() {
        return messages;
    }

    public void setMessages(Collection<ChatPostedMessage> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "GetMessagesResponse{" +
                "messages=" + messages +
                '}';
    }

}
