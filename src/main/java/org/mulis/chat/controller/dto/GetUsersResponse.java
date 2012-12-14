package org.mulis.chat.controller.dto;

import org.mulis.chat.model.ChatUser;

import java.util.Collection;

public class GetUsersResponse extends ChatResponse {

    private Collection<ChatUser> users;

    public Collection<ChatUser> getUsers() {
        return users;
    }

    public void setUsers(Collection<ChatUser> users) {
        this.users = users;
    }

}
