package org.mulis.chat.controller.dto;

import org.mulis.chat.model.ChatPostedMessage;
import org.mulis.chat.model.ChatUser;

import java.util.Collection;

public class GetAllResponse extends ChatResponse {

    private Collection<ChatPostedMessage> messages;
    private Collection<ChatUser> users;

    public Collection<ChatPostedMessage> getMessages() {
        return messages;
    }

    public void setMessages(Collection<ChatPostedMessage> messages) {
        this.messages = messages;
    }

    public Collection<ChatUser> getUsers() {
        return users;
    }

    public void setUsers(Collection<ChatUser> users) {
        this.users = users;
    }

}
