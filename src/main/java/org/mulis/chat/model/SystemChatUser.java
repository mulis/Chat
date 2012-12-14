package org.mulis.chat.model;

class SystemChatUser extends ChatUser {

    public SystemChatUser(String nickname, String password, String color, boolean logged, int lastMessageIndex) {
        super(nickname, password, color, logged, lastMessageIndex);
    }

    @Override
    public void setNickname(String nickname) {
    }

    @Override
    public void setPassword(String password) {
    }

    @Override
    public void setColor(String color) {
    }

    @Override
    public void setLogged(boolean logged) {
    }

    @Override
    public void setLastMessageIndex(int lastMessageIndex) {
    }

}
