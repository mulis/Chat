package org.mulis.chat.controller;

public enum ChatSystemMessage {

    USER_SIGNED_IN("User signed in."),
    USER_SIGNED_OUT("User signed out."),
    USER_NOT_SIGNED_IN("User not signed in."),
    USER_ALREADY_SIGNED_IN("User already signed in."),
    USER_ALREADY_SIGNED_OUT("User already signed out."),

    USER_NICKNAME_IS_RESERVED("User nickname is reserved."),
    SENDER_NICKNAME_IS_RESERVED("Sender nickname is reserved."),
    USER_PASSWORD_NOT_CORRECT("User password not correct."),

    USER_LOGGED_IN("User logged in."),
    USER_LOGGED_OUT("User logged out."),
    USER_NOT_LOGGED_IN("User not logged in."),
    USER_ALREADY_LOGGED_IN("User already logged in."),
    USER_ALREADY_LOGGED_OUT("User already logged out."),

    USER_SIGNED_IN_AND_LOGGED_IN("User signed in and logged in."),
    USER_SIGNED_OUT_AND_LOGGED_OUT("User signed out and logged out."),

    SENDER_NOT_SIGNED_IN("Sender not signed in."),
    SENDER_NOT_LOGGED_IN("Sender not logged in."),
    RECEIVER_NOT_SIGNED_IN("Receiver not signed in."),
    RECEIVER_NOT_LOGGED_IN("Receiver not logged in."),

    USER_POST_MESSAGE("User post message."),
    USER_GET_MESSAGES("User get messages."),

    SYSTEM_STATE_UNKNOWN("System state unknown.");

    private String message;

    ChatSystemMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
