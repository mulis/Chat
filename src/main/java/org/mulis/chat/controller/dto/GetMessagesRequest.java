package org.mulis.chat.controller.dto;

public class GetMessagesRequest {

    private String nickname;
    private String password;
    private int lastMessageIndex;

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

    public int getLastMessageIndex() {
        return lastMessageIndex;
    }

    public void setLastMessageIndex(int lastMessageIndex) {
        this.lastMessageIndex = lastMessageIndex;
    }

}
