package org.mulis.chat.model;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

@Service
public class ChatModel {

    private final HashMap<String, ChatUser> users = new HashMap<String, ChatUser>() {{
        put(ReservedChatUser.ADMIN.getUser().getNickname(), ReservedChatUser.ADMIN.getUser());
        put(ReservedChatUser.ANY.getUser().getNickname(), ReservedChatUser.ANY.getUser());
    }};

    private final LinkedList<ChatMessage> messages = new LinkedList<ChatMessage>();

    private int messageIndex = 0;

    ChatModel() {
        ChatUser user1 = new ChatUser("user1", 0);
        addUser(user1);
        postMessage(user1, null, "message 1");
    }

    public void addUser(String nickname, int color) {
        addUser(new ChatUser(nickname, color));
    }

    public void addUser(ChatUser user) {
        users.put(user.getNickname(), user);
    }

    public ChatUser getUser(String nickname) {
        return users.get(nickname);
    }

    public ChatMessage postMessage(String senderNickname, String receiverNickname, String text) {
        return postMessage(getUser(senderNickname), getUser(receiverNickname), text);
    }

    public ChatMessage postMessage(ChatUser sender, ChatUser receiver, String text) {
        ChatMessage message = new ChatMessage(messageIndex++, sender, receiver, text);
        messages.push(message);
        return message;
    }

    public LinkedList<ChatMessage> getMessages(String userNickname, int index) {

        LinkedList<ChatMessage> massagesAfter = new LinkedList<ChatMessage>();
        Iterator<ChatMessage> iterator = messages.iterator();
        ChatMessage message;

        while (iterator.hasNext()) {

            message = iterator.next();

            if (message.getIndex() > index) {
                if (message.getSender().getNickname().equals(userNickname) ||
                        message.getReceiver().getNickname().equals(userNickname) ||
                        message.getReceiver().equals(getAny())) {
                    massagesAfter.push(message);
                }
            } else {
                break;
            }
        }

        return massagesAfter;

    }

    public boolean isSigned(String nickname) {
        return users.containsKey(nickname);
    }

    public boolean isLogged(String nickname) {
        return getUser(nickname).isLogged();
    }

    public void login(String nickname) {
        getUser(nickname).setLogged(true);
    }

    public void logout(String nickname) {
        getUser(nickname).setLogged(false);
    }

    public ChatUser getAdmin() {
        return ReservedChatUser.ADMIN.getUser();
    }

    public ChatUser getAny() {
        return ReservedChatUser.ANY.getUser();
    }

}
