package org.mulis.chat.controller.dto;

import org.mulis.chat.model.ChatMessage;

public class PostMessageRequest {

    private ChatMessage message;
    private String password;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
