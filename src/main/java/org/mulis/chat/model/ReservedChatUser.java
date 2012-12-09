package org.mulis.chat.model;

public enum ReservedChatUser {

    ADMIN("Admin", 16711680),
    ANY("Any", 16711680);

    private class UnlogableChatUser extends ChatUser {

        public UnlogableChatUser(String nickname, String color) {
            super(nickname, color);
        }

        @Override
        public void setLogged(boolean logged) {
        }

    }

    private ChatUser user;

    ReservedChatUser(String nickname, String color) {
        user = new UnlogableChatUser(nickname, color);
    }

    public ChatUser getUser() {
        return user;
    }

}
