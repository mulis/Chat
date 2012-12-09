package org.mulis.chat.controller;

public class GetMessagesRequestBody {

    private String nickname;
    private int index;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
