package org.mulis.chat.model;

public class ChatUser {

    private String nickname;
    private String color;
    private boolean logged;

    public ChatUser(String nickname, String color) {
        this.nickname = nickname;
        this.color = color;
        this.logged = false;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    @Override
    public String toString() {
        return "ChatUser{" +
                "nickname='" + nickname + "', " +
                "color=" + color + "', " +
                "logged='" + logged + "', " +
                "}";
    }

}
