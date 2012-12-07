package org.mulis.chat.controller;

public class LoginRequestBody {

    private final String nickname;
    private final int color;
    private final String password;


    public LoginRequestBody(String nickname, int color, String password) {
        this.nickname = nickname;
        this.color = color;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getColor() {
        return color;
    }

    public String getPassword() {
        return password;
    }

}
