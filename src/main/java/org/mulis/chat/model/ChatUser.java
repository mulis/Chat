package org.mulis.chat.model;

public class ChatUser {

    private String nickname;
    private String password;
    private String color;
    private boolean logged;
    private int lastMessageIndex;

    public ChatUser(String nickname, String password, String color, boolean logged, int lastMessageIndex) {
        this.nickname = nickname;
        this.password = password;
        this.color = color;
        this.logged = logged;
        this.lastMessageIndex = lastMessageIndex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public int getLastMessageIndex() {
        return lastMessageIndex;
    }

    public void setLastMessageIndex(int lastMessageIndex) {
        this.lastMessageIndex = lastMessageIndex;
    }

    @Override
    public String toString() {
        return "ChatUser{" +
                "nickname=" + nickname + ", " +
                "password=" + password + ", " +
                "color=" + color + "', " +
                "logged=" + logged + ", " +
                "lastMessageIndex=" + lastMessageIndex +
                "}";
    }

}
