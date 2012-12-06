package org.mulis.chat.model;

public class ChatUser {

    private final String nickname;
    private int color;
    private boolean logged;

    public ChatUser(String nickname, int color) {
        this.nickname = nickname;
        this.color = color;
    }

    public String getNickname() {
        return nickname;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    @Override
    public String toString() {
        return "ChatUser{" +
                "nickname='" + nickname + "', " +
                "color=" + Integer.toHexString(color) + "', " +
                "logged='" + logged + "', " +
                "}";
    }

}
