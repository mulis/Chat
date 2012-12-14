package org.mulis.chat.controller;

public enum ChatStatus {

    USER_SIGNED_IN("User %s signed in."),
    USER_SIGNED_OUT("User %s signed out."),
    USER_NOT_SIGNED_IN("User %s not signed in."),
    USER_ALREADY_SIGNED_IN("User %s already signed in."),
    USER_ALREADY_SIGNED_OUT("User %s already signed out."),

    USER_NICKNAME_IS_RESERVED("User nickname %s is reserved."),
    SENDER_NICKNAME_IS_RESERVED("Sender nickname %s is reserved."),
    USER_PASSWORD_NOT_CORRECT("User password %s not correct."),

    USER_LOGGED_IN("User %s logged in."),
    USER_LOGGED_OUT("User %s logged out."),
    USER_NOT_LOGGED_IN("User %s not logged in."),
    USER_ALREADY_LOGGED_IN("User %s already logged in."),
    USER_ALREADY_LOGGED_OUT("User %s already logged out."),

    USER_SIGNED_IN_AND_LOGGED_IN("User %s signed in and logged in."),
    USER_SIGNED_OUT_AND_LOGGED_OUT("User %s signed out and logged out."),

    USER_PASSWORD_CHANGED("User %s password changed"),
    USER_COLOR_CHANGED("User %s color changed"),

    SENDER_NOT_SIGNED_IN("Sender %s not signed in."),
    SENDER_NOT_LOGGED_IN("Sender %s not logged in."),
    RECEIVER_NOT_SIGNED_IN("Receiver %s not signed in."),
    RECEIVER_NOT_LOGGED_IN("Receiver %s not logged in."),

    USER_POST_MESSAGE("User %s post message."),
    USER_GET_MESSAGES("User %s get messages."),
    USER_GET_USERS("User %s get users."),

    SYSTEM_STATE_UNKNOWN("System state unknown.");

    private final String text;

    ChatStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String format(Object... arguments) {
        return String.format(text, arguments);
    }

    @Override
    public String toString() {
        return text;
    }

}
