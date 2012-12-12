package org.mulis.chat.controller;

public enum ChatErrorCode {

    OK(0),
    IGNORE(10),
    STOP(20),
    FATAL(30);

    private final int code;

    ChatErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
