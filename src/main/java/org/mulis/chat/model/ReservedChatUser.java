package org.mulis.chat.model;

public enum ReservedChatUser {

    ADMIN("Admin", 16711680),
    ANY("Any", 16711680);

    private ChatUser user;

    ReservedChatUser(String nickname, int color) {
        user = new ChatUser(nickname, color);
    }

    public ChatUser getUser() {
        return user;
    }

}
