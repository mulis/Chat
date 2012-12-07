package org.mulis.chat.controller;

public class GetMessagesRequestBody {

    private final String nickname;
    private final int index;

    public GetMessagesRequestBody(String nickname, int index) {
        this.nickname = nickname;
        this.index = index;
    }

    public String getNickname() {
        return nickname;
    }

    public int getIndex() {
        return index;
    }

}
