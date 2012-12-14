package org.mulis.chat.model;

public enum ReservedChatUser {

    ADMIN("Admin", "minad", "red", true, -1),
    ANY("", "", "green", true, -1);

    private ChatUser user;

    ReservedChatUser(String nickname, String password, String color, boolean logged, int lastMessageId) {
        user = new SystemChatUser(nickname, password, color, logged, lastMessageId);
    }

    public ChatUser getUser() {
        return user;
    }

}
