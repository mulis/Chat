package org.mulis.chat.controller;

public class LogoutRequestBody {

    private final String nickname;
    private final String password;


    public LogoutRequestBody(String nickname, int color, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

}
