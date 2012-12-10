package org.mulis.chat.model;

public enum ReservedChatUser {

    ADMIN("Admin", "minad", "red", true, 0),
    ANY("", "", "green", true, 0);

    private class UnlogableChatUser extends ChatUser {

        public UnlogableChatUser(String nickname, String password, String color, boolean logged, int lastMessageIndex) {
            super(nickname, password, color, logged, lastMessageIndex);
        }

        @Override
        public void setLogged(boolean logged) {
        }

    }

    private ChatUser user;

    ReservedChatUser(String nickname, String password, String color, boolean logged, int lastMessageId) {
        user = new UnlogableChatUser(nickname, password, color, logged, lastMessageId);
    }

    public ChatUser getUser() {
        return user;
    }

}
